package edu.gmu.cs332.hw5.shapes;


/**
 * @author session
 */
public class Rectangle extends Shape {
	Point bottomLeft;
	Point topRight;
	
	public Rectangle() {
		bottomLeft = new Point();
		topRight = new Point();
	}
	
	public Rectangle(Point bottomLeft, int height, int width) {
		this.bottomLeft = bottomLeft;
		this.topRight = new Point(bottomLeft.getX() + width, bottomLeft.getY() + height);
	}
	
	public Rectangle(Point bottomLeft, Point topRight) {
		this.bottomLeft = bottomLeft;
		this.topRight = topRight;
	}
	
	public int getHeight() {
		double dist = Geometry.distance(bottomLeft, new Point(bottomLeft.getX(), topRight.getY()));
		return (int)Math.floor(dist + 0.5f);
	}
	
	public int getWidth() {
		double dist = Geometry.distance(bottomLeft, new Point(topRight.getX(), bottomLeft.getY()));
		return (int)Math.floor(dist + 0.5f);
	}
	
	protected String getName() {
		return "RECTANGLE";
	}
	
	protected String getCoords() {
		return bottomLeft.toString() + " " + getHeight() + " " + getWidth(); 
	}
}
