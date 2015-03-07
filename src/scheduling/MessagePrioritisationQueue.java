package scheduling;

import common.Message;

public interface MessagePrioritisationQueue {
	void enqueue(Message m);
	Message dequeue();
	boolean isEmpty();
}
