package master.springframework.usermanagement.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ContractCommand {
    private String id;
    private String description;
}
