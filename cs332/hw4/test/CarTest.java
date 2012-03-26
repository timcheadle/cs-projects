import junit.framework.*;

/**
 * CarTest is a JUnit test suite for the Car class.
 * 
 * @author Tim Cheadle
 */
public class CarTest extends TestCase {
	/**
	 * Constructs the test case.
	 * 
	 * @param name The name of the test case
	 */
	public CarTest(String name) {
		super(name);
	}

	/**
	 * Tests Car.compareTo() to make sure that comparisons are not performed on non-<code>Car</code>
	 * classes, and ensure that the correct ordering of attributes is taking place.
	 */
	public void testCompareTo() {
		// Create two Car objects to compare
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
		
		// Try to compare to a non-Car object
		try {
			a.compareTo(new Integer(2));
		} catch (Exception e) {
			assertTrue("Compare to non-car object throws ClassCastException", e instanceof ClassCastException);
		}
		
		// Try to compare to a null object
		try {
			a.compareTo(null);
		} catch (Exception e) {
			assertTrue("Compare to null throws NullPointerException", e instanceof NullPointerException);
		}
		
		// Compare an obejct to itself
		assertTrue("Car should be the same as itself", a.compareTo(a) == 0);
		
		// Make sure attributes are being ordered correctly
		assertTrue("Makes of cars should descend (A-Z alphabetically)", a.compareTo(b) > 0);
	}
	
	/**
	 * Creates the test suite.
	 * 
	 * @return The test suite
	 */
	public static Test suite() {
		return new TestSuite(CarTest.class);
	}
}
