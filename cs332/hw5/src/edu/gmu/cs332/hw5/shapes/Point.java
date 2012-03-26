package edu.gmu.cs332.hw5.shapes;

import java.util.*;

/**
 * Point is a cloneable container class representing a point
 * in two-dimensional Cartesian coordinates.
 * 
 * @author Tim Cheadle
 */
public class Point implements Cloneable {
	int x;
	int y;

	/**
	 * Constructs a point with default values.
	 */
	public Point() {
		x = 0;
		y = 0;
	}

	/**
	 * Constructs a point with the given coordinates.
	 * 
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructs a point based on a string description such as <code>"(1, 2)"</code>.
	 * 
	 * @param point The string description of the point
	 */
	public Point(String point) {
		try {
			String delimiters = "(), ";
			StringTokenizer st = new StringTokenizer(point, delimiters);
			
			this.x = Integer.parseInt(st.nextToken());
			this.y = Integer.parseInt(st.nextToken());
		} catch (NoSuchElementException n) {
			n.printStackTrace();
		}
	}

	/**
	 * Returns the point's x-coordinate.
	 * 
	 * @return The x-coordinate
	 */
	public int getX() { return x; }
	
	/**
	 * Returns the point's y-coordinate.
	 * 
	 * @return The y-coordinate
	 */
	public int getY() { return y; }

	/**
	 * Returns a string description of the point
	 * 
	 * @return The description of the point
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	/**
	 * Checks to see if the point is equal to the other point.
	 * 
	 * @return <code>true</code> if the points are equal, <code>false</code> if otherwise
	 */
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (other instanceof Point) {
			if (this.x != ((Point) other).getX())
				return false;
			if (this.y != ((Point) other).getY())
				return false;
			return true;
		}

		return false;
	}
	
	/**
	 * Returns a somwhat unique hash code for this point
	 * 
	 * @return The hash code
	 */
	public int hashCode() {
		return (x << 16) | (y >> 16);
	}
	
	/**
	 * @see java.lang.Cloneable#clone()
	 */
	public Object clone() throws CloneNotSupportedException {
			return super.clone();
	}
}
