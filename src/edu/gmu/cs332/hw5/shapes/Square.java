package edu.gmu.cs332.hw5.shapes;

/**
 * @author session
 */
public class Square extends Shape {
	Point bottomLeft;
	Point topRight;
	
	public Square() {
		bottomLeft = new Point();
		topRight = new Point();
	}
	
	public Square(Point bottomLeft, int width) {
		this.bottomLeft = bottomLeft;
		this.topRight = new Point(bottomLeft.getX() + width, bottomLeft.getY() + width);
	}
	
	public Square(Point bottomLeft, Point topRight) {
		this.bottomLeft = bottomLeft;
		this.topRight = topRight;
	}
	
	public int getWidth() {
		double dist = Geometry.distance(bottomLeft, new Point(bottomLeft.getX(), topRight.getY()));
		return (int)Math.floor(dist + 0.5f);
	}
	
	protected String getName() {
		return "SQUARE";
	}
	
	protected String getCoords() {
		return bottomLeft.toString() + " " + getWidth(); 
	}
}
