package scheduling;


import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import common.*;

public class PriorityGroupQueue {
	private Map<Integer, Queue<Message>> qMap;
	private PrioritisationStrategy pStrategy;
	Condition isEmptyCondition;
	ReentrantLock lock;
	
	public PriorityGroupQueue(PrioritisationStrategy pStrategy){
		this.qMap = new HashMap<Integer, Queue<Message>>();
		this.pStrategy = pStrategy;
		lock = new ReentrantLock();
	    isEmptyCondition = lock.newCondition();
	}
	
	public void put(Message m){
		final ReentrantLock lock = this.lock;
		lock.lock();
	    try {
	        this.enqueue(m);
	        isEmptyCondition.signalAll();
	    } finally {
	        lock.unlock();
	    }
	}
	
	public Message take() throws InterruptedException{
	        final ReentrantLock lock = this.lock;
	        lock.lockInterruptibly();
	        Message result;
	        try {
	            while ( (result = dequeue()) == null)
	            	isEmptyCondition.await();
	        } finally {
	            lock.unlock();
	        }
	        return result;
	}
	
	private void enqueue(Message m){
		pStrategy.setPriority(m, qMap);
		int groupId = m.getGroupId();
		if(qMap.containsKey(groupId)){
			Queue<Message> queue = qMap.get(groupId);
			queue.add(m);
		}
		else{
			Queue<Message> queue = new LinkedList<Message>();
			queue.add(m);
			qMap.put(groupId, queue);
		}
	}
	
	private Message dequeue(){
		return pStrategy.getNext(qMap);
	}
	
	private boolean isEmpty(){
		for (Integer groupId : qMap.keySet()) {
			Queue<Message> queue = qMap.get(groupId);
			if(!queue.isEmpty())
				return false;
		}
		return true;
	}
}
