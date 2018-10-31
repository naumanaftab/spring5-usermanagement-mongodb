package master.springframework.usermanagement.converters;

import lombok.Synchronized;
import master.springframework.usermanagement.commands.ContractCommand;
import master.springframework.usermanagement.domain.Contract;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ContractToContractCommand implements Converter<Contract, ContractCommand> {

    @Synchronized
    @Nullable
    @Override
    public ContractCommand convert(Contract source) {
        if (source == null) {
            return null;
        }
        final ContractCommand contractCommand = new ContractCommand();

        contractCommand.setId(source.getId());
        contractCommand.setDescription(source.getDescription());

        return contractCommand;

    }
}
