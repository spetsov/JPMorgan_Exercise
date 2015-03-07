package scheduling;

import java.util.Map;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

import common.Message;

public class GroupNaturalOrderPrioritisationSequence implements PrioritisationStrategy {
	private SortedSet<Integer> priorities;
	
	public GroupNaturalOrderPrioritisationSequence() {
		this.priorities = new TreeSet<Integer>();
	}
	
	@Override
	public void setPriority(Message m, Map<Integer, Queue<Message>> qMap) {
		int groupId = m.getGroupId();
		if(!priorities.contains(groupId)){
			priorities.add(groupId);
		}		
	}

	@Override
	public Message getNext(Map<Integer, Queue<Message>> qMap) {
		for (Integer groupId : priorities) {
			Queue<Message> queue = qMap.get(groupId);
			if(!queue.isEmpty()){
				return queue.remove();
			}
		}
		return null;
	}

}
