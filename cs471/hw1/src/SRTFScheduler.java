import java.lang.System;
import java.util.*;

/**
 * @author Faye Wong
 */
public class SRTFScheduler extends Scheduler {
	/**
	 * This process will enqueue a process on the CPU queue.  
	 *
	 */
	protected void enqueueCPU(Process process) {
		int index = 0;
		boolean insert = false;

		System.out.println(
			"Process "
				+ process.getPid()
				+ " has been added to the CPU queue.");

		if (cpuQueue.size() == 0) {
			cpuQueue.add(process);
			insert = true;
		} else {
			for (int i = 0; i < cpuQueue.size(); i++) {
				Process p = ((Process) cpuQueue.get(i));
				if (process.getBurst() < p.getBurst()) {
					index = i;
				}
			}
			cpuQueue.add(index, process);
			insert = true;
		}

		if (insert == false)
			cpuQueue.add(process);

		System.out.println(cpuQueueToString());

	}

	/**
	 * This method will dequeue a process from the CPU queue.
	 *
	 */
	protected void dequeueCPU() {
		cpuQueue.remove(0);
		System.out.println(cpuQueueToString());
	}

	protected void addNewArrivals() {
		// For each process in processList, see if it has arrived at time i.  If so, 
		// enqueue the process in the cpuQueue.
		for (Iterator i = processList.iterator(); i.hasNext();) {
			Process p = (Process) i.next();

			if (p.getTimeArrive() == clock) {
				printTime();
				System.out.println("Process " + p.getPid() + " has arrived.");

				p.setTimeRemaining(p.getBurst());

				// enqueueing the process in the cpuQueue
				enqueueCPU(p);	
			}
		}
	}

	protected void dispatchCPU() {
		Process p = (Process) cpuQueue.get(0);
		p.setState("CPU");

		System.out.println("Process " + p.getPid() + " has dispatched to CPU.");
		
		dequeueCPU();
		usingCPU = p.getPid() - 1;

		System.out.println("Process " + p.getPid() + " has started running.");
	}

	protected void dispatchIO() {
		Process p = ((Process) ioQueue.get(0));
		p.setState("IO"); // Set the state to IO

		System.out.println("Process " + p.getPid() + " dispatched to IO device.");

		// Set usingIO to the process executing
		dequeueIO(); // Remove the process from the ioQueue
		usingIO = p.getPid() - 1;		
		
		// Set the timeRemaining for the process
		// to the present burst time
		p.setTimeRemaining(p.getBurst());

		System.out.println("Process " + p.getPid() + " has started IO.");
	}
	
	protected void updateCPUWait() {
		for (Iterator i = cpuQueue.iterator(); i.hasNext();) {
			Process p = (Process) i.next();
			p.setWaitTime(p.getWaitTime() + 1);
		}
	}
	
	protected void updateIOWait() {
		for (Iterator i = ioQueue.iterator(); i.hasNext();) {
			Process p = (Process) i.next();
			p.setWaitTime(p.getWaitTime() + 1);
		}
	}
	
	protected void updateCPUTR() {
		if (usingCPU != -1) {
			Process p = (Process) processList.get(usingCPU);
			p.setTimeRemaining(p.getTimeRemaining() - 1);
			System.out.println(
				"Time Remaining/CPU: "
					+ ((Process) processList.get(usingCPU))
						.getTimeRemaining());
		}
	}
	
	protected void updateIOTR() {
		if (usingIO != -1) {
			((Process) processList.get(usingIO)).setTimeRemaining(
				((Process) processList.get(usingIO)).getTimeRemaining()- 1);
			System.out.println(
				"Time Remaining/IO: "
					+ ((Process) processList.get(0)).getTimeRemaining());
		}
	}

