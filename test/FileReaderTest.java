import junit.framework.*;

/**
 * @author Faye Wong
 */
public class FileReaderTest extends TestCase{
	/**
	 * @param arg0
	 */
	public FileReaderTest(String arg0) {
		super(arg0);
	}
	
	public void testFileReader() {
		FileReader reader = new FileReader();
		String filename = "input2.txt";
		Scheduler scheduler = new FCFSScheduler();
		
		scheduler = reader.readFile(filename);
		
		assertTrue("Process 1 should have Arrival Time 0", scheduler.ProcessPeek(0).getTimeArrive() == 0);
		
		System.out.println(scheduler.processListToString());
		System.out.println("Process 1 PID: " + scheduler.ProcessPeek(0).getPid());
		System.out.println("Process 1 Arrival Time: " + scheduler.ProcessPeek(0).getTimeArrive());		
		System.out.println("CPU burst: " + scheduler.ProcessPeek(0).getBurst());
		System.out.println("IO burst: " + scheduler.ProcessPeek(0).getBurst(1));
		System.out.println("CPU burst: " + scheduler.ProcessPeek(0).getBurst(2));
		System.out.println("IO burst: " + scheduler.ProcessPeek(0).getBurst(3));
		System.out.println("CPU burst: " + scheduler.ProcessPeek(0).getBurst(4));
		System.out.println("IO burst: " + scheduler.ProcessPeek(0).getBurst(5));
		
		System.out.println("Process 4 PID: " + scheduler.ProcessPeek(3).getPid());		
		System.out.println("Process 4 Arrival Time: " + scheduler.ProcessPeek(3).getTimeArrive());
		System.out.println("Process 4 burst: " + scheduler.ProcessPeek(3).getBurst());		
		
		assertTrue("Process 2 should have Arrival Time 100", scheduler.ProcessPeek(1).getTimeArrive() == 100);
		
	
	}
}
