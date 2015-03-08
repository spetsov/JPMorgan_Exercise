package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.junit.Test;

import scheduling.GroupNaturalOrderPrioritisationStrategy;

public class GroupSequencePrioritisationStrategy_Should {

	@Test
	public void Prioritise_Based_On_Sequence() {
		GroupNaturalOrderPrioritisationStrategy str = new GroupNaturalOrderPrioritisationStrategy();
		str.setPriority(2);
		str.setPriority(1);
		str.setPriority(3);
		
		Iterator<Integer> it = str.getIterator();
		
		int first = it.next();
		int second = it.next();
		int third = it.next();
		
		assertEquals(1, first);
		assertEquals(2, second);
		assertEquals(3, third);
		
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
