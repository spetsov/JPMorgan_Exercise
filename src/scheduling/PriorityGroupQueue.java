package scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.HashMap;

import common.*;

public class PriorityGroupQueue {
	private Map<Integer, Queue<Message>> qMap;
	private PrioritisationStrategy pStrategy;
	
	public PriorityGroupQueue(PrioritisationStrategy pStrategy){
		this.qMap = new HashMap<Integer, Queue<Message>>();
		this.pStrategy = pStrategy;
	}
	
	public void enqueue(Message m){
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
	
	public Message dequeue(){
		return pStrategy.getNext(qMap);
	}
	
	public boolean isEmpty(){
		for (Integer groupId : qMap.keySet()) {
			Queue<Message> queue = qMap.get(groupId);
			if(!queue.isEmpty())
				return false;
		}
		return true;
	}
}
