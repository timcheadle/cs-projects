package edu.gmu.cs332.hw5.shapes;

import java.awt.Color;

/**
 * Shape is a generic template interface for various shapes.  The template behaviors
 * allow for a string description of the shape to be returned.  The shape also contains
 * information about it's color.
 * 
 * @author Tim Cheadle
 */
public abstract class Shape {
	protected Color color = new Color(0, 0, 0);
	
	/**
	 * Returns a description of the shape's type.
	 * 
	 * @return The shape's type
	 */
	protected abstract String getName();
	
	/**
	 * Returns a description of the shape's coordinates.
	 * 
	 * @return The shape's coordinates
	 */
	protected abstract String getCoords();
	
	/**
	 * Returns a description of the shape.
	 * 
	 * @return The shape's description
	 */
	public String toString() {
		return getName() + " " + getCoords();
	}
	
	/**
	 * Returns the shape's color.
	 * 
	 * @return The shape's color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Set's the shape's color via RGB values.
	 * @param r The red component of the color (0 - 255)
	 * @param g The green component of the color (0 - 255)
	 * @param b The blue component of the color (0 - 255)
	 */
	public void setColor(int r, int g, int b) {
		color = new Color(r, g, b);
	}
}
