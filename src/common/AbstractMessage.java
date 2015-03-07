package common;

import java.util.Vector;


public abstract class AbstractMessage implements Message{
	private int groupId;
	private int messageId;
	private boolean isTerminationMessage;
	private String text;
	private Vector<Observer> observers;
	
	public AbstractMessage(int groupId, String text, int messageId){
		this.groupId = groupId;
		this.text = text;
		this.messageId = messageId;
		this.observers = new Vector<Observer>();
		this.isTerminationMessage = false;
	}
	
	public void completed() {
		this.notifyObservers();
	}

	public int getGroupId() {
		return this.groupId;
	}
	
	public int getId(){
		return this.messageId;
	}
	
	public boolean isTerminationMessage() {
		return this.isTerminationMessage;
	}
	
	public void setTerminationMessage(){
		this.isTerminationMessage = true;
	}
	
	@Override
	public abstract void run();

	@Override
	public void addObserver(Observer o) {
		if (!observers.contains(o)) {
			observers.addElement(o);
        }
	}

	@Override
	public void notifyObservers() {
		Object[] arrLocal;

        synchronized (this) {
            arrLocal = observers.toArray();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(this, null);
	}

	@Override
	public void deleteObserver(Observer o) {
		this.observers.remove(o);
	}
}
