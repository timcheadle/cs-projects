package edu.gmu.cs332.hw5.shapes;


/**
 * @author session
 */
public class Line extends Shape {
	Point startPoint;
	Point endPoint;
	
	public Line() {
		startPoint = new Point();
		endPoint = new Point();
	}
	
	public Line(Point start, Point end) {
		startPoint = start;
		endPoint = end;
	}
	
	protected String getName() {
		return "LINE";
	}
	
	protected String getCoords() {
		return startPoint.toString() + " " + endPoint.toString(); 
	}
}
