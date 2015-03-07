package demos;

import scheduling.GroupTerminatedException;
import scheduling.MessageImpl;
import scheduling.ResourceScheduler;
import common.Message;

public class Sender implements Runnable {
	private ResourceScheduler sch;

	public Sender(ResourceScheduler sch) {
		this.sch = sch;
	}

	@Override
	public void run() {
		String testText = "test";
		Message m1 = new MessageImpl(2, testText, 1);
		Message m2 = new MessageImpl(1, testText, 2);
		Message m3 = new MessageImpl(2, testText, 3);
		Message m4 = new MessageImpl(3, testText, 4);
		Message m5 = new MessageImpl(1, testText, 5);
		Message m6 = new MessageImpl(3, testText, 6);
		Message m7 = new MessageImpl(2, testText, 7);
		// m3.setTerminationMessage();
		try {
			sch.scheduleSend(m1);

			sch.scheduleSend(m2);
			sch.scheduleSend(m3);
			sch.scheduleSend(m4);
			sch.scheduleSend(m5);
			sch.scheduleSend(m6);
			sch.scheduleSend(m7);
			// sch.cancelGroup(2);
			Thread.sleep(5000);

			sch.scheduleSend(new MessageImpl(3, testText, 8));
			sch.scheduleSend(new MessageImpl(1, testText, 9));
			sch.scheduleSend(new MessageImpl(3, testText, 10));
			sch.scheduleSend(new MessageImpl(2, testText, 11));
		} catch (GroupTerminatedException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
