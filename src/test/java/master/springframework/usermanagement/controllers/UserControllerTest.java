package master.springframework.usermanagement.controllers;

import master.springframework.usermanagement.commands.UserCommand;
import master.springframework.usermanagement.domain.User;
import master.springframework.usermanagement.services.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Ignore
public class UserControllerTest {

    private UserController controller;

    @Mock
    private UserService userService;


    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                //.setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void get_new_user_form_view_success() throws Exception {
        mockMvc.perform(get("/user/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/userForm"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void post_new_user_form_success() throws Exception {
        UserCommand command = new UserCommand();
        command.setId("2");

        when(userService.save(any())).thenReturn(Mono.just(command));

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("firstName", "some first name")
                .param("lastName", "some last name"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/2/show"));
    }

    @Test
    public void get_update_form_view_success() throws Exception {
        UserCommand command = new UserCommand();
        command.setId("2");

        when(userService.getById(anyString())).thenReturn(Mono.just(command));

        mockMvc.perform(get("/user/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/userForm"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void get_user_by_id_view_success() throws Exception {

        UserCommand user = new UserCommand();
        user.setId("1");

        when(userService.getById(anyString())).thenReturn(Mono.just(user));

        mockMvc.perform(get("/user/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/show"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void delete_user_success() throws Exception {
        mockMvc.perform(get("/user/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(userService, times(1)).deleteById(anyString());
    }

}
