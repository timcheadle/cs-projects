import junit.framework.*;

/*
 * Created on Apr 6, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */

/**
 * @author session
 */
public class CarTester extends TestCase {

	public CarTester(String name) {
		super(name);
	}

	public void testCompareTo() {
		Car a = new Car(
			"Mercedes-Benz",
			"C230K",
			2003,
			"White",
			192,
			200,
			4
		);
		
		Car b = new Car(
			"BMW",
			"C230K",
			2002,
			"White",
			192,
			200,
			4
		);
		 
		assertTrue("Car should be the same as itself", a.compareTo(a) == 0);
		assertTrue("Makes should descend", a.compareTo(b) > 0);
	}
	
	public static Test suite() {
		return new TestSuite(CarTester.class);
	}
}
