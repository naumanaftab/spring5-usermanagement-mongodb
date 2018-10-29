package master.springframework.usermanagement.repositories.reactive;

import master.springframework.usermanagement.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserReactiveRepository extends ReactiveMongoRepository<User, String> {
}
