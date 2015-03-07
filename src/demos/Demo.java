package demos;

import java.util.Date;

import common.*;
import ext.*;
import scheduling.*;

public class Demo {

	public static void main(String[] args) throws GroupTerminatedException {
		String testText = "test";
		Message m1 = new MessageImpl(2, testText, 1);
		Message m2 = new MessageImpl(1, testText, 2);
		Message m3 = new MessageImpl(2, testText, 3);
		Message m4 = new MessageImpl(3, testText, 4);
		Message m5 = new MessageImpl(1, testText, 5);
		Message m6 = new MessageImpl(3, testText, 6);
		Message m7 = new MessageImpl(2, testText, 7);
		//m3.setTerminationMessage();
		ResourceScheduler sch = new ResourceScheduler(2, new GatewayImpl(), new GroupSequencePrioritisationStrategy());
		Thread t = new Thread(sch);
		t.start();
		sch.scheduleSend(m1);
		sch.scheduleSend(m2);
		sch.scheduleSend(m3);
		sch.scheduleSend(m4);
		sch.scheduleSend(m5);
		sch.scheduleSend(m6);
		sch.scheduleSend(m7);
		//sch.cancelGroup(2);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sch.scheduleSend(new MessageImpl(3, testText, 8));
		sch.scheduleSend(new MessageImpl(1, testText, 9));
		sch.scheduleSend(new MessageImpl(3, testText, 10));
		sch.scheduleSend(new MessageImpl(2, testText, 11));
	}

}
