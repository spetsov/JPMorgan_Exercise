package scheduling;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import common.*;

public class PriorityMessageQueue {
	private Map<Integer, Queue<Message>> qMap;
	private PrioritisationStrategy pStrategy;
	Condition isEmptyCondition;
	ReentrantLock lock;

	public PriorityMessageQueue(PrioritisationStrategy pStrategy) {
		this.qMap = new HashMap<Integer, Queue<Message>>();
		this.pStrategy = pStrategy;
		lock = new ReentrantLock();
		isEmptyCondition = lock.newCondition();
	}

	public void put(Message m) {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			this.enqueue(m);
			isEmptyCondition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public Message take() throws InterruptedException {
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		Message result;
		try {
			while ((result = dequeue()) == null)
				isEmptyCondition.await();
		} finally {
			lock.unlock();
		}
		return result;
	}
	
	public boolean isEmpty(){
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			for (Integer key : qMap.keySet()) {
				Queue<Message> current = qMap.get(key);
				if(!current.isEmpty())
					return false;
			}
			return true;
		} finally {
			lock.unlock();
		}
	}

	public void discardQueue(int id) {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			this.qMap.remove(id);
			this.pStrategy.removePriority(id);
		} finally {
			lock.unlock();
		}
	}

	private void enqueue(Message m) {
		int groupId = m.getGroupId();
		if (qMap.containsKey(groupId)) {
			Queue<Message> queue = qMap.get(groupId);
			queue.add(m);
		} else {
			Queue<Message> queue = new LinkedList<Message>();
			queue.add(m);
			qMap.put(groupId, queue);
			pStrategy.setPriority(groupId);
		}
	}

	private Message dequeue() {
		Iterator<Integer> it = pStrategy.getIterator();
		while (it.hasNext()) {
			int groupId = it.next();
			Queue<Message> queue = qMap.get(groupId);
			if (!queue.isEmpty()) {
				return queue.remove();
			}
		}

		return null;
	}
}
