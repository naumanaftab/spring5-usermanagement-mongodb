package master.springframework.usermanagement.converters;

import master.springframework.usermanagement.commands.GroupCommand;
import master.springframework.usermanagement.domain.Group;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GroupToGroupCommand implements Converter<Group, GroupCommand> {

    @Override
    public GroupCommand convert(Group source) {
        if (source == null) {
            return null;
        }

        GroupCommand groupCommand = new GroupCommand();
        groupCommand.setId(source.getId());
        groupCommand.setName(source.getName());

        return groupCommand;
    }
}
