package master.springframework.usermanagement.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@Document
public class Contract {

    @Id
    private String id;

    private String description;

    @DBRef
    private Set<User> users;

}
