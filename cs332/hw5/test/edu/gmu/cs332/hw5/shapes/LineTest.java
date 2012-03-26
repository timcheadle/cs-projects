package edu.gmu.cs332.hw5.shapes;

import junit.framework.*;

/**
 * JUnit tests for the Line class.
 * 
 * @author Tim Cheadle
 */
public class LineTest extends TestCase {
	public LineTest(String name) {
		super(name);
	}
	
	/**
	 * Tests whether the line is reliably storing and returning attributes.
	 */
	public void testLine() {
		Point a = new Point(1, 2);
		Point b = new Point(10, 22);
		Line l = new Line(a, b);
		
		assertTrue(l.getStart().equals(a));
		assertTrue(l.getEnd().equals(b));
		assertTrue(l.toString().equals("LINE (1, 2) (10, 22)"));
	}
	
	public static Test suite() {
		return new TestSuite(LineTest.class);
	}
}
