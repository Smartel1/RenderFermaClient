package ru.smartel.renderferma.client.converter;

import org.springframework.stereotype.Service;
import ru.smartel.renderferma.client.dto.Command;
import ru.smartel.renderferma.client.handler.CommandType;
import ru.smartel.renderferma.client.validator.CommandValidationException;

import java.util.*;

import static ru.smartel.renderferma.client.handler.CommandType.*;

@Service
public class CommandConverterImpl implements CommandConverter {

    @Override
    public Command convert(String rawCommand) throws CommandValidationException {
        List<String> elements = Arrays.asList(rawCommand.split("\\s+"));

        if (elements.isEmpty()) {
            fail();
        }

        return new Command(
                fromString(elements.get(0)),
                elements.subList(1, elements.size())
        );
    }

    private CommandType fromString(String rawCommand) throws CommandValidationException {
        CommandType commandType = getAssociations().get(rawCommand);
        if (null == commandType) fail();
        return commandType;
    }

    @Override
    public Map<String, CommandType> getAssociations() {
        Map<String, CommandType> associations = new HashMap<>();
        associations.put("reg", REGISTER);
        associations.put("login", LOGIN);
        associations.put("mk", MAKE_TASK);
        associations.put("ls", LIST_TASKS);
        return associations;
    }


    private void fail() throws CommandValidationException {
        throw new CommandValidationException("invalid command");
    }
}
