package dao;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import controller.DatabaseController;
import data.Message;

/**
 * 
 * @author Joulian
 * This is the data acces object for the messages
 */
public class DAO_Message {
	private final String MESSAGE_CONTENT = "messageContent";
	private final String RECEIVER = "receiver";
	private final String SENDER = "sender";
	private final String TRANSMITTEDTIME = "transmittedTime";
	private final String MESSAGE_COLLECTION = "Message";

	private static DAO_Message daoMessage = null;

	private DatabaseController dbController = null;

	/**
	 * @param db
	 * @throws IOException
	 */
	private DAO_Message() throws IOException {
		super();
		dbController = DatabaseController.getDbController();
	}

	/**
	 * This method is inserting a message to our cloud fire store database
	 * @param messageContent
	 * @param receiver
	 * @param sender
	 * @param transmittedTime
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void insertMessage(String messageContent, String receiver, String sender, Date transmittedTime)
			throws InterruptedException, ExecutionException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		String strtransmittedTime = dateFormat.format(transmittedTime);
		
		Map<String, Object> message = new HashMap<>();
		message.put(this.MESSAGE_CONTENT, messageContent);
		message.put(this.RECEIVER, receiver);
		message.put(this.SENDER, sender);
		message.put(this.TRANSMITTEDTIME, strtransmittedTime);
		dbController.insert(this.MESSAGE_COLLECTION, message);
	}

	/**
	 * This method is getting all messages of the cloud fire store database
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws ParseException 
	 */
	public HashSet<Message> getAllMessages() throws InterruptedException, ExecutionException, ParseException {
		QuerySnapshot allMessagesFromFireStore = dbController.getCollection(this.MESSAGE_COLLECTION);
		HashSet<Message> allMessages = new HashSet<Message>();

		if (!allMessagesFromFireStore.isEmpty()) {
			for (QueryDocumentSnapshot message : allMessagesFromFireStore) {
				
				Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(message.getString(TRANSMITTEDTIME));
				//Object date = message.get(this.TRANSMITTEDTIME);
				allMessages.add(new Message(message.getString(this.MESSAGE_CONTENT), message.getString(this.RECEIVER),
						message.getString(this.SENDER), date));
			}
		} else {
			System.out.println("Ther are no messages existing");
		}

		return allMessages;
	}
	
	/**
	 * This method deletes a message
	 * @param time
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void deleteMessage(String time) throws InterruptedException, ExecutionException {
		this.dbController.deleteDocument(this.MESSAGE_COLLECTION, this.dbController.getIdOfDocument(MESSAGE_COLLECTION, time));
	}

	/**
	 * @return the daoMessage
	 * @throws IOException
	 */
	public static DAO_Message getDaoMessage() throws IOException {
		if (DAO_Message.daoMessage == null) {
			DAO_Message.daoMessage = new DAO_Message();
		}

		return DAO_Message.daoMessage;
	}
}
