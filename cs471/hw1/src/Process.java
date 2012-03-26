import java.util.*;

/**
 * @author Faye Wong
 *
 * The Process class represents a Process Control Block.  It contains the time
 * of Arrival for a process (timeArrive), the time remaining for a burst of the
 * process to complete (timeRemaining), a list of the different burst values 
 * (burst), a state value (state), and a process id (pid).  The ArrayList burst 
 * acts as a queue of values of burst lengths.  It contains both CPU and IO bursts.
 */
public class Process {
	private int pid;				// process id
	private String state;		
	private int timeArrive;			// arrival time of the process
	private int timeRemaining;		// remaining time that a *burst* has to execute
	private int waitTime;			// time that the process has waited in the queue
	private ArrayList burst;		// list of burst times
						  
	
	
	/**
	 * Constructor
	 */
	public Process(){
		this.timeArrive = 0;
		this.timeRemaining = 0;
		this.burst = new ArrayList();
		this.state = "idle";
	}
	
	/**
	 * Constructor
	 */
	public Process(int pid){
		this.pid = pid;
		this.timeArrive = 0;
		this.timeRemaining = 0;
		this.burst = new ArrayList();
		this.state = "idle";
	}
	
	/**
	 * Constructor
	 */
	public Process(int pid, int timeArrive, int timeRemaining){
		this.pid = pid;
		this.timeArrive = timeArrive;
		this.timeRemaining = timeRemaining;
		this.burst = new ArrayList();
		this.state = "idle";
	}
	
	/**
	* @return the value of the first element of the burst queue.  This method
	* will return -1 if the value of burst is null (if the burst queue is empty).
	*/
	public int getBurst() {
		if (burst.size() == 0)
			return -1;
		else
			return ((Integer)burst.get(0)).intValue();
	}
	
	/**
	* @return the value of the element at "index" of the burst queue.  This method
	* will return -1 if the value of burst is null (if the burst queue is empty).
	*/
	public int getBurst(int index) {
		if (burst.size() == 0)
			return -1;
		else
			return ((Integer)burst.get(index)).intValue();
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
	public void setTimeArrive(int i) {
		timeArrive = i;
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
	
	public void addFirstBurst(int i) {
		burst.add(0, new Integer(i));
	}
	
	/**
	 * This method dequeues the first element of the burst queue.
	 */
	public void removeBurst() {
		burst.remove(0);
	}
	/**
	 * @return process id
	 */
	public int getPid() {
		return pid;
	}

	/**
	 * @return state
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

	/**
	 * @return waitTime
	 */
	public int getWaitTime() {
		return waitTime;
	}

	/**
	 * @param waitTime
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

}
	
	