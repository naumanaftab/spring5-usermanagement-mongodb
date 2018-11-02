package master.springframework.usermanagement.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Document
public class Group {

    @Id
    private String id;

    @NotBlank
    @Min(3)
    private String name;

    @DBRef
    private Set<User> users;
}
