package master.springframework.usermanagement.converters;

import lombok.Synchronized;
import master.springframework.usermanagement.commands.UserCommand;
import master.springframework.usermanagement.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    private final ContractCommandToContract contractConverter;

    private final GroupCommandToGroup groupConverter;

    public UserCommandToUser(ContractCommandToContract contractConverter, GroupCommandToGroup groupConverter) {
        this.contractConverter = contractConverter;
        this.groupConverter = groupConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public User convert(UserCommand command) {
        if (command == null) {
            return null;
        }
        final User user = new User();
        user.setId(command.getId());
        user.setUserType(command.getUserType());
        user.setFirstName(command.getFirstName());
        user.setLastName(command.getLastName());

        if (command.getContracts() != null && command.getContracts().size() > 0) {
            command.getContracts()
                    .forEach(contract -> user.getContracts().add(contractConverter.convert(contract)));
        }

        if(command.getGroups() != null && command.getGroups().size() > 0) {
            command.getGroups()
                    .forEach(group -> user.getGroups().add(groupConverter.convert(group)));
        }

        return user;
    }
}
