package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.Javalin;
import java.util.List;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService = new AccountService();
    MessageService messageService = new MessageService();

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);

        return app;
    }

    /**
     * Handler to post a new account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException Thrown if there is an issue converting JSON into an object.
     */
    private void postAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount != null) {
            context.json(mapper.writeValueAsString(addedAccount));
        } else {
            context.status(400);
        }
    }

    /**
     * Handler to post a login request.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException Thrown if there is an issue converting JSON into an object.
     */
    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account verifiedAccount = accountService.getAccountByLogin(account.getUsername(), account.getPassword());
        if (verifiedAccount != null) {
            context.json(mapper.writeValueAsString(verifiedAccount));
        } else {
            context.status(401);
        }
    }

    /**
     * Handler to post a new message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException Thrown if there is an issue converting JSON into an object.
     */
    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage != null) {
            context.json(mapper.writeValueAsString(addedMessage));
        } else {
            context.status(400);
        }
    }

    /**
     * Handler to get all Messages.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException Thrown if there is an issue converting JSON into an object.
     */
    private void getMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    /**
     * Handler to get a Message, identified by its message_id.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException Thrown if there is an issue converting JSON into an object.
     */
    private void getMessageHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessage(message_id);
        if (message != null) {
            context.json(message);
        } else {
            context.json("");
        }
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

}
