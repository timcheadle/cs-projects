package edu.gmu.cs332.hw5.shapes;

/**
 * Provides a common interface through which to access shape factories, following the
 * Abstract Factory design pattern.  This interface can be used to build concrete factories
 * that allow different methods of shape creation.
 *  
 * @author Tim Cheadle
 */
public interface ShapeFactory {
	/**
	 * Generates a new shape.
	 * 
	 * @return A shape
	 */
	public Shape makeShape();
}
