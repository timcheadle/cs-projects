package edu.gmu.cs332.hw5.shapes;

import java.awt.Color;

/**
 * Shape is a generic template interface for various shapes.  The template behaviors
 * allow for a string description of the shape to be returned.
 * 
 * @author Tim Cheadle
 * @todo Add a behavior to draw the shape to a Swing container
 */
public abstract class Shape {
	protected Color color = new Color(0, 0, 0);
	
	protected abstract String getName();
	protected abstract String getCoords();
	
	public String toString() {
		return getName() + ": " + getCoords();
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(int r, int g, int b) {
		color = new Color(r, g, b);
	}
}
