package master.springframework.usermanagement.services.reactive;

import lombok.extern.slf4j.Slf4j;
import master.springframework.usermanagement.commands.GroupCommand;
import master.springframework.usermanagement.converters.GroupCommandToGroup;
import master.springframework.usermanagement.converters.GroupToGroupCommand;
import master.springframework.usermanagement.domain.Group;
import master.springframework.usermanagement.domain.User;
import master.springframework.usermanagement.repositories.reactive.UserReactiveRepository;
import master.springframework.usermanagement.services.GroupService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class GroupReactiveServiceImpl implements GroupService {

    private final UserReactiveRepository userRepository;

    private final GroupToGroupCommand groupToGroupCommand;

    private final GroupCommandToGroup groupCommandToGroup;

    public GroupReactiveServiceImpl(UserReactiveRepository userRepository,
                                    GroupToGroupCommand groupToGroupCommand,
                                    GroupCommandToGroup groupCommandToGroup) {
        this.userRepository = userRepository;
        this.groupToGroupCommand = groupToGroupCommand;
        this.groupCommandToGroup = groupCommandToGroup;
    }

    @Override
    public Mono<GroupCommand> findByUserIdAndGroupId(String userId, String groupId) {
        return userRepository.findById(userId)
                .map(user -> user.getGroups()
                        .stream()
                        .filter(group -> group.getId().equalsIgnoreCase(groupId))
                        .findFirst())
                .filter(Optional::isPresent)
                .map(group -> {
                    GroupCommand command = groupToGroupCommand.convert(group.get());
                    command.setUserId(userId);
                    return command;
                });

    }

    @Override
    public Mono<GroupCommand> saveGroup(GroupCommand command) {
        User user = userRepository.findById(command.getUserId()).block();

        if (user == null) {

            //todo toss error if not found!
            log.error("User not found for id: " + command.getUserId());
            return Mono.just(new GroupCommand());
        } else {

            Optional<Group> groupOptional = user
                    .getGroups()
                    .stream()
                    .filter(group -> group.getId().equals(command.getId()))
                    .findFirst();

            if (groupOptional.isPresent()) {
                Group foundGroup = groupOptional.get();
                foundGroup.setName(command.getName());
            } else {
                //add new Group
                Group group = groupCommandToGroup.convert(command);
                user.addGroup(group);
            }

            User savedUser = userRepository.save(user).block();

            Optional<Group> savedGroupOptional =
                    savedUser.getGroups()
                            .stream()
                            .filter(userGroups -> userGroups.getId().equals(command.getId()))
                            .findFirst();

            //check by name
            if (!savedGroupOptional.isPresent()) {
                //not totally safe... But best guess
                savedGroupOptional = savedUser.getGroups().stream()
                        .filter(userGroups -> userGroups.getName().equals(command.getName()))
                        .findFirst();
            }

            //todo check for fail

            //enhance with id value
            GroupCommand groupCommandSaved = groupToGroupCommand.convert(savedGroupOptional.get());
            groupCommandSaved.setUserId(user.getId());

            return Mono.just(groupCommandSaved);
        }
    }

    @Override
    public Mono<Void>   deleteById(String userId, String idToDelete) {
        log.debug("Deleting group: " + userId + ":" + idToDelete);

        User user = userRepository.findById(userId).block();

        if (user != null) {

            log.debug("found user");

            Optional<Group> groupOptional = user
                    .getGroups()
                    .stream()
                    .filter(groups -> groups.getId().equals(idToDelete))
                    .findFirst();

            if (groupOptional.isPresent()) {
                log.debug("found Group");

                user.getGroups().remove(groupOptional.get());
                userRepository.save(user).block();
            }
        } else {
            log.debug("User Not found with Id:" + userId);
        }

        return Mono.empty();
    }
}
