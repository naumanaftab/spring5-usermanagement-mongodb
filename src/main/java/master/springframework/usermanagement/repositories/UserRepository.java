package master.springframework.usermanagement.repositories;

import master.springframework.usermanagement.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
