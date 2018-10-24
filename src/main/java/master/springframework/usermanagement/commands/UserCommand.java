package master.springframework.usermanagement.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import master.springframework.usermanagement.domain.Contract;
import master.springframework.usermanagement.domain.Group;
import master.springframework.usermanagement.domain.UserType;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserCommand {

    private String id;

    private String firstName;

    private String lastName;

    private Byte[] image;

    private UserType userType;

    private Set<Contract> contracts = new HashSet<>();

    private Set<Group> groups = new HashSet<>();
}
