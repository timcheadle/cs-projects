import java.util.*;
/**
 * @author Faye Wong
 *
 */
public class Simulation {
	int clock;		//Variable representing the present cycle of the CPU
					//"clock".
	
	int simLen;		//Variable to store the length of the simulation from 
					//the input file.
			
	private ArrayList processList;	//List of Processes that are read from the input file.
	
	private ArrayList ready;	//List of Processes ready for CPU execution.
	
	private ArrayList io;		//List of Processes ready for IO execution.
	
	private Process cpuObject;	//Object representing the current process executing 
							//on the CPU.
	
	private Process ioObject;	//Object representing the current process executing 
							//on the IO device.
		
	//From 0 to simLen, check each element of the list of Processes to see if there
	//is a process requesting the CPU or a process requesting the I/O device.

		//Goes through each element of processList
		//If timeArrive == clock
		//	If isCPU==True
		//		enqueue object in ready queue
		//	Else
		//		enqueue object in io queue
		//	
		//output queues
		
		//Dispatch to CPU
		//If CPU not busy (if cpuObject.timeRemaining == 0)
		//	If isCPU=False
		//		exit with error
		//	Else
		//		cpuObject = top of ready queue
		//		cpuObject.timeRemaining = cpuObject.getBurst();
		//		cpuObject.dequeue();
		//		cpuObject.setCPU(FALSE);
		//		cpuBusy = TRUE;
		//Output that process has been dispatched
		//Output ready queue
		
		//Dispatch to IO device
		//If IO not busy(if ioObject.timeRemaining == 0)
		//	If isCPU==True
		//		exit with error
		//	Else
		//		ioObject = top of io queue
		//		ioObject.timeRemaining = ioObject.getBurst();
		//		ioObject.dequeue();
		//		ioObject.setCPU(TRUE);
		//		ioBusy = TRUE;
		//Output that process has been dispatched
		//Output io queue
		
		//Increment clock
		//If cpuBusy==TRUE
		//	decrement cpuObject.timeRemaining;
		//If ioBusy==TRUE
		//	decrement ioObject.timeRemaining;
		
}
