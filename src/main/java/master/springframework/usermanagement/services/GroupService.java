package master.springframework.usermanagement.services;

import master.springframework.usermanagement.commands.GroupCommand;
import reactor.core.publisher.Mono;

public interface GroupService {

    Mono<GroupCommand> findByUserIdAndGroupId(String userId, String groupId);

    Mono<GroupCommand> saveGroup(GroupCommand command);

    Mono<Void> deleteById(String userId, String idToDelete);

}
