/**
 * 
 */
package data;

/**
 * @author Joulian
 * This class represents a message object
 */
public class Message {
	private String messageContent = "";
	private String receiver = "";
	private String sender = "";
	
	/**
	 * @param messageContent
	 * @param receiver
	 * @param sender
	 */
	public Message(String messageContent, String receiver, String sender) {
		super();
		this.messageContent = messageContent;
		this.receiver = receiver;
		this.sender = sender;
	}

	/**
	 * @return the messageContent
	 */
	public String getMessageContent() {
		return messageContent;
	}

	/**
	 * @param messageContent the messageContent to set
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
}
