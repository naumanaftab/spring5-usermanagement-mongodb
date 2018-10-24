package master.springframework.usermanagement.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document
public class User {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private Byte[] image;

    private UserType userType;

    @DBRef
    private Set<Contract> contracts = new HashSet<>();

    @DBRef
    private Set<Group> groups = new HashSet<>();

}
