package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import java.util.List;
import Model.Message;

public class MessageService {
    private AccountDAO accountDAO;
    private MessageDAO messageDAO;

    /**
     * No-args constructor for creating a new AccountService with a new AccountDAO.
     */
    public MessageService() {
        accountDAO = new AccountDAO();
        messageDAO = new MessageDAO();
    }
    
    /**
     * Uses the MessageDAO to persist a Message. The given Message will not have an message_id provided.
     * @param account A Message object.
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
}
