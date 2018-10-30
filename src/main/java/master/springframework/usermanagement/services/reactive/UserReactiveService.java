package master.springframework.usermanagement.services.reactive;

import lombok.extern.slf4j.Slf4j;
import master.springframework.usermanagement.commands.UserCommand;
import master.springframework.usermanagement.converters.UserCommandToUser;
import master.springframework.usermanagement.converters.UserToUserCommand;
import master.springframework.usermanagement.repositories.reactive.UserReactiveRepository;
import master.springframework.usermanagement.services.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserReactiveService implements UserService {

    private final UserReactiveRepository userReactiveRepository;
    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;

    public UserReactiveService(UserReactiveRepository userReactiveRepository,
                               UserCommandToUser userCommandToUser,
                               UserToUserCommand userToUserCommand) {
        this.userReactiveRepository = userReactiveRepository;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    public Flux<UserCommand> getUsers() {
        return userReactiveRepository
                .findAll()
                .map(userToUserCommand::convert);
    }

    @Override
    public Mono<UserCommand> getById(String id) {
        return userReactiveRepository
                .findById(id)
                .map(userToUserCommand::convert);
    }

    @Override
    public Mono<UserCommand> save(UserCommand command) {
        return userReactiveRepository
                .save(userCommandToUser.convert(command))
                .map(userToUserCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(String idToDelete) {
        userReactiveRepository.deleteById(idToDelete).block();
        return Mono.empty();
    }
}
