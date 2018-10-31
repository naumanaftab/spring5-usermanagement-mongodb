package master.springframework.usermanagement.converters;

import master.springframework.usermanagement.commands.GroupCommand;
import master.springframework.usermanagement.domain.Group;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GroupCommandToGroup implements Converter<GroupCommand, Group> {

    @Override
    public Group convert(GroupCommand source) {
        if (source == null) {
            return null;
        }

        Group group = new Group();
        group.setId(source.getId());
        group.setName(source.getName());

        return group;
    }
}
