package ru.smartel.renderferma.client.handler;

import ru.smartel.renderferma.client.dto.Command;
import ru.smartel.renderferma.client.exception.CommandHandleException;
import ru.smartel.renderferma.client.validator.CommandValidationException;

public interface CommandHandler {
    String handle(Command command) throws CommandValidationException, CommandHandleException;
}
