/**
 * Queue provides an interface to a FIFO queue data structure.  It stores Objects
 * as to allow for generic usage among any application.
 * 
 * @author Tim Cheadle
 * @version 1.0
 * @since 1.0
 */
public interface Queue {
	/**
	 * Adds a new element onto the tail of the queue.
	 * 
	 * @param element The Object to add
	 */
	public void enqueue(Object element);
	
	/**
	 * Removes and returns the element on the head of the queue.
	 * 
	 * @return Object The element on the head of the queue
	 */
	public Object dequeue();
	
	/**
	 * Returns the number of elements in the queue.
	 * 
	 * @return int The size of the queue
	 */
	public int size();
	
	/**
	 * Returns the element on the head of the queue without removing it.
	 * 
	 * @return Object The element on the head of the queue
	 */
	public Object first();
	
	/**
	 * Returns the element on the tail of the queue without removing it.
	 * 
	 * @return Object The element on the tail of the queue
	 */
	public Object last();
}
