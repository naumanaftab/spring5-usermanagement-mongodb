package master.springframework.usermanagement.bootstrap;

import lombok.extern.slf4j.Slf4j;
import master.springframework.usermanagement.domain.Contract;
import master.springframework.usermanagement.domain.Group;
import master.springframework.usermanagement.domain.User;
import master.springframework.usermanagement.domain.UserType;
import master.springframework.usermanagement.repositories.ContractRepository;
import master.springframework.usermanagement.repositories.GroupRepository;
import master.springframework.usermanagement.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserManagementBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final GroupRepository groupRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    public UserManagementBootstrap(GroupRepository groupRepository,
                                   ContractRepository contractRepository,
                                   UserRepository userRepository) {

        this.groupRepository = groupRepository;
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadContracts();
        loadGroups();
        userRepository.saveAll(getUsers());

        log.debug("Loading Bootstrap Data");
    }

    private void loadContracts(){
        Contract contract1 = new Contract();
        contract1.setDescription("Master");
        contractRepository.save(contract1);

        Contract contract2 = new Contract();
        contract2.setDescription("Child");
        contractRepository.save(contract2);

        log.debug("Loading Contracts .....");
    }

    private void loadGroups(){
        Group group1 = new Group();
        group1.setName("Master-Production");
        groupRepository.save(group1);

        Group group2 = new Group();
        group2.setName("Master-Staging");
        groupRepository.save(group2);

        Group group3 = new Group();
        group3.setName("Child-Dev");
        groupRepository.save(group3);

        Group group4 = new Group();
        group4.setName("Child-Test");
        groupRepository.save(group4);

        log.debug("Loading Groups .....");
    }

    private List<User> getUsers() {

        List<User> users = new ArrayList<>(2);

        Contract contractMaster = contractRepository.findByDescription("Master")
                .orElseThrow(() -> new RuntimeException("Expected Contract Not Found"));

        Contract contractChild = contractRepository.findByDescription("Child")
                .orElseThrow(() -> new RuntimeException("Expected Contract Not Found"));

        Group masterProduction = groupRepository.findByName("Master-Production")
                .orElseThrow(() -> new RuntimeException("Expected Group Not Found"));

        Group masterStaging = groupRepository.findByName("Master-Staging")
                .orElseThrow(() -> new RuntimeException("Expected Group Not Found"));

        Group childDev = groupRepository.findByName("Child-Dev")
                .orElseThrow(() -> new RuntimeException("Expected Group Not Found"));

        Group childTest = groupRepository.findByName("Child-Test")
                .orElseThrow(() -> new RuntimeException("Expected Group Not Found"));


        User nauman = new User();
        nauman.setFirstName("Nauman");
        nauman.setLastName("Aftab");
        nauman.setUserType(UserType.OWNER);
        nauman.getContracts().add(contractMaster);
        nauman.getGroups().add(masterProduction);
        nauman.getGroups().add(masterStaging);

        users.add(nauman);

        User simon = new User();
        simon.setFirstName("Simon");
        simon.setLastName("Kintzel");
        simon.setUserType(UserType.OWNER);
        simon.getContracts().add(contractChild);
        simon.getGroups().add(childDev);
        simon.getGroups().add(childTest);

        users.add(simon);

        return users;

    }

}
