import java.util.*;

/**
 * @author Faye Wong
 * 
 * The Scheduler class will run the simulation for CPU scheduling.  It has a queue
 * for both the CPU and IO device (cpuQueue, ioQueue) which holds Process objects
 * that are waiting to become active.  It has a process list (processList) that was
 * generated from the input file; these are all of the processes involved in the 
 * simulation.  
 *
 * The methods in this class are addProcess, which adds a process from the input file
 * to the simulation, enqueue/dequeue for CPU/IO, which will manage the waiting 
 * queues for the CPU and IO device, and a run process, which begins the simulation.
 * The enqueue/dequeue methods for the CPU are abstract, as each algorithm handles the waiting
 * process differently.  The enqueue/dequeue methods for IO are defined here, as each 
 * algorithm will be running FCFS for IO.  The run method is also abstract as each algorithm will call
 * enqueue/dequeue at different times.
 */
public abstract class Scheduler {

	protected ArrayList cpuQueue = new ArrayList();		//Processes waiting for the CPU (ready queue)
	protected ArrayList ioQueue = new ArrayList();		//Processes waiting for the IO device (device queue)
	protected ArrayList processList = new ArrayList();	//A list of all simulation processes
	protected int simLength;							//Length of the simulation
	protected int usingCPU;								//PID of the process using the CPU(-1 if not busy)
	protected int usingIO;								//PID of the process using the IO device(-1 if not busy)
	protected int clock;								//CPU clock variable
	protected boolean firstEvent;						//The first event in a clock cycle
	protected double avgWait;								//Average wait time for each process
	protected double avgTurnaround;						//Average turnaround time for each process
	/**
	 * This method will add a process to be simulated.
	 * 
	 * @param process 
	 */
	public void addProcess(Process process) {
		processList.add(process);
	}

	/**
	 * This process will enqueue a process on the CPU queue.  
	 *
	 */
	protected abstract void enqueueCPU(Process process); 

	/**
	 * This method will dequeue a process from the CPU queue.
	 *
	 */
	protected abstract void dequeueCPU();
	
	/**
	 * This method will enqueue a process on the IO device queue using the 
	 * FCFS algorithm.
	 *
	 */
	protected void enqueueIO(Process process) {
		ioQueue.add(process);
		System.out.println(ioQueueToString());
	}
	
	/**
	 * This method will dequeue a process from the IO device queue using the
	 * FCFS algorithm.
	 *
	 */
	protected void dequeueIO() {
		ioQueue.remove(0);
		System.out.println(ioQueueToString());
	}

	/**
	 * This method will run the CPU scheduler (enqueue/dequeue processes, dipatch
	 * processes to the CPU/IO device, preempt processes if needed).
	 *
	 */
	public abstract void run();

	public Process ProcessPeek(int index) {
		return (Process)processList.get(index);
	}

	public Process cpuQueuePeek(int index) {
		return (Process)cpuQueue.get(index);
	}

	public Process ioQueuePeek(int index) {
		return (Process)ioQueue.get(index);
	}

	public void printTime() {
		if (firstEvent == false) {
			System.out.print("\nTime = " + clock + ": ");
			firstEvent = true;
		}
	}

	public String cpuQueueToString() {
		
		StringBuffer queueString = new StringBuffer();
		queueString.append("CPU Queue: [");

		for(Iterator i = cpuQueue.iterator(); i.hasNext(); ) {
			Process p = (Process)i.next();
			queueString.append("Process ");
			queueString.append(p.getPid());
			if (i.hasNext())
				queueString.append(", ");			
		}	
		
		queueString.append("]");
		return queueString.toString();
	}

	public String ioQueueToString() {
		
		StringBuffer queueString = new StringBuffer();
		queueString.append("IO Device Queue: [");

		for(Iterator i = ioQueue.iterator(); i.hasNext(); ) {
			Process p = (Process)i.next();
			queueString.append("Process ");
			queueString.append(p.getPid());
			if (i.hasNext())
				queueString.append(", ");			
		}	

		queueString.append("]");
		return queueString.toString();
	}

	public String processListToString() {
		
		StringBuffer queueString = new StringBuffer();
		queueString.append("Process List: [");

		for(Iterator i = processList.iterator(); i.hasNext(); ) {
			Process p = (Process)i.next();
			queueString.append("Process ");
			queueString.append(p.getPid());
			if (i.hasNext())
				queueString.append(", ");			
		}	

		queueString.append("]");
		return queueString.toString();
	}

}
