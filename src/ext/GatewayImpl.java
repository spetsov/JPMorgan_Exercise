package ext;

import common.Gateway;
import common.Message;

public class GatewayImpl implements Gateway {

	@Override
	public boolean send(Message msg) {
		System.out.println("Message sent - id:" + msg.getId());
		Thread t = new Thread(msg);
		t.start();
		return true;
	}

}
