package edu.gmu.cs332.hw5.shapes;

/**
 * @author session
 */
public class Geometry {
	public static double distance(Point a, Point b) {
		return Math.abs(Math.sqrt(
			Math.pow((double)(a.getX() - b.getX()), 2.0) + Math.pow((double)(a.getY() - b.getY()), 2.0)
		));
	}
	
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
