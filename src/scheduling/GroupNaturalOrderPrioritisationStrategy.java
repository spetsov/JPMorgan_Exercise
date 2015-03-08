package scheduling;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class GroupNaturalOrderPrioritisationStrategy implements PrioritisationStrategy {
	private SortedSet<Integer> priorities;
	
	public GroupNaturalOrderPrioritisationStrategy() {
		this.priorities = new TreeSet<Integer>();
	}
	
	@Override
	public void setPriority(int groupId) {
		priorities.add(groupId);
	}

	@Override
	public Iterator<Integer> getIterator() {
		return priorities.iterator();
	}

	@Override
	public void removePriority(int groupId) {
		priorities.remove((Integer)groupId);
	}

}
