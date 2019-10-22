package ru.smartel.renderferma.client.dto;

import ru.smartel.renderferma.client.handler.CommandType;

import java.util.List;

public class Command {
    private CommandType commandType;
    private List<String> args;

    public Command(CommandType commandType, List<String> args) {
        this.commandType = commandType;
        this.args = args;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }
}
