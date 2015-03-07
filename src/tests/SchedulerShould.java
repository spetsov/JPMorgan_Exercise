package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import common.Message;
import scheduling.GroupNaturalOrderPrioritisationSequence;
import scheduling.GroupSequencePrioritisationStrategy;
import scheduling.GroupTerminatedException;
import scheduling.ResourceScheduler;

public class SchedulerShould {

	@Test
	public void SEND_MESSAGES_IN_CORRECT_SEQUENCE() {
		String testText = "test";
		GatewayMock mock = new GatewayMock(7);
		ResourceScheduler sch = new ResourceScheduler(5, mock, new GroupSequencePrioritisationStrategy());
		Message m1 = new MessageMock(2, testText, 1);
		Message m2 = new MessageMock(1, testText, 2);
		Message m3 = new MessageMock(2, testText, 3);
		Message m4 = new MessageMock(3, testText, 4);
		Message m5 = new MessageMock(1, testText, 5);
		Message m6 = new MessageMock(3, testText, 6);
		Message m7 = new MessageMock(2, testText, 7);
		sch.startListening();
		try {
			sch.scheduleSend(m1);
			sch.scheduleSend(m2);
			sch.scheduleSend(m3);
			sch.scheduleSend(m4);
			sch.scheduleSend(m5);
			sch.scheduleSend(m6);
			sch.scheduleSend(m7);
		} catch (GroupTerminatedException e1) {
			e1.printStackTrace();
		}
		synchronized(mock){
			try {
				mock.wait(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		sch.stopListening();
		Map<Integer, Integer> orderMap = mock.getOrderMap();
		
		int order = 0;
		assertTrue(orderMap.get(1) == ++order);
		assertTrue(orderMap.get(3) == ++order);
		assertTrue(orderMap.get(7) == ++order);
		assertTrue(orderMap.get(2) == ++order);
		assertTrue(orderMap.get(5) == ++order);
		assertTrue(orderMap.get(4) == ++order);
		assertTrue(orderMap.get(6) == ++order);
		
	}
	
	@Test
	public void SEND_MESSAGES_IN_NATURAL_SEQUENCE() {
		String testText = "test";
		GatewayMock mock = new GatewayMock(7);
		ResourceScheduler sch = new ResourceScheduler(5, mock, new GroupNaturalOrderPrioritisationSequence());
		Message m1 = new MessageMock(2, testText, 1);
		Message m2 = new MessageMock(1, testText, 2);
		Message m3 = new MessageMock(2, testText, 3);
		Message m4 = new MessageMock(3, testText, 4);
		Message m5 = new MessageMock(1, testText, 5);
		Message m6 = new MessageMock(3, testText, 6);
		Message m7 = new MessageMock(2, testText, 7);
		sch.startListening();
		try {
			sch.scheduleSend(m1);
			sch.scheduleSend(m2);
			sch.scheduleSend(m3);
			sch.scheduleSend(m4);
			sch.scheduleSend(m5);
			sch.scheduleSend(m6);
			sch.scheduleSend(m7);
		} catch (GroupTerminatedException e1) {
			e1.printStackTrace();
		}
		synchronized(mock){
			try {
				mock.wait(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		sch.stopListening();
		Map<Integer, Integer> orderMap = mock.getOrderMap();
		
		int order = 0;
		assertTrue(orderMap.get(2) == ++order);
		assertTrue(orderMap.get(5) == ++order);
		assertTrue(orderMap.get(1) == ++order);
		assertTrue(orderMap.get(3) == ++order);
		assertTrue(orderMap.get(7) == ++order);
		assertTrue(orderMap.get(4) == ++order);
		assertTrue(orderMap.get(6) == ++order);
		
	}
	
	@Test
	public void CANCEL_MESSAGES_FROM_GROUP() {
		String testText = "test";
		GatewayMock mock = new GatewayMock(3);
		ResourceScheduler sch = new ResourceScheduler(5, mock, new GroupSequencePrioritisationStrategy());
		Message m1 = new MessageMock(2, testText, 1);
		Message m2 = new MessageMock(1, testText, 2);
		Message m3 = new MessageMock(2, testText, 3);
		sch.startListening();
		try {
			sch.scheduleSend(m1);
			sch.scheduleSend(m2);
			sch.cancelGroup(2);
			sch.scheduleSend(m3);
		} catch (GroupTerminatedException e1) {
			e1.printStackTrace();
		}
		synchronized(mock){
			try {
				mock.wait(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		sch.stopListening();
		Map<Integer, Integer> orderMap = mock.getOrderMap();
		
		assertFalse(orderMap.containsKey(m3.getId()));		
	}
	
	@Test(expected=GroupTerminatedException.class)
	public void TERMINATE_MESSAGES_FROM_GROUP() throws GroupTerminatedException {
		String testText = "test";
		GatewayMock mock = new GatewayMock(3);
		ResourceScheduler sch = new ResourceScheduler(5, mock, new GroupSequencePrioritisationStrategy());
		Message m1 = new MessageMock(2, testText, 1);
		m1.setTerminationMessage();
		Message m2 = new MessageMock(1, testText, 2);
		Message m3 = new MessageMock(2, testText, 3);
		sch.startListening();
		try {
			sch.scheduleSend(m1);
			sch.scheduleSend(m2);
			sch.scheduleSend(m3);
		} catch (GroupTerminatedException e1) {
			sch.stopListening();
			throw e1;
		}		
	}
}
