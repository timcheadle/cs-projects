package edu.gmu.cs332.hw5.shapes;

/**
 * Provides common geometric functions for shape geometry.
 * 
 * @author Tim Cheadle
 */
public class Geometry {
	/**
	 * Returns the distance between two two-dimensional points.
	 * 
	 * @param a The first point
	 * @param b The second point
	 * @return The distance between the points
	 */
	public static double distance(Point a, Point b) {
		return Math.abs(Math.sqrt(
			Math.pow((double)(a.getX() - b.getX()), 2.0) + Math.pow((double)(a.getY() - b.getY()), 2.0)
		));
	}
	
	/**
	 * Checks to see if a set of four verteces is a square.
	 * 
	 * @param bl The bottom left vertex
	 * @param tl The top left vertex
	 * @param tr The top right vertex
	 * @param br The bottom right vertex
	 * @return Whether or not the verteces form a square
	 */
	public static boolean isSquare(Point bl, Point tl, Point tr, Point br) {
		if (distance(bl, tl) == distance(tl, tr) &&
			distance(tl, tr) == distance(tr, br) &&
			distance(tr, br) == distance(br, bl))
		{
			return true;
		}
			
		return false;
	}
}
