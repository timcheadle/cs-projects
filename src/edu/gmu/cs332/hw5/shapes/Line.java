package edu.gmu.cs332.hw5.shapes;

/**
 * A container class to store attributes about a line segment.
 * 
 * @author Tim Cheadle
 */
public class Line extends Shape {
	Point startPoint;
	Point endPoint;
	
	/**
	 * Constructs a line with the default coordinates.
	 */
	public Line() {
		startPoint = new Point();
		endPoint = new Point();
	}
	
	/**
	 * Constructs a line with the given coordinates.
	 * 
	 * @param start The starting point
	 * @param end The ending point
	 */
	public Line(Point start, Point end) {
		startPoint = start;
		endPoint = end;
	}
	
	/**
	 * Returns the line's starting point.
	 * 
	 * @return The starting point
	 */
	public Point getStart() {
		return startPoint;
	}
	
	/**
	 * Returns the line's ending point.
	 * 
	 * @return The ending point
	 */
	public Point getEnd() {
		return endPoint;
	}
	
	/**
	 * Returns a description of the line's type.
	 * 
	 * @return The line's type description
	 */
	protected String getName() {
		return "LINE";
	}
	
	/**
	 * Returns a description of the line's coordinates.
	 * 
	 * @return The description of the line's coordinates
	 */
	protected String getCoords() {
		return startPoint.toString() + " " + endPoint.toString(); 
	}
}
