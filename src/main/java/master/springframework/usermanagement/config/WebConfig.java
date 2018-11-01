package master.springframework.usermanagement.config;

import master.springframework.usermanagement.commands.UserCommand;
import master.springframework.usermanagement.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;


@Configuration
public class WebConfig {

    @Bean
    RouterFunction<?> routes(UserService userService){
        return RouterFunctions.route(GET("/api/users"),
                serverRequest -> ServerResponse
                                    .ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(userService.getUsers(), UserCommand.class));

    }
}
