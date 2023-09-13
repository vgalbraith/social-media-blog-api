package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import java.util.ArrayList;
import java.util.List;
import Model.Message;

public class MessageService {
    private AccountDAO accountDAO;
    private MessageDAO messageDAO;

    /**
     * No-args constructor for creating a new AccountService with new AccountDAO && MessageDAO objects.
     */
    public MessageService() {
        accountDAO = new AccountDAO();
        messageDAO = new MessageDAO();
    }
    
    /**
     * Uses the MessageDAO to persist a Message. The given Message will not have a message_id provided.
     * @param message A Message object.
     * @return The persisted Message if the persistence is successful, otherwise null.
     */
    public Message addMessage(Message message) {
        if (message.getMessage_text().equals("") || 
            message.getMessage_text().length() >= 255 ||
            accountDAO.getAccountById(message.getPosted_by()) == null) {
            return null;
        } else {
            return messageDAO.insertMessage(message);
        }
    }
    
    /**
     * Uses the MessageDAO to retrieve all Messages.
     * @return all Messages.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
    
    /**
     * Uses the MessageDAO to retrieve a Message, identified by its message_id.
     * @param message_id
     * @return the Message, may be null if message_id is invalid.
     */
    public Message getMessage(int message_id) {
        return messageDAO.getMessage(message_id);
    }
    
    /**
     * Uses the MessageDAO to delete a Message, identified by its message_id.
     * @param message_id
     * @return The Message, may be null if message_id is invalid.
     */
    public Message deleteMessage(int message_id) {
        Message message = messageDAO.getMessage(message_id);
        if (message == null) {
            return null;
        } else {
            messageDAO.deleteMessage(message_id);
            return message;
        }
    }
    
    /**
     * Uses the MessageDAO to update a Message's message_text, identified by its message_id.
     * @param message_id
     * @param message_text New message_text to update the Message with.
     * @return The Message, may be null if message_id or message_text is invalid.
     */
    public Message updateMessage(int message_id, String message_text) {
        Message message = messageDAO.getMessage(message_id);
        if (message_text.equals("") ||message_text.length() >= 255 || message == null) {
            return null;
        } else {
            messageDAO.updateMessage(message_id, message_text);
            message.setMessage_text(message_text);
            return message;
        }
    }
    
    /**
     * Uses the MessageDAO to retrieve all Messages written by a particular user, identified by their account_id.
     * @param account_id
     * @return a List of Messages written by the particular user, may be empty if no Messages were written by the particluar user or if the account_id is invalid.
     */
    public List<Message> getAllMessagesByAccount_id(int account_id) {
        List<Message> messages = new ArrayList<Message>();
        if (accountDAO.getAccountById(account_id) != null) {
            messages = messageDAO.getAllMessagesByAccount_id(account_id);
        }
        return messages;
    }
}
