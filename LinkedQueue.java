import java.util.*; 

/**
 * LinkedQueue is a canonical class that implements the Queue
 * interface using a LinkedList.
 * 
 * @author Tim Cheadle
 * @version 1.0
 * @since 1.0
 */
public class LinkedQueue implements Queue, Cloneable {
	private LinkedList queue;
	
	public LinkedQueue() {
		queue = new LinkedList();
	}
	
	/**
	 * @see Queue#enqueue(Object)
	 */
	public void enqueue(Object element) {
		queue.add(element);
	}

	/**
	 * @see Queue#dequeue()
	 */
	public Object dequeue() {
		return queue.removeFirst();
	}

	/**
	 * @see Queue#size()
	 */
	public int size() {
		return queue.size();
	}

	/**
	 * @see Queue#first()
	 */
	public Object first() {
		return queue.getFirst();
	}

	/**
	 * @see Queue#last()
	 */
	public Object last() {
		return queue.getLast();
	}

	/**
	 * Returns a clone of the object.  The following contract is in place:
	 * <ol>
	 * <li><code>a.clone()</code> does not reference the same object as <code>a</code>
	 * <li><code>a.clone()</code> equals <code>a</code>
	 * <li><code>a.clone()</code> is of the same class as <code>a</code>
	 * </ol>
	 * 
	 * @exception CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException {
		LinkedQueue c = new LinkedQueue();
		c.queue.addAll(this.queue);
		return c;
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.  This means that
	 * both objects are of instances of the <code>LinkedQueue</code> class and contain
	 * the same data in the same order in the queue.
	 * 
	 * @param other The reference object with which to compare
	 * @return <code>true</code> if this object is the same as the other one; <code>false</code> otherwise
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
			
		if (other instanceof LinkedQueue && this.queue.equals(((LinkedQueue)other).queue)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns a string describing the object.
	 * 
	 * @return A string describing the object
	 */
	public String toString() {
		return "LinkedQueue" + queue.toString();
	}
	
	/**
	 * Generates a number that could be used to spread out the data in a hash.  This number
	 * should somewhat uniquely identifies the object, although uniqueness is not required.
	 * 
	 * @return A number used to somewhat uniquely identify the object
	 */
	public int hashCode() {
		return queue.hashCode();
	}
}
