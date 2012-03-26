import junit.framework.*;

/**
 * LinkedQueueTester is a unit test for LinkedQueue that utilizes the
 * JUnit framework.  It tests queuing methods as well as the canonical methods
 * that LinkedQueue supports.
 * 
 * @author Tim Cheadle
 * @version 1.0
 * @since 1.0
 */
public class LinkedQueueTester extends TestCase {
	public LinkedQueueTester(String name) {
		super(name);
	}
	
	/**
	 * Tests queuing/dequeuing methods.
	 * 
	 * @see LinkedQueue#enqueue(Object)
	 * @see LinkedQueue#dequeue()
	 * @see LinkedQueue#size()
	 * @see LinkedQueue#first()
	 * @see LinkedQueue#last()
	 */
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
	
	/**
	 * Tests canonical equality method.
	 * 
	 * @see LinkedQueue#equals(Object)
	 */
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
	
	/**
	 * Tests canonical object cloning method.
	 * 
	 * @see LinkedQueue#clone()
	 */
	public void testClone() {
		LinkedQueue a = new LinkedQueue();
		a.enqueue(new Integer(1));
		
		try {
			assertTrue("Clones are not the same object as the original", a.clone() != a);
			assertTrue("Clones are equal to their original", a.clone().equals(a));
			assertTrue("Clones are the same class as their original", a.clone() instanceof LinkedQueue);
		} catch (CloneNotSupportedException e) {}
	}
	
	/**
	 * Tests canonical object description string method.
	 * 
	 * @see LinkedQueue#toString()
	 */
	public void testToString() {
		LinkedQueue a = new LinkedQueue();
		
		String desc = a.toString();
		assertTrue("Empty queue does not return a null toString() description", desc != null);
		System.out.println("toString(): " + desc);
		
		for(int i=1; i <= 10; i++) {
			a.enqueue(new Integer(i));
		}
		
		desc = a.toString();
		assertTrue("Non-empty queue does not return a null toString() description", desc != null);
		System.out.println("toString(): " + desc);
	}
	
	/**
	 * Generates the suite of tests to perform
	 * 
	 * @return The suite of tests to perform
	 */
	public static Test suite() {
		return new TestSuite(LinkedQueueTester.class);
	}
}
