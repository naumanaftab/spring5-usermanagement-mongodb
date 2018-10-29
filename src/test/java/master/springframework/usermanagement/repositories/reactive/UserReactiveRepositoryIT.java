package master.springframework.usermanagement.repositories.reactive;

import master.springframework.usermanagement.domain.User;
import master.springframework.usermanagement.repositories.UserRepository;
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
public class UserReactiveRepositoryIT {

    @Autowired
    private UserReactiveRepository userReactiveRepository;

    @Before
    public void setUp() {
        userReactiveRepository.deleteAll().block();
    }

    @Test
    public void create_new_user_success() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName("reactive.firstName");
        user.setLastName("reactive.lastName");

        userReactiveRepository.save(user).block();

        Long count = userReactiveRepository.count().block();

        Assert.assertEquals(Long.valueOf(1L), count);
    }

}
