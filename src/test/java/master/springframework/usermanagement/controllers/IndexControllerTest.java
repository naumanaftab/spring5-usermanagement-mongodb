package master.springframework.usermanagement.controllers;

import master.springframework.usermanagement.commands.UserCommand;
import master.springframework.usermanagement.domain.User;
import master.springframework.usermanagement.services.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@Ignore
public class IndexControllerTest {

    private IndexController controller;

    @Mock
    private UserService userService;

    @Mock
    private Model model;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new IndexController(userService);
    }

    @Test
    public void get_index_page_success() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(userService.getUsers()).thenReturn(Flux.empty());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void get_index_page_with_users_success() {

        //given
        Set<UserCommand> users = new HashSet<>();
        users.add(new UserCommand());

        UserCommand user = new UserCommand();
        user.setId("1");

        users.add(user);

        when(userService.getUsers()).thenReturn(Flux.fromIterable(users));

        ArgumentCaptor<List<User>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        //when
        String viewName = controller.getIndexPage(model);


        //then
        assertEquals("index", viewName);
        verify(userService, times(1)).getUsers();
        verify(model, times(1)).addAttribute(eq("users"), argumentCaptor.capture());
        List<User> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

}