package ru.smartel.renderferma.client.validator;

import org.springframework.stereotype.Service;
import ru.smartel.renderferma.client.dto.Command;

@Service
public class CommandValidatorImpl implements CommandValidator {
    @Override
    public void validate(Command command) throws CommandValidationException {
        //Check arguments correctness
        switch (command.getCommandType()) {
            case REGISTER:
            case LOGIN: {
                checkArgsCount(command, 2);
                if (3 > command.getArgs().get(0).length())
                    throw new CommandValidationException("Email must contain 3 symbols at least");
                break;
            }
            case MAKE_TASK:
            case LIST_TASKS:
                checkArgsCount(command, 0); break;
        }
    }

    private void checkArgsCount(Command command, int expected) throws CommandValidationException {
        if (command.getArgs().size() != expected) {
            throw new CommandValidationException("Command supplies " + expected + " args!");
        }
    }
}
