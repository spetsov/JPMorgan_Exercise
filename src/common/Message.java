package common;


public interface Message extends Runnable, Observable{
	void completed();
	int getGroupId();
	int getId();
	boolean isTerminationMessage();
	void setTerminationMessage();
}
