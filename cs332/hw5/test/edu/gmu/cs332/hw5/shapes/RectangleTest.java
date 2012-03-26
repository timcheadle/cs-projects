package edu.gmu.cs332.hw5.shapes;

import junit.framework.*;

/**
 * JUnit tests for the Rectangle class.
 * 
 * @author Tim Cheadle
 */
public class RectangleTest extends TestCase {
	public RectangleTest(String name) {
		super(name);
	}
	
	/**
	 * Tests whether the rectangle is reliably storing and returning attributes.
	 */
	public void testLine() {
		Point a = new Point(1, 2);
		Point b = new Point(10, 12);
		Rectangle r = new Rectangle(a, b);
		
		assertTrue(r.getWidth() == 9);
		assertTrue(r.getHeight() == 10);
		assertTrue(r.toString().equals("RECTANGLE (1, 2) 10 9"));
	}
	
	public static Test suite() {
		return new TestSuite(RectangleTest.class);
	}
}
