package tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import scheduling.PrioritisationStrategy;

public class PrioritisationStrategyMock implements PrioritisationStrategy {
	private List<Integer> priorities;
	private List<Integer> prioritisedGroups;
	
	public PrioritisationStrategyMock(List<Integer> priorities) {
		this.priorities = priorities;
		this.prioritisedGroups = new ArrayList<Integer>();
	}
	
	public List<Integer> getPrioritisedGroups(){
		return this.prioritisedGroups;
	}

	@Override
	public void setPriority(int groupID) {
		this.prioritisedGroups.add(groupID);
	}

	@Override
	public Iterator<Integer> getIterator() {
		return priorities.iterator();
	}

	@Override
	public void removePriority(int groupID) {
		this.priorities.remove((Integer)groupID);
	}

}
