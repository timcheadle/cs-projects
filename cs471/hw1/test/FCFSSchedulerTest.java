import junit.framework.*;

/**
 * @author Faye Wong
 */
public class FCFSSchedulerTest extends TestCase{
	/**
	 * @param arg0
	 */
	public FCFSSchedulerTest(String arg0) {
		super(arg0);
	}
	
	public void testFCFS() {
		Scheduler scheduler = new FCFSScheduler();
		
		/**Process a = new Process(1, 0, 0);
		Process b = new Process(2, 0, 0);
		Process c = new Process(3, 0, 0);
		
		scheduler.enqueueCPU(a);
		
		assertTrue("First element should be Process 1", 
				   scheduler.cpuQueuePeek(0).getPid() == 1);
		scheduler.enqueueCPU(b);
		scheduler.enqueueCPU(c);
		
		assertTrue("Second element should be Process 2",
				   scheduler.cpuQueuePeek(1).getPid() == 2);
				   
		assertTrue("Third element should be Process 2",
				   scheduler.cpuQueuePeek(2).getPid() == 3);
						   
		scheduler.dequeueCPU();
		
		assertTrue("First element should be Process 2", 
				   scheduler.cpuQueuePeek(0).getPid() == 2);	
		
		System.err.println(scheduler.cpuQueueToString());
			   
		assertTrue("cpuQueue should be Ready Queue: [Process 2, Process 3]", 
				   scheduler.cpuQueueToString().equals("Ready Queue: [Process 2, Process 3]"));
		*/
		   
		String filename = "input1.txt";
		FileReader reader = new FileReader();
		scheduler = reader.readFile(filename);
		scheduler.run();
				   
		
	
	}
}
