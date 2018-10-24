package master.springframework.usermanagement.repositories;

import master.springframework.usermanagement.domain.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, String> {

    Optional<Group> findByName(String id);
}
