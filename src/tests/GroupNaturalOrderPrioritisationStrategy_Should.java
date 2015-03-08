package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.junit.Test;

import scheduling.GroupNaturalOrderPrioritisationStrategy;

public class GroupNaturalOrderPrioritisationStrategy_Should {

	@Test
	public void Prioritise_Based_On_Natural_Order() {
		GroupNaturalOrderPrioritisationStrategy str = new GroupNaturalOrderPrioritisationStrategy();
		str.setPriority(2);
		str.setPriority(6);
		str.setPriority(4);
		str.setPriority(5);
		str.setPriority(7);
		str.setPriority(1);
		str.setPriority(3);
		
		Iterator<Integer> it = str.getIterator();
		
		int first = it.next();
		int second = it.next();
		int third = it.next();
		int fourth = it.next();
		int fifth = it.next();
		int sixth = it.next();
		int seventh = it.next();
		
		assertEquals(1, first);
		assertEquals(2, second);
		assertEquals(3, third);
		assertEquals(4, fourth);
		assertEquals(5, fifth);
		assertEquals(6, sixth);
		assertEquals(7, seventh);
		
	}
	
	@Test
	public void Remove_Priority() {
		GroupNaturalOrderPrioritisationStrategy str = new GroupNaturalOrderPrioritisationStrategy();
		str.setPriority(2);
		str.setPriority(1);
		
		str.removePriority(2);
		
		Iterator<Integer> it = str.getIterator();		
		
		int first = it.next();
		
		assertEquals(1, first);
		assertFalse(it.hasNext());
		
	}

}
