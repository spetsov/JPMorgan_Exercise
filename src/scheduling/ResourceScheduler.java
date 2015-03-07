package scheduling;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import common.Gateway;
import common.Message;
import common.Observable;
import common.Observer;

public class ResourceScheduler implements Runnable, Observer {
	private Set<Integer> cancelledGroups;
	private Set<Integer> terminatedGroups;
	private MessagePrioritisationQueue queue;
	private Gateway gate;
	private AtomicInteger availableResources;

	public ResourceScheduler(int availableResources, Gateway gate, MessagePrioritisationQueue queue) {
		this.queue = queue;
		this.cancelledGroups = new HashSet<Integer>();
		this.terminatedGroups = new HashSet<Integer>();
		this.availableResources = new AtomicInteger(availableResources);
		this.gate = gate;
	}

	@Override
	public void run() {
		while (true) {
			int res = this.availableResources.get();
			if (res > 0 && !queue.isEmpty()) {
				Message m = queue.dequeue();
				sendMessage(m);
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.availableResources.incrementAndGet();
		// prevent memory leak
		o.deleteObserver(this);
	}

	public void scheduleSend(Message m) throws GroupTerminatedException {
		if(this.isTerminated(m)){
			throw new GroupTerminatedException();
		}
		if(m.isTerminationMessage()){
			this.terminateGroup(m.getGroupId());
		}
		if(this.isCancelled(m)){
			return;
		}
		m.addObserver(this);
		int res = this.availableResources.get();
		if(res > 0 && queue.isEmpty())
			sendMessage(m);
		else
			this.queue.enqueue(m);
	}
	
	public void cancelGroup(int groupId){
		if(!cancelledGroups.contains(groupId)){
			cancelledGroups.add(groupId);
		}
	}
	
	public boolean isCancelled(Message m){
		return cancelledGroups.contains(m.getGroupId());
	}
	
	private void terminateGroup(int groupId){
		if(!terminatedGroups.contains(groupId)){
			terminatedGroups.add(groupId);
		}
	}
	
	private boolean isTerminated(Message m){
		return terminatedGroups.contains(m.getGroupId());
	}

	private void sendMessage(Message m) {
		if (m != null && !this.isCancelled(m)) {
			if (this.gate.send(m))
				this.availableResources.decrementAndGet();
		}
	}

}