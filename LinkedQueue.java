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
	 * @see Queue#enqueue(java.lang.Object)
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

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
			
		if (other instanceof LinkedQueue) {
			/*
			for (Iterator i = this.queue.iterator(); i.hasNext(); ) {
				for (Iterator j = other.queue.iterator(); j.hasNext(); ) {
					if (!i.next().equals(j.next())) {
						return false;
					}
				}
			}
			*/
			
			if (this.queue.equals(((LinkedQueue)other).queue)) {
				return true;
			}
		}
		
		return false;
	}
	
	public String toString() {
		return "LinkedQueue with " + this.size() + " elements";
	}
	
	public int hashCode() {
		return queue.hashCode();
	}
}
