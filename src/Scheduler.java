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
 *The methods in this class are addProcess, which adds a process from the input file
 *to the simulation, enqueue/dequeue for CPU/IO, which will manage the waiting 
 *queues for the CPU and IO device, and a run process, which begins the simulation.
 *The enqueue/dequeue methods are abstract, as each algorithm handles the waiting
 *process differently.  The run method is also abstract as each algorithm will call
 *enqueue/dequeue at different times.
 */
public abstract class Scheduler {

	protected ArrayList cpuQueue;						//Processes waiting for the CPU
	protected ArrayList ioQueue;						//Processes waiting for the IO device
	protected ArrayList processList = new ArrayList();	//A list of all simulation processes
	protected int simLength;							//Length of the simulation
	protected int usingCPU;								//PID of the process using the CPU
	protected int usingIO;								//PID of the process using the IO device

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
	protected abstract void enqueueCPU(); 

	/**
	 * This method will dequeue a process from the CPU queue.
	 *
	 */
	protected abstract void dequeueCPU();
	
	/**
	 * This method will enqueue a process on the IO device queue.
	 *
	 */
	protected abstract void enqueueIO();
	
	/**
	 * This method will dequeue a process from the IO device queue.
	 *
	 */
	protected abstract void dequeueIO();

	/**
	 * This method will run the CPU scheduler (enqueue/dequeue processes, dipatch
	 * processes to the CPU/IO device, preempt processes if needed).
	 *
	 */
	public abstract void run();
}
