package demos;

import scheduling.GroupSequencePrioritisationStrategy;
import scheduling.GroupTerminatedException;
import scheduling.ResourceScheduler;
import ext.GatewayImpl;

public class Demo {

	public static void main(String[] args) throws GroupTerminatedException {
		
		ResourceScheduler sch = new ResourceScheduler(5, new GatewayImpl(), new GroupSequencePrioritisationStrategy());
		Sender sender = new Sender(sch);
		Thread t1 = new Thread(sch);
		Thread t2 = new Thread(sender);
		t1.start();
		t2.start();
		
	}

}
