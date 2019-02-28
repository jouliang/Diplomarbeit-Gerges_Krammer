/**
 * 
 */
package data;

import java.util.Date;

/**
 * @author Joulian
 * This class represents a message object
 */
public class Message {
	private String messageContent = "";
	private String receiver = "";
	private String sender = "";
	private Date transmittedTime = null;

	/**
	 * @param messageContent
	 * @param receiver
	 * @param sender
	 * @param transmittedTime
	 */
	public Message(String messageContent, String receiver, String sender, Date transmittedTime) {
		super();
		this.messageContent = messageContent;
		this.receiver = receiver;
		this.sender = sender;
		this.transmittedTime = transmittedTime;
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

	public Date getTransmittedTime() {
		return transmittedTime;
	}

	public void setTransmittedTime(Date transmittedTime) {
		this.transmittedTime = transmittedTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageContent == null) ? 0 : messageContent.hashCode());
		result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (messageContent == null) {
			if (other.messageContent != null)
				return false;
		} else if (!messageContent.equals(other.messageContent))
			return false;
		if (receiver == null) {
			if (other.receiver != null)
				return false;
		} else if (!receiver.equals(other.receiver))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		return true;
	}
}
