package master.springframework.usermanagement.repositories.reactive;

import master.springframework.usermanagement.domain.Contract;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ContractReactiveRepository extends ReactiveMongoRepository<Contract, String> {

    Mono<Contract> findByDescription(String description);
}
