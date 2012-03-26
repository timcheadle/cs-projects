import junit.framework.*;

/**
 * @author Faye Wong
 */
public class SJFSchedulerTest extends TestCase{
	/**
	 * @param arg0
	 */
	public SJFSchedulerTest(String arg0) {
		super(arg0);
	}
	
	public void testSJFScheduler() {
		
	Scheduler scheduler = new SJFScheduler();	
	/**Process a = new Process(1);
	Process b = new Process(2);
	Process c = new Process(3);
	
	a.addBurst(30);
	b.addBurst(10);
	c.addBurst(20);

	scheduler.enqueueCPU(a);
	
	assertTrue("Process 1 is inserted.", scheduler.cpuQueuePeek(0).getPid() == 1);
	
	scheduler.enqueueCPU(b);
	scheduler.enqueueCPU(c);
	
	System.out.println(scheduler.cpuQueuePeek(0).getPid());
	System.out.println(scheduler.cpuQueuePeek(1).getPid());
	System.out.println(scheduler.cpuQueuePeek(2).getPid());
	
	assertTrue("Process 2 is inserted in front of Process 1", scheduler.cpuQueuePeek(0).getPid() == 2);
	
	scheduler.dequeueCPU();
	
	assertTrue("Process 3 is at the front of the queue", scheduler.cpuQueuePeek(0).getPid() == 3);
	*/
	
	String filename = "input2.txt";
	FileReader reader = new FileReader();
	scheduler = reader.readFile(filename);
	scheduler.run();
	}
	
	

}
