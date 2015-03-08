package scheduling;

import java.util.Iterator;

public interface PrioritisationStrategy {
	void setPriority(int groupID);
	Iterator<Integer> getIterator();
	void removePriority(int groupID);
}
