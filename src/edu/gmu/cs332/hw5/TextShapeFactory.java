package edu.gmu.cs332.hw5;

import java.util.*;
import java.awt.Color;
import edu.gmu.cs332.hw5.shapes.*;

/**
 * TestShapeFactory is a concrete factory that produces <code>Shape</code> objects.  It
 * generates these shapes based on information provided in a string containing the shape's
 * color and vertex coordinates.  Here is an example of a red line segment:
 * 
 * <code>RED (1 1) (2 2)</code>
 * 
 * And here is an example of a blue square:
 * 
 * <code>BLUE (0 0) (0 2) (2 2) (2 0)</code>
 * 
 * The coordinates should be ordered clockwise, starting at the lowest left vertex.
 * 
 * @author Tim Cheadle
 */
public class TextShapeFactory implements ShapeFactory {
	private String input;
	private Hashtable colorTable = new Hashtable();
	
	/**
	 * Creates a new factory using the given shape information
	 * 
	 * @param input The information about the shape
	 */
	public TextShapeFactory(String input) {
		this.input = input;
		
		// Initialize the color table
		colorTable.put("RED", Color.RED);
		colorTable.put("BLUE", Color.BLUE);
		colorTable.put("GREEN", Color.GREEN);
	}

	/**
	 * Makes a shape based on the information given.
	 * 
	 * @return The shape parsed from the information
	 * @see edu.gmu.cs332.hw5.shapes.ShapeFactory#makeShape()
	 */
	public Shape makeShape() {
		Shape shape = null;
		ArrayList points = new ArrayList();
		
		try {
			String delim = " ()";
			StringTokenizer st = new StringTokenizer(input, delim);
			String color = st.nextToken();
			
			// Put all of the points into a list
			while(st.hasMoreTokens()) {
				Point p = new Point(
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken())
				);
				points.add(p);
			}
			
			// Now figure out which shape it was based on the points
			switch (points.size()) {
				case 2:
					shape = new Line((Point)points.get(0), (Point)points.get(1));
					break;
				case 4:
					if (Geometry.isSquare(
						(Point)points.get(0),
						(Point)points.get(1), 
						(Point)points.get(2),
						(Point)points.get(3)))
					{
						shape = new Square((Point)points.get(0), (Point)points.get(2));
					} else {
						shape = new Rectangle((Point)points.get(0), (Point)points.get(2));
					}
					break;
			}
			
		} catch (NoSuchElementException n) {
			n.printStackTrace();
		}
		
		return shape;
	}

	/**
	 * Maps a string to it's corresponding <code>Color</code> in the color table.  It
	 * returns <code>null</code> if no such color is found.
	 * 
	 * @param color The string describing the color
	 * @return The color object
	 */
	private Color getColorMapping(String color) {
		if (colorTable.containsKey(color)) {
			return (Color)colorTable.get(color);
		}
		
		return null;
	}
}
