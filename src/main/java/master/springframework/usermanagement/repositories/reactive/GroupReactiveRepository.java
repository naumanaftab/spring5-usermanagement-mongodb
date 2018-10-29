package master.springframework.usermanagement.repositories.reactive;

import master.springframework.usermanagement.domain.Group;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface GroupReactiveRepository extends ReactiveMongoRepository<Group, String> {
}
