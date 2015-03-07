package common;

import java.util.Vector;

public class MessageImpl implements Message {

	private int groupId;
	private int messageId;
	private boolean isTerminationMessage;
	private String text;
	private Vector<Observer> observers;
	
	public MessageImpl(int groupId, String text, int messageId){
		this.groupId = groupId;
		this.text = text;
		this.messageId = messageId;
		this.observers = new Vector<Observer>();
		this.isTerminationMessage = false;
	}
	
	@Override
	public void completed() {
		this.notifyObservers();
	}

	@Override
	public int getGroupId() {
		return this.groupId;
	}
	
	@Override
	public int getId(){
		return this.messageId;
	}
	
	@Override
	public boolean isTerminationMessage() {
		return this.isTerminationMessage;
	}
	
	@Override
	public void setTerminationMessage(){
		this.isTerminationMessage = true;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Message finished processing - id: " + this.messageId);
		this.completed();
	}

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
