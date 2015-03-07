package scheduling;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import common.Gateway;
import common.Message;
import common.Observable;
import common.Observer;

public class ResourceScheduler implements Runnable, Observer {
	private Set<Integer> cancelledGroups;
	private Set<Integer> terminatedGroups;
	private PriorityGroupQueue queue;
	private Gateway gate;
	private AtomicInteger availableResources;
	private Thread t;

	public ResourceScheduler(int availableResources, Gateway gate, PrioritisationStrategy pStrategy) {
		this.queue = new PriorityGroupQueue(pStrategy);
		this.cancelledGroups = new HashSet<Integer>();
		this.terminatedGroups = new HashSet<Integer>();
		this.availableResources = new AtomicInteger(availableResources);
		this.gate = gate;
		this.t = new Thread(this);
	}

	@Override
	public void run() {
		while (!(Thread.currentThread().isInterrupted())) {
			int res = this.availableResources.get();
			if (res > 0) {
				Message m;
				try {
					m = queue.take();
					sendMessage(m);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
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
	
	public void startListening(){
		t.start();
	}
	
	public void stopListening(){
		t.interrupt();
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
		this.queue.put(m);
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
