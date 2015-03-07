package tests;

import java.util.Vector;

import common.Message;
import common.Observer;

public class MessageMock implements Message {
	private int groupId;
	private int messageId;
	private boolean isTerminationMessage;
	private String text;
	private Vector<Observer> observers;
	
	public MessageMock(int groupId, String text, int messageId){
		this.groupId = groupId;
		this.text = text;
		this.messageId = messageId;
		this.observers = new Vector<Observer>();
		this.isTerminationMessage = false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void completed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getGroupId() {
		return groupId;
	}

	@Override
	public int getId() {
		return messageId;
	}

	@Override
	public boolean isTerminationMessage() {
		return isTerminationMessage;
	}

	@Override
	public void setTerminationMessage() {
		this.isTerminationMessage = false;
	}

}
