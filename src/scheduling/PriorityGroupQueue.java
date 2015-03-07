package scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.HashMap;

import common.*;

public class PriorityGroupQueue implements MessagePrioritisationQueue {
	private Map<Integer, Queue<Message>> qSet;
	private List<Integer> groupsOrderList;
	
	public PriorityGroupQueue(){
		this.qSet = new HashMap<Integer, Queue<Message>>();
		this.groupsOrderList = new ArrayList<Integer>();
	}
	
	public void enqueue(Message m){
		int groupId = m.getGroupId();
		if(qSet.containsKey(groupId)){
			Queue<Message> queue = qSet.get(groupId);
			queue.add(m);
		}
		else{
			Queue<Message> queue = new LinkedList<Message>();
			queue.add(m);
			qSet.put(groupId, queue);
			groupsOrderList.add(groupId);
		}
	}
	
	public Message dequeue(){
		for (int i = 0; i < groupsOrderList.size(); i++) {
			int groupId = groupsOrderList.get(i);
			Queue<Message> queue = qSet.get(groupId);
			if(!queue.isEmpty()){
				return queue.remove();
			}
		}
		return null;
	}
	
	public boolean isEmpty(){
		for (Integer groupId : qSet.keySet()) {
			Queue<Message> queue = qSet.get(groupId);
			if(!queue.isEmpty())
				return false;
		}
		return true;
	}
}
