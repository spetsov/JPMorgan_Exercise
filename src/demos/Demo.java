package demos;

import scheduling.GroupSequencePrioritisationStrategy;
import scheduling.GroupTerminatedException;
import scheduling.ResourceScheduler;
import ext.GatewayImpl;

public class Demo {

	public static void main(String[] args) throws GroupTerminatedException, InterruptedException {
		
		ResourceScheduler sch = new ResourceScheduler(5, new GatewayImpl(), new GroupSequencePrioritisationStrategy());
		Sender sender = new Sender(sch);
		sch.startListening();
		Thread t2 = new Thread(sender);
		t2.start();
		Thread.sleep(10000);
		sch.stopListening();
		t2.interrupt();
	}

}