	/**
	 * This method will run the CPU scheduler (enqueue/dequeue processes, dipatch
	 * processes to the CPU/IO device, preempt processes if needed).
	 *
	 */
	public void run() {
		// Set CPU and IO to not busy
		usingCPU = -1;
		usingIO = -1;
		avgTurnaround = 0.0;
		avgWait = 0.0;

		System.out.println("Running SRTF.");

		// Run from cycle 0 to simLength
		while (clock <= simLength) {
			firstEvent = false;

			addNewArrivals();

			// Check to see if CPU and IO device have completed a process.

			// If there is a process in the CPU, see if the time remaining is greater than the
			// time remaining of the processes in queue.  If so, put that process back in queue and
			// run new process.
			if (usingCPU != -1) {
				Process presentProcess = ((Process) processList.get(usingCPU));

				// If the present CPU process is done, send it to the I/O queue
				if (presentProcess.getTimeRemaining() == 0) {
					printTime();

					presentProcess.removeBurst(); // Remove completed burst

					System.out.println("Process " + presentProcess.getPid() + " has completed CPU burst.");
					presentProcess.setState("IO"); // Change state

					enqueueIO(presentProcess); // Enqueue for IO

					usingCPU = -1; // Set the CPU to idle

					// If there are more process waiting for the CPU, dispatch one
					if (cpuQueue.size() != 0) {
						dispatchCPU();
					}
				}

				// Check for preemption; this means looking to see if any process in the
				// CPU queue has a shorter time remaining than the one currently using the CPU
				if (cpuQueue.size() != 0) {
					Process check = ((Process) cpuQueue.get(0));

					if (presentProcess.getTimeRemaining() > check.getTimeRemaining()) {
						printTime();

						System.out.println("Process " + presentProcess.getPid() + " being preempted.");

						//check.setTimeRemaining(check.getBurst());

						//presentProcess.addFirstBurst(presentProcess.getTimeRemaining());
						// Change value of first burst								

						System.out.println("Process " + presentProcess.getPid() + " tR " + presentProcess.getTimeRemaining());

						enqueueCPU(presentProcess);
						// Put presentProcess back in queue					

						System.out.println("usingcpu's time remaining: " + check.getTimeRemaining());
						System.out.println("PID " + check.getPid());

						dispatchCPU();

						System.out.println("usingcpu = " + usingCPU);
					}
				}
			} else if (usingCPU == -1) {
				// If the CPU isn't busy, simply run the next (if any) process from the
				// CPU queue in the CPU
				if (cpuQueue.size() != 0) {
					printTime();
					dispatchCPU();
				}
			}

			// If there is a process using the IO device, see if the process has completed
			// it's burst.  If so, set the IO device to not busy.
			if (usingIO != -1) {

				Process p = ((Process) processList.get(usingIO));
				if (p.getTimeRemaining() == 0) {
					printTime();

					System.out.println("Process " + p.getPid() + " has completed IO burst.");
					p.removeBurst(); //Remove completed burst

					// If process has another CPU burst, put into the CPU queue
					if (p.getBurst() != -1) {
						p.setState("CPU"); //Change state
						p.setTimeRemaining(p.getBurst());
						// Update timeRemaining

						// Enqueue for CPU or if there are no more bursts, the 
						// process has completed.
						System.out.println(
							"Process "
								+ p.getPid()
								+ " has been added to the CPU queue.");
						enqueueCPU(p);

						usingIO = -1;

					} else {
						avgTurnaround =
							avgTurnaround + (clock - p.getTimeArrive());
						System.out.println(
							"Process " + p.getPid() + " complete.");
					}

					if (ioQueue.size() != 0) {
						dispatchIO();
					}
				}

			} else if (usingIO == -1) {
				// If the IO device is not busy, take the first process in the ioQueue and 
				// dispatch it to the IO device.
				if (ioQueue.size() != 0) {
					printTime();
					
					dispatchIO();
				}
			}

			// Increment waiting times
			updateCPUWait();
			updateIOWait();

			// Decrement timeRemaining
			updateCPUTR();
			updateIOTR();
			
			// Increment clock
			clock++;

		} // End while() loop

		for (Iterator i = processList.iterator(); i.hasNext();) {
			Process p = (Process) i.next();
			//System.out.println("Wait Time: " + p.getWaitTime());
			avgWait = avgWait + p.getWaitTime();
		}
		avgWait = avgWait / processList.size();
		avgTurnaround = avgTurnaround / processList.size();

		System.out.println(
			"Average Waiting Time: "
				+ avgWait
				+ "\n"
				+ "Average Turnaround Time: "
				+ avgTurnaround);
	} // End run()
}