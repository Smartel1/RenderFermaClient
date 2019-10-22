package ru.smartel.renderferma.client.validator;

import ru.smartel.renderferma.client.dto.Command;

public interface CommandValidator {
    void validate(Command command) throws CommandValidationException;
}
