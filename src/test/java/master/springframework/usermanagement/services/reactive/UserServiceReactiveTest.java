package master.springframework.usermanagement.services.reactive;


import master.springframework.usermanagement.commands.UserCommand;
import master.springframework.usermanagement.converters.UserCommandToUser;
import master.springframework.usermanagement.converters.UserToUserCommand;
import master.springframework.usermanagement.domain.User;
import master.springframework.usermanagement.repositories.reactive.UserReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceReactiveTest {

    private UserReactiveService userReactiveService;

    @Mock
    private UserReactiveRepository userReactiveRepository;

    @Mock
    private UserToUserCommand userToUserCommand;

    @Mock
    private UserCommandToUser userCommandToUser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userReactiveService =
                new UserReactiveService(userReactiveRepository, userCommandToUser, userToUserCommand);
    }

    @Test
    public void get_all_users_success() {

        //Given
        User user = new User();
        user.setId("1");

        UserCommand userCommand = new UserCommand();
        userCommand.setId("1");

        when(userToUserCommand.convert(any())).thenReturn(userCommand);
        when(userReactiveRepository.findAll()).thenReturn(Flux.just(user));

        //When
        List<UserCommand> users = userReactiveService.getUsers().collectList().block();

        //Then
        assertNotNull(users);
        assertEquals(users.size(), 1);
        assertSame(users.get(0), userCommand);
        verify(userReactiveRepository, times(1)).findAll();
        verify(userReactiveRepository, never()).findById(anyString());
    }


    @Test
    public void get_user_by_id_success() {

        //Given
        User user = new User();
        user.setId("1");

        UserCommand userCommand = new UserCommand();
        userCommand.setId("1");

        when(userToUserCommand.convert(any())).thenReturn(userCommand);
        when(userReactiveRepository.findById(anyString())).thenReturn(Mono.just(user));

        //When
        UserCommand foundUser = userReactiveService.getById("1").block();

        //Then
        assertSame(foundUser, userCommand);
        verify(userReactiveRepository).findById(anyString());
        verify(userReactiveRepository, never()).findAll();
    }

    @Test
    public void save_user_success() {

        //Given
        User user = new User();
        user.setId("1");

        UserCommand userCommand = new UserCommand();
        userCommand.setId("1");

        when(userCommandToUser.convert(any())).thenReturn(user);
        when(userReactiveRepository.save(any())).thenReturn(Mono.just(user));

        //When
        userReactiveService.save(userCommand);

        //Then
        verify(userReactiveRepository, times(1)).save(any());
    }

    @Test
    public void delete_user_by_id_success() {

        //Given
        String idToDelete = "2";

        when(userReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());

        //When
        userReactiveService.deleteById(idToDelete).block();

        //Then
        verify(userReactiveRepository, times(1)).deleteById(anyString());
    }

}
