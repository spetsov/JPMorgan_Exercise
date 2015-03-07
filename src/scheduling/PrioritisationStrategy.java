package scheduling;

import java.util.Map;
import java.util.Queue;

import common.Message;

public interface PrioritisationStrategy {
	void setPriority(Message m, Map<Integer, Queue<Message>> qMap);
	Message getNext(Map<Integer, Queue<Message>> qMap);
}
