package edu.gmu.cs332.hw5.shapes;


/**
 * @author session
 */
public class Square extends Shape {
	Point bottomLeft;
	int width;
	
	public Square() {
		bottomLeft = new Point();
		width = 0;
	}
	
	public Square(Point bottomLeft, int width) {
		this.bottomLeft = bottomLeft;
		this.width = width;
	}
	
	protected String getName() {
		return "SQUARE";
	}
	
	protected String getCoords() {
		return bottomLeft.toString() + " " + width; 
	}
}
