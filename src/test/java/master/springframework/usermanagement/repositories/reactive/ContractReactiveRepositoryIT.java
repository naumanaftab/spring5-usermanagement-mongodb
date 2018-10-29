package master.springframework.usermanagement.repositories.reactive;

import master.springframework.usermanagement.domain.Contract;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ContractReactiveRepositoryIT {

    @Autowired
    private ContractReactiveRepository contractReactiveRepository;

    @Before
    public void setUp() {
        contractReactiveRepository.deleteAll().block();
    }

    @Test
    public void create_new_contract_success() {
        Contract contract = new Contract();
        contract.setDescription("las.vegas.contract");

        contractReactiveRepository.save(contract).block();

        Long count = contractReactiveRepository.count().block();

        Assert.assertEquals(Long.valueOf(1L), count);
    }

    @Test
    public void get_contract_by_description_success() {
        Contract contract = new Contract();
        contract.setDescription("london.contract");

        contractReactiveRepository.save(contract).block();

        Contract foundContract = contractReactiveRepository.findByDescription("london.contract").block();

        Assert.assertNotNull(foundContract);
    }

}
