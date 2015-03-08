package scheduling;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroupSequencePrioritisationStrategy implements
		PrioritisationStrategy {
	private List<Integer> groupsOrderList;

	public GroupSequencePrioritisationStrategy() {
		groupsOrderList = new ArrayList<Integer>();
	}

	@Override
	public void setPriority(int groupId) {
		groupsOrderList.add(groupId);
	}

	@Override
	public Iterator<Integer> getIterator() {
		return groupsOrderList.iterator();
	}

	@Override
	public void removePriority(int groupId) {
		this.groupsOrderList.remove((Integer) groupId);
	}

}
