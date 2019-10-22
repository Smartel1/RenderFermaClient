package ru.smartel.renderferma.client.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.smartel.renderferma.client.dto.Command;
import ru.smartel.renderferma.client.dto.RegistrationDTO;
import ru.smartel.renderferma.client.dto.TaskDTO;
import ru.smartel.renderferma.client.exception.CommandHandleException;
import ru.smartel.renderferma.client.validator.CommandValidationException;
import ru.smartel.renderferma.client.validator.CommandValidator;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommandHandlerImpl implements CommandHandler {

    private CommandValidator commandValidator;
    private RestTemplate restClient;

    @Value("${renderferma.server.host}")
    private String serverHost;
    @Value("${renderferma.server.port}")
    private Integer serverPort;

    private static String REG_ENDPOINT = "/api/user";
    private static String MAKE_ENDPOINT = "/api/task";
    private static String LIST_ENDPOINT = "/api/task";

    public CommandHandlerImpl(CommandValidator commandValidator, RestTemplate restClient) {
        this.commandValidator = commandValidator;
        this.restClient = restClient;
    }

    @Override
    public String handle(Command command) throws CommandValidationException, CommandHandleException {
        commandValidator.validate(command);
        try {
            switch (command.getCommandType()) {
                case REGISTER: return register(command.getArgs().get(0), command.getArgs().get(1));
                case LOGIN: return login(command.getArgs().get(0), command.getArgs().get(1));
                case MAKE_TASK: return makeTask();
                case LIST_TASKS: return listTasks();
                default: return "Unrecognized command";
            }
        } catch (Exception ex) {
            throw new CommandHandleException("Command handle went wrong: " + ex.getMessage(), ex);
        }

    }

    /**
     * register new user and save credentials
     */
    private String register(String email, String password) throws CommandHandleException {
        RegistrationDTO registrationDTO = new RegistrationDTO(email, password);
        restClient.getInterceptors().clear();
        try {
            restClient.exchange(
                    "http://" + serverHost + ":" + serverPort + REG_ENDPOINT,
                    HttpMethod.POST,
                    new HttpEntity<>(registrationDTO),
                    String.class
            );
        } catch (HttpClientErrorException ex) {
            throw new CommandHandleException("User cannot be created. " + ex.getResponseBodyAsString());
        }
        //add user credentials into restClient
        restClient.getInterceptors().add(getBasicAuthInterceptor(email, password));
        return "Created user '" + email + "' and logged in";
    }

    /**
     * set credentials of user
     */
    private String login(String email, String password) {
        restClient.getInterceptors().clear();
        //add user credentials into restClient
        restClient.getInterceptors().add(getBasicAuthInterceptor(email, password));
        return "Credentials have been saved";
    }

    /**
     * Create task
     */
    private String makeTask() {
        if (restClient.getInterceptors().isEmpty()) return "Login first";

        restClient.exchange("http://" + serverHost + ":" + serverPort + MAKE_ENDPOINT,
                HttpMethod.POST,
                null,
                Object.class
        );
        return "Task successfully created";
    }

    /**
     * Get user's tasks list
     */
    private String listTasks() {
        if (restClient.getInterceptors().isEmpty()) return "Login first";

        ResponseEntity<List<TaskDTO>> response = restClient.exchange(
                "http://" + serverHost + ":" + serverPort + LIST_ENDPOINT,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        if (null == response.getBody() || response.getBody().isEmpty()) return "No tasks";

        return response.getBody()
                .stream()
                .map(task -> "Task id: " + task.getId() + ", status: " + task.getStatus()
                )
                .collect(Collectors.joining("\n"));
    }

    /**
     * Construct basicAuth interceptor with defined credentials
     */
    private ClientHttpRequestInterceptor getBasicAuthInterceptor(String username, String password) {
        return new BasicAuthenticationInterceptor(username, password) {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                //dont user basicAuth on register endpoint.
                String IGNORE_ENDPOINT = "api/user";
                if (request.getURI().toString().endsWith(IGNORE_ENDPOINT)) {
                    return execution.execute(request, body);
                }
                return super.intercept(request, body, execution);
            }
        };
    }
}
