package edu.gmu.cs332.hw5.shapes;

/**
 * Point is a cloneable container class representing a point
 * in 2-dimensional Cartesian coordinates.
 * 
 * @author Tim Cheadle
 */
public class Point implements Cloneable {
	int x;
	int y;

	public Point() {
		x = 0;
		y = 0;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() { return x; }
	public int getY() { return y; }

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

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
	
	public int hashCode() {
		return (x << 16) | (y >> 16);
	}
	
	public Object clone() throws CloneNotSupportedException {
			return super.clone();
	}
}
