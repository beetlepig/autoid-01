package programaciondmi.per;

import java.io.Serializable;

public class ContentMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Sender's ID
	 */
	private int sender;
	/**
	 * Receiver's ID
	 */
	private int receiver;
	private int x;
	private int y;
	
	public ContentMessage(int sender, int receiver, int x, int y) {
		this.sender = sender;
		this.receiver = receiver;
		this.x=x;
		this.y=y;
	}

	public int getSender() {
		return sender;
	}
	
	public int getReceiver() {
		return receiver;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
