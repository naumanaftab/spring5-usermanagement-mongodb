package master.springframework.usermanagement.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import master.springframework.usermanagement.domain.User;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class GroupCommand {

    private String id;

    private String name;

    private Set<User> users = new HashSet<>();
}
