package edu.gmu.cs332.hw5.shapes;


/**
 * @author session
 */
public class Rectangle extends Shape {
	Point bottomLeft;
	int height;
	int width;
	
	public Rectangle() {
		bottomLeft = new Point();
		height = 0;
		width = 0;
	}
	
	public Rectangle(Point bottomLeft, int height, int width) {
		this.bottomLeft = bottomLeft;
		this.height = height;
		this.width = width;
	}
	
	protected String getName() {
		return "RECTANGLE";
	}
	
	protected String getCoords() {
		return bottomLeft.toString() + " " + height + " " + width; 
	}
}
