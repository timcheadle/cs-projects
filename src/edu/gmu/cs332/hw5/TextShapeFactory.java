package edu.gmu.cs332.hw5;

import java.util.*;
import java.awt.Color;

import edu.gmu.cs332.hw5.shapes.*;

/**
 * @author session
 */
public class TextShapeFactory implements ShapeFactory {
	private String input;
	private Hashtable colorTable = new Hashtable();
	
	public TextShapeFactory(String input) {
		this.input = input;
		
		// Initialize the color table
		colorTable.put("RED", Color.RED);
		colorTable.put("BLUE", Color.BLUE);
		colorTable.put("GREEN", Color.GREEN);
	}

	/**
	 * @see edu.gmu.cs332.hw5.shapes.ShapeFactory#getShape()
	 */
	public Shape makeShape() {
		Shape shape = null;
		ArrayList points = new ArrayList();
		
		try {
			StringTokenizer st = new StringTokenizer(input);
			String color = st.nextToken();
			
			// Put all of the points into a list
			while(st.hasMoreTokens()) {
				points.add(new Point(st.nextToken()));
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
		return null;
	}

	private Color getColorMapping(String color) {
		if (colorTable.containsKey(color)) {
			return (Color)colorTable.get(color);
		}
		
		return null;
	}
}
