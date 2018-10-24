package master.springframework.usermanagement.repositories;

import master.springframework.usermanagement.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void create_new_user_success() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName("firstName");
        user.setLastName("lastName");

        User savedUser = userRepository.save(user);
        Assert.assertSame(user, savedUser);
    }

}
