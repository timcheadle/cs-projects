import junit.framework.*;

/*
 * Created on Mar 25, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */

/**
 * @author session
 */
public class LinkedQueueTester extends TestCase {
	public LinkedQueueTester(String name) {
		super(name);
	}
	
	public void testQueuing() {
		LinkedQueue a = new LinkedQueue();
		assertTrue("Constructor creates an empty queue", a.size() == 0);
		
		for(int i=0; i < 10; i++) {
			a.enqueue(new Integer(i));
		}
		assertTrue("Enqueue adds items to the queue", a.size() == 10);
		
		Integer n = (Integer)(a.dequeue());
		assertTrue("Dequeue returns the first element", n.equals(new Integer(0)));
		assertTrue("Dequeue removes the first element", a.size() == 9);
		
		n = (Integer)(a.first());
		assertTrue("First returns the first element", n.equals(new Integer(1)));
		assertTrue("First does not remove the first element", a.size() == 9);
		
		n = (Integer)(a.last());
		assertTrue("Last returns the first element", n.equals(new Integer(9)));
		assertTrue("Last does not remove the first element", a.size() == 9);
	}
	
	public void testEqual() {
		LinkedQueue a = new LinkedQueue();
		LinkedQueue b = new LinkedQueue();
		assertTrue("Empty queues are equal", a.equals(b));
		
		a.enqueue(new Integer(1));
		b.enqueue(new Integer(1));
		assertTrue("Same Non-empty queues are equal", a.equals(b));
		
		b.enqueue(new Integer(2));
		assertTrue("Different non-empty queues are equal", !a.equals(b));
		
		a.dequeue();
		assertTrue("Empty queue not equal non-empty queue", !a.equals(b));
		assertTrue("Queue equals itself", a.equals(a));
	}
	
	public void testClone() {
		LinkedQueue a = new LinkedQueue();
		a.enqueue(new Integer(1));
		
		try {
			assertTrue("Clones are equal", !(a.clone().equals(a)));
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public static Test suite() {
		return new TestSuite(LinkedQueueTester.class);
	}
}
