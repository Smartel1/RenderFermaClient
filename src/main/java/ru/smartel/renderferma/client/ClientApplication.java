package ru.smartel.renderferma.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.smartel.renderferma.client.converter.CommandConverter;
import ru.smartel.renderferma.client.dto.Command;
import ru.smartel.renderferma.client.exception.CommandHandleException;
import ru.smartel.renderferma.client.handler.CommandHandler;
import ru.smartel.renderferma.client.validator.CommandValidationException;

import java.io.BufferedReader;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    @Autowired
    BufferedReader reader;
    @Autowired
    CommandHandler commandHandler;
    @Autowired
	CommandConverter commandConverter;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("----------------------------------");
		System.out.println("Hello! This is RenderFerma-prototype client!");
		System.out.println("----------------------------------");
		System.out.println("Available commands: ");
		System.out.println("    login <username> <password> // set credentials");
		System.out.println("    reg <username> <password> // register user and set credentials");
		System.out.println("    mk // make task");
		System.out.println("    ls // list tasks");
		System.out.println("Exit by Ctrl+C");

        while (true) {
			System.out.print("Enter command: ");
            String rawCommand = reader.readLine();
            try {
				Command commandDTO = commandConverter.convert(rawCommand);
				String handlingResult = commandHandler.handle(commandDTO);
                System.out.println(handlingResult);
			} catch (CommandValidationException | CommandHandleException ex) {
				System.out.println(ex.getMessage());
			}
        }
    }
}
