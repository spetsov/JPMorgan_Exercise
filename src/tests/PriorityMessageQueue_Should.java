package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import scheduling.PriorityMessageQueue;

import org.junit.Test;

public class PriorityMessageQueue_Should {
	@Test
	public void PRIORITISE_GROUP(){
		PrioritisationStrategyMock str = new PrioritisationStrategyMock(null);
		PriorityMessageQueue queue = new PriorityMessageQueue(str);
		String text = "Test";
		MessageMock m1 = new MessageMock(1, text, 1);
		MessageMock m2 = new MessageMock(2, text, 2);
		MessageMock m3 = new MessageMock(1, text, 3);
		queue.put(m1);
		queue.put(m2);
		queue.put(m3);
		
		List<Integer> priorities = str.getPrioritisedGroups();
		assertEquals(priorities.size(), 2);
		assertEquals((int)priorities.get(0), 1);
		assertEquals((int)priorities.get(1), 2);
	}
	
	@Test
	public void ADD_NEW_MESSAGE() throws InterruptedException{
		List<Integer> priorities = new ArrayList<Integer>();
		priorities.add(1);
		PrioritisationStrategyMock str = new PrioritisationStrategyMock(priorities);
		PriorityMessageQueue queue = new PriorityMessageQueue(str);
		String text = "Test";
		MessageMock m1 = new MessageMock(1, text, 1);
		queue.put(m1);
		
		assertEquals(queue.take(), m1);
	}
	
	@Test
	public void GET_TOP_MESSAGES() throws InterruptedException{
		List<Integer> priorities = new ArrayList<Integer>();
		priorities.add(3);
		priorities.add(1);
		priorities.add(2);
		PrioritisationStrategyMock str = new PrioritisationStrategyMock(priorities);
		PriorityMessageQueue queue = new PriorityMessageQueue(str);
		String text = "Test";
		MessageMock m1 = new MessageMock(1, text, 1);
		MessageMock m2 = new MessageMock(3, text, 2);
		MessageMock m3 = new MessageMock(2, text, 3);
		MessageMock m4 = new MessageMock(1, text, 4);
		queue.put(m1);
		queue.put(m2);
		queue.put(m3);
		queue.put(m4);
		
		assertEquals(queue.take(), m2);
		assertEquals(queue.take(), m1);
		assertEquals(queue.take(), m4);
		assertEquals(queue.take(), m3);
	}
}
