package master.springframework.usermanagement.converters;

import lombok.Synchronized;
import master.springframework.usermanagement.commands.UserCommand;
import master.springframework.usermanagement.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {

    private final ContractToContractCommand contractConverter;

    private final GroupToGroupCommand groupConverter;

    public UserToUserCommand(ContractToContractCommand contractConverter, GroupToGroupCommand groupConverter) {
        this.contractConverter = contractConverter;
        this.groupConverter = groupConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UserCommand convert(User source) {
        if (source == null) {
            return null;
        }
        final UserCommand user = new UserCommand();
        user.setId(source.getId());
        user.setUserType(source.getUserType());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());

        if(source.getContracts() != null && !source.getContracts().isEmpty()) {
            source.getContracts()
                    .forEach(contractCommand -> user.getContracts().add(contractConverter.convert(contractCommand)));
        }

        if(source.getGroups() != null && !source.getGroups().isEmpty()) {
            source.getGroups()
                    .forEach(group -> user.getGroups().add(groupConverter.convert(group)));
        }

        return user;
    }
}
