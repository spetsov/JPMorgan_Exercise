package tests;

import java.util.Map;
import java.util.TreeMap;

import common.Gateway;
import common.Message;

public class GatewayMock implements Gateway {
	private Map<Integer, Integer> orderMap;
	private int expectedMessages;
	private int currentMessages;
	
	public GatewayMock(int expectedMessages){
		this.orderMap = new TreeMap<Integer, Integer>();
		this.currentMessages = 0;
		this.expectedMessages = expectedMessages;
	}
	
	public Map<Integer, Integer> getOrderMap(){
		return this.orderMap;
	}

	@Override
	public boolean send(Message msg) {
		msg.completed();
		this.currentMessages++;
		this.orderMap.put(msg.getId(), currentMessages);

		if(this.currentMessages == this.expectedMessages){
			synchronized(this){
				this.notifyAll();
			}
		}
		return true;
	}

}
