package edu.gmu.cs332.hw5.shapes;

/**
 * @author session
 */
public abstract class Shape {
	protected abstract String getName();
	protected abstract String getCoords();
	
	public String toString() {
		return getName() + ": " + getCoords();
	}
}
