import java.util.*;

/**
 * @author Faye Wong
 *
 * The Process class represents a Process Control Block.  It contains the time
 * of Arrival for a process (timeArrive), the time remaining for a burst of the
 * process to complete (timeRemaining), a list of the different burst values 
 * (burst), and a boolean value (cpuBurstNext) to tell you what the type of the next 
 * burst is (CPU or IO).
 * 
 * The ArrayList "burst" acts as a queue of burst values.  Each time a burst 
 * executes on the CPU or IO device, it is dequeued from the list.  For 
 * preemptive scheduling, a burst may be put back onto the queue to wait until
 * the next time the process executes.
 */
public class Process {
	private int pid;				// process id
	private String state;		
	private int timeArrive;			// arrival time of the process
	private int timeRemaining;		// remaining time that a *burst* has
									// to execute
	private ArrayList burst;		// list of burst times
						  
	
	
	/**
	 * Constructor
	 * @return new Process object
	 */
	public Process(){
		this.timeArrive = 0;
		this.timeRemaining = 0;
		this.burst = null;
		
	}
	
	/**
	* @return the value of the first element of the burst queue.
	*/
	public int getBurst() {
		return ((Integer)burst.get(0)).intValue();
	}

	/**
	 * @return the value of timeArrive.
	 */
	public int getTimeArrive() {
		return timeArrive;
	}

	/**
	 * @return the value of timeRemaining.  Remember that timeRemaining 
	 * is the time remaining for a burst, not the total remaining time.
	 */
	public int getTimeRemaining() {
		return timeRemaining;
	}

	/**
	 * This method sets the value for timeRemaining.
	 * 
	 * @param i
	 */
	public void setTimeRemaining(int i) {
		timeRemaining = i;
	}

	/**
	 * This method enqueues a new burst value onto the burst queue.
	 * 
	 * @param i
	 */
	public void addBurst(int i) {
		burst.add(new Integer(i));
	}
	
	/**
	 * This method dequeues the first element of the burst queue.
	 */
	public void removeBurst() {
		burst.remove(0);
	}
	/**
	 * @return
	 */
	public int getPid() {
		return pid;
	}

	/**
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param i
	 */
	public void setPid(int i) {
		pid = i;
	}

	/**
	 * @param string
	 */
	public void setState(String string) {
		state = string;
	}

}
	
	