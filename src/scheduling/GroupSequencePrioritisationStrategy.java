package scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import common.Message;

public class GroupSequencePrioritisationStrategy implements PrioritisationStrategy {
	private List<Integer> groupsOrderList;
	
    public GroupSequencePrioritisationStrategy() {
		groupsOrderList = new ArrayList<Integer>();
	}

	@Override
	public void setPriority(Message m, Map<Integer, Queue<Message>> qMap) {
		int groupId = m.getGroupId();
		if(!qMap.containsKey(groupId)){
			groupsOrderList.add(groupId);
		}
	}

	@Override
	public Message getNext(Map<Integer, Queue<Message>> qMap) {
		for (int i = 0; i < groupsOrderList.size(); i++) {
			int groupId = groupsOrderList.get(i);
			Queue<Message> queue = qMap.get(groupId);
			if(!queue.isEmpty()){
				return queue.remove();
			}
		}
		return null;
	}

}
