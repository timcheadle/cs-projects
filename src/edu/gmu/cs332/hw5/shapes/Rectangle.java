package edu.gmu.cs332.hw5.shapes;

/**
 * A container class to store attributes about a rectangle.
 * 
 * @author Tim Cheadle
 */
public class Rectangle extends Shape {
	Point bottomLeft;
	Point topRight;
	
	/**
	 * Creates a new rectangle with the default coordinates.
	 */
	public Rectangle() {
		bottomLeft = new Point();
		topRight = new Point();
	}
	
	/**
	 * Creates a rectangle with the given bottom-left vertex, height and width.
	 * 
	 * @param bottomLeft The rectangle's bottom left vertex coordinates
	 * @param height The rectangle's height
	 * @param width The rectangle's width
	 */
	public Rectangle(Point bottomLeft, int height, int width) {
		this.bottomLeft = bottomLeft;
		this.topRight = new Point(bottomLeft.getX() + width, bottomLeft.getY() + height);
	}
	
	/**
	 * Creates a rectangle with the given bottom-left and top-right verteces.
	 * 
	 * @param bottomLeft The rectangle's bottom-left vertex coordinates
	 * @param topRight The rectangle's top-right vertex coordinates
	 */
	public Rectangle(Point bottomLeft, Point topRight) {
		this.bottomLeft = bottomLeft;
		this.topRight = topRight;
	}
	
	/**
	 * Returns the rectangle's height.
	 * 
	 * @return The rectangle's height
	 */
	public int getHeight() {
		double dist = Geometry.distance(bottomLeft, new Point(bottomLeft.getX(), topRight.getY()));
		return (int)Math.floor(dist + 0.5f);
	}
	
	/**
	 * Returns the rectangle's width.
	 * 
	 * @return The rectangle's width
	 */
	public int getWidth() {
		double dist = Geometry.distance(bottomLeft, new Point(topRight.getX(), bottomLeft.getY()));
		return (int)Math.floor(dist + 0.5f);
	}
	
	/**
	 * Returns a description of the rectangle's type.
	 * 
	 * @return The rectangle's type description
	 */
	protected String getName() {
		return "RECTANGLE";
	}
	
	/**
	 * Returns the rectangle's coordinates.
	 * 
	 * @return The rectangle's coordinate description
	 */
	protected String getCoords() {
		return bottomLeft.toString() + " " + getHeight() + " " + getWidth(); 
	}
}
