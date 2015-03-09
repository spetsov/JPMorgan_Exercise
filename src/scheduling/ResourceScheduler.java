package scheduling;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import common.Gateway;
import common.Message;
import common.Observable;
import common.Observer;

public class ResourceScheduler implements Runnable, Observer {
	private Set<Integer> cancelledGroups;
	private Set<Integer> terminatedGroups;
	private PriorityMessageQueue queue;
	private Gateway gate;
	private Semaphore availableResources;
	private Thread consumerThread;

	public ResourceScheduler(int availableResources, Gateway gate,
			PrioritisationStrategy pStrategy) {
		this.queue = new PriorityMessageQueue(pStrategy);
		this.cancelledGroups = new HashSet<Integer>();
		this.terminatedGroups = new HashSet<Integer>();
		this.availableResources = new Semaphore(availableResources, true);
		this.gate = gate;
		this.consumerThread = new Thread(this);
	}

	@Override
	public void run() {
		while (!(Thread.currentThread().isInterrupted())) {

			Message m;
			try {
				this.availableResources.acquire();
				m = queue.take();
				if(!sendMessage(m))
					this.availableResources.release();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.availableResources.release();
		// prevent memory leak
		o.deleteObserver(this);
	}

	public void startListening() {
		consumerThread.start();
	}

	public void stopListening() {
		consumerThread.interrupt();
	}

	public void scheduleSend(Message m) throws GroupTerminatedException {
		if (this.isTerminated(m)) {
			throw new GroupTerminatedException();
		}
		if (m.isTerminationMessage()) {
			this.terminateGroup(m.getGroupId());
		}
		if (this.isCancelled(m)) {
			return;
		}
		m.addObserver(this);

		this.queue.put(m);

	}

	public void cancelGroup(int groupId) {
		if (!cancelledGroups.contains(groupId)) {
			this.queue.discardQueue(groupId);
			cancelledGroups.add(groupId);
		}
	}

	public boolean isCancelled(Message m) {
		return cancelledGroups.contains(m.getGroupId());
	}

	private void terminateGroup(int groupId) {
		if (!terminatedGroups.contains(groupId)) {
			this.queue.discardQueue(groupId);
			terminatedGroups.add(groupId);
		}
	}

	private boolean isTerminated(Message m) {
		return terminatedGroups.contains(m.getGroupId());
	}

	private boolean sendMessage(Message m) throws InterruptedException {
		if (m != null && !this.isCancelled(m)) {
			return this.gate.send(m);
		}
		return false;
	}

}
