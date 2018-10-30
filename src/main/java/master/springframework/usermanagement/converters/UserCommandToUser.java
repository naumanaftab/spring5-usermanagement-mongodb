package master.springframework.usermanagement.converters;

import lombok.Synchronized;
import master.springframework.usermanagement.commands.UserCommand;
import master.springframework.usermanagement.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    @Synchronized
    @Nullable
    @Override
    public User convert(UserCommand source) {
        if (source == null) {
            return null;
        }
        final User user = new User();
        user.setId(source.getId());
        user.setUserType(source.getUserType());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        return user;
    }
}
