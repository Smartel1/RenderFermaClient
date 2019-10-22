package ru.smartel.renderferma.client.exception;

public class CommandHandleException extends Exception {
    public CommandHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandHandleException(String message) {
        super(message);
    }
}
