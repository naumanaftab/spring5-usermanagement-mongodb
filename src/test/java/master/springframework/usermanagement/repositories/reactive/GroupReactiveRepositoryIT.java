package master.springframework.usermanagement.repositories.reactive;

import master.springframework.usermanagement.domain.Contract;
import master.springframework.usermanagement.domain.Group;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class GroupReactiveRepositoryIT {

    @Autowired
    private GroupReactiveRepository groupReactiveRepository;

    @Before
    public void setUp() {
        groupReactiveRepository.deleteAll().block();
    }

    @Test
    public void create_new_group_success() {
        Group group = new Group();
        group.setName("production");

        Group savedGroup = groupReactiveRepository.save(group).block();

        Long count = groupReactiveRepository.count().block();

        Assert.assertEquals(Long.valueOf(1L), count);
        Assert.assertNotNull(savedGroup.getId());
        Assert.assertEquals(savedGroup.getName(), "production");
    }

}
