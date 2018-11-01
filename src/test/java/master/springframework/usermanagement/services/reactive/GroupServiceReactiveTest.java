package master.springframework.usermanagement.services.reactive;

import master.springframework.usermanagement.commands.GroupCommand;
import master.springframework.usermanagement.converters.GroupCommandToGroup;
import master.springframework.usermanagement.converters.GroupToGroupCommand;
import master.springframework.usermanagement.domain.Group;
import master.springframework.usermanagement.domain.User;
import master.springframework.usermanagement.repositories.reactive.UserReactiveRepository;
import master.springframework.usermanagement.services.GroupService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GroupServiceReactiveTest {

    private GroupService groupService;
    private final GroupToGroupCommand groupToGroupCommand;
    private final GroupCommandToGroup groupCommandToGroup;

    @Mock
    UserReactiveRepository userReactiveRepository;

    //init converters
    public GroupServiceReactiveTest() {
        this.groupToGroupCommand = new GroupToGroupCommand();
        this.groupCommandToGroup = new GroupCommandToGroup();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        groupService = new GroupReactiveServiceImpl(userReactiveRepository,
                groupToGroupCommand, groupCommandToGroup);
    }

    @Test
    public void get_group_by_user_id_and_group_id_success() {
        //given
        User user = new User();
        user.setId("1");

        Group group1 = new Group();
        group1.setId("1");

        Group group2 = new Group();
        group2.setId("1");

        Group group3 = new Group();
        group3.setId("3");

        user.addGroup(group1);
        user.addGroup(group2);
        user.addGroup(group3);

        when(userReactiveRepository.findById(anyString())).thenReturn(Mono.just(user));

        //then
        GroupCommand groupCommand = groupService.findByUserIdAndGroupId("1", "3").block();

        //when
        assertEquals("3", groupCommand.getId());
        verify(userReactiveRepository, times(1)).findById(anyString());
    }


    @Test
    public void save_group_success() {
        //given
        GroupCommand command = new GroupCommand();
        command.setId("3");
        command.setUserId("2");

        User savedUser = new User();
        savedUser.addGroup(new Group());
        savedUser.getGroups().iterator().next().setId("3");

        when(userReactiveRepository.findById(anyString())).thenReturn(Mono.just(new User()));
        when(userReactiveRepository.save(any())).thenReturn(Mono.just(savedUser));

        //when
        GroupCommand savedCommand = groupService.saveGroup(command).block();

        //then
        assertEquals("3", savedCommand.getId());
        verify(userReactiveRepository, times(1)).findById(anyString());
        verify(userReactiveRepository, times(1)).save(any(User.class));

    }

  @Test
    public void delete_group_success() {
        //given
        User user = new User();
        Group group = new Group();
        group.setId("3");
        user.addGroup(group);

        when(userReactiveRepository.findById(anyString())).thenReturn(Mono.just(user));
        when(userReactiveRepository.save(any())).thenReturn(Mono.just(user));

        //when
        groupService.deleteById("1", "3");

        //then
        verify(userReactiveRepository, times(1)).findById(anyString());
        verify(userReactiveRepository, times(1)).save(any(User.class));
    }


}
