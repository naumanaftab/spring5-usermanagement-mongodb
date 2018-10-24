package master.springframework.usermanagement.repositories;

import master.springframework.usermanagement.domain.Contract;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContractRepository extends CrudRepository<Contract, String> {

    Optional<Contract> findByDescription(String description);
}
