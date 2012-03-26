package edu.gmu.cs332.hw5;

import junit.framework.*;
import edu.gmu.cs332.hw5.shapes.*;

/**
 * JUnit tests for the TextShapeFactory class.
 * @author Tim Cheadle
 */
public class TextShapeFactoryTest extends TestCase {
	public TextShapeFactoryTest(String name) {
		super(name);
	}
	
	/**
	 * Tests whether or not the factory produces the correct shapes
	 */
	public void testMakeShape() {
		ShapeFactory sf = new TextShapeFactory("RED (0 1) (2 3)");
		Shape shape = sf.makeShape();

		assertTrue(shape instanceof Line);
	}
	
	public static Test suite() {
		return new TestSuite(TextShapeFactoryTest.class);
	}
}
