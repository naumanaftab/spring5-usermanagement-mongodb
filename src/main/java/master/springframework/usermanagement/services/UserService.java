package master.springframework.usermanagement.services;

import master.springframework.usermanagement.commands.UserCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserCommand> getUsers();

    Mono<UserCommand> getById(String id);

    Mono<UserCommand>  save(UserCommand command);

    Mono<Void> deleteById(String idToDelete);

}
