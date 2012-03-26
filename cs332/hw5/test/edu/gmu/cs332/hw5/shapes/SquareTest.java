package edu.gmu.cs332.hw5.shapes;

import junit.framework.*;

/**
 * JUnit tests for the Square class.
 * 
 * @author Tim Cheadle
 */
public class SquareTest extends TestCase {
	public SquareTest(String name) {
		super(name);
	}
	
	/**
	 * Tests whether the square is reliably storing and returning attributes.
	 */
	public void testLine() {
		Point a = new Point(1, 2);
		Point b = new Point(10, 11);
		Square s = new Square(a, b);
		
		assertTrue(s.getWidth() == 9);
		assertTrue(s.toString().equals("SQUARE (1, 2) 9"));
	}
	
	public static Test suite() {
		return new TestSuite(SquareTest.class);
	}
}
