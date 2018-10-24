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
public class Group {

    @Id
    private String id;

    private String name;

    @DBRef
    private Set<User> users = new HashSet<>();
}
