package ru.smartel.renderferma.client.converter;

import ru.smartel.renderferma.client.dto.Command;
import ru.smartel.renderferma.client.handler.CommandType;
import ru.smartel.renderferma.client.validator.CommandValidationException;

import java.util.Map;

public interface CommandConverter {
    Command convert(String rawCommand) throws CommandValidationException;

    /**
     * Get map which joins raw String commands with CommandTypes
     */
    Map<String, CommandType> getAssociations();
}
