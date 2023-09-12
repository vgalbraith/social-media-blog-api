package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;


public class MessageDAO {

    /**
     * Insert a Message into the Message table.
     * The message_id should be automatically generated by the sql database if it is not provided because it was set to auto_increment.
     * Therefore, only the remaining columns (posted_by, message_text, time_posted_epoch) are needed to insert a record.
     * @param message The Message to add to the database.
     * @return Message if successfully persisted, null if not (if the Message primary key was already in use.)
     */
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_message_id = (int)pkeyResultSet.getLong(1);
                return new Message(generated_message_id,
                                   message.getPosted_by(),
                                   message.getMessage_text(),
                                   message.getTime_posted_epoch());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Retrieve all Messages from the Message table.
     * @return all Messages.
     */
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                                              rs.getInt("posted_by"),
                                              rs.getString("message_text"),
                                              rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}