import junit.framework.*;


/**
 * @author Faye Wong
 */
public class ProcessTest extends TestCase {
	/**
	 * @param arg0
	 */
	public ProcessTest(String arg0) {
		super(arg0);
	}

	public void testProcess() {
		Process a = new Process();
		Process b = new Process(100, 30);
		
		assertTrue("Default Arrival Time should be 0", a.getTimeArrive() == 0);
		assertTrue("Default Remaining Time should be 0", a.getTimeRemaining() == 0);
		
		assertTrue("Time Arrive for b should be 100", b.getTimeArrive() == 100);
		assertTrue("Time Remaining for b should be 30", b.getTimeRemaining() == 30);
		
		assertTrue("Value of first element of a.burst should be -1 (empty list)", 
				   a.getBurst() == -1);
				   
		b.addBurst(30);
		
		assertTrue("Value of first element of b.burst should be 30", 
				   b.getBurst() == 30);
				
		b.addBurst(45);
		   
		assertTrue("Value of first element of b.burst should be 30", 
				   b.getBurst() == 30);
		
		b.removeBurst();
		
		assertTrue("Value of first element of b.burst should be 30", 
				   b.getBurst() == 45);
				   
		a.setTimeRemaining(100);
		a.setState("CPU");
		a.setPid(1);
		
		assertTrue("Time Remaining for a should be 100", a.getTimeRemaining() == 100);
		assertTrue("Process state of a should be CPU", a.getState() == "CPU");
		assertTrue("Process ID for a should be 1", a.getPid() == 1);
	}
	
}
