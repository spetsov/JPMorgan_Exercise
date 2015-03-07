package scheduling;

import common.AbstractMessage;

public class MessageImpl extends AbstractMessage {
	
	public MessageImpl(int groupId, String text, int messageId) {
		super(groupId, text, messageId);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Message finished processing - id: " + this.getId());
		this.completed();
	}

}
