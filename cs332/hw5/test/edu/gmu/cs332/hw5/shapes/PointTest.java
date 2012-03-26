package edu.gmu.cs332.hw5.shapes;

import junit.framework.*;

/**
 * JUnit tests for the Point class.
 * 
 * @author Tim Cheadle
 */
public class PointTest extends TestCase {
	public PointTest(String name) {
		super(name);
	}
	
	/**
	 * Tests whether the Point can take two integers and properly behave.
	 */
	public void testIntegerPoint() {
		Point p = new Point(1, 2);
		
		assertTrue(p.getX() == 1);
		assertTrue(p.getY() == 2);
		assertTrue(p.toString().equals("(1, 2)"));
	}
	
	/**
	 * Tests whether the Point can take a string description and properly behave.
	 */
	public void testStringPoint() {
		Point p = new Point("(1, 2)");
		
		assertTrue(p.getX() == 1);
		assertTrue(p.getY() == 2);
		assertTrue(p.toString().equals("(1, 2)"));
	}
	
	public static Test suite() {
		return new TestSuite(PointTest.class);
	}
}
