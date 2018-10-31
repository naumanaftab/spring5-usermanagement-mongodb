package master.springframework.usermanagement.converters;

import lombok.Synchronized;
import master.springframework.usermanagement.commands.ContractCommand;
import master.springframework.usermanagement.domain.Contract;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ContractCommandToContract implements Converter<ContractCommand, Contract> {

    @Synchronized
    @Nullable
    @Override
    public Contract convert(ContractCommand source) {
        if (source == null) {
            return null;
        }
        final Contract contract = new Contract();

        contract.setId(source.getId());
        contract.setDescription(source.getDescription());

        return contract;
    }
}
