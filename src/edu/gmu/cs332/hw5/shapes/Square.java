package edu.gmu.cs332.hw5.shapes;

/**
 * A container class to store attributes about a square.
 * 
 * @author Tim Cheadle
 */
public class Square extends Shape {
	Point bottomLeft;
	Point topRight;
	
	/**
	 * Creates a new square with the default coordinates.
	 */
	public Square() {
		bottomLeft = new Point();
		topRight = new Point();
	}
	
	/**
	 * Creates a square with the given bottom-left vertex and width.
	 * 
	 * @param bottomLeft The square's bottom left vertex coordinates
	 * @param width The square's width
	 */
	public Square(Point bottomLeft, int width) {
		this.bottomLeft = bottomLeft;
		this.topRight = new Point(bottomLeft.getX() + width, bottomLeft.getY() + width);
	}
	
	/**
	 * Creates a square with the given bottom-left and top-right verteces.
	 * 
	 * @param bottomLeft The square's bottom-left vertex coordinates
	 * @param topRight The square's top-right vertex coordinates
	 */
	public Square(Point bottomLeft, Point topRight) {
		this.bottomLeft = bottomLeft;
		this.topRight = topRight;
	}
	
	/**
	 * Returns the square's width.
	 * 
	 * @return The square's width
	 */
	public int getWidth() {
		double hyp = Geometry.distance(bottomLeft, topRight);
		double w = hyp * Math.cos(Math.PI/4);
		return (int)Math.round(w);
	}
	
	/**
	 * Returns a description of the square's type.
	 * 
	 * @return The square's type description
	 */
	protected String getName() {
		return "SQUARE";
	}
	
	/**
	 * Returns the square's coordinates.
	 * 
	 * @return The square's coordinate description
	 */
	protected String getCoords() {
		return bottomLeft.toString() + " " + getWidth(); 
	}
}
