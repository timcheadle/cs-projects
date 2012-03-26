package XMLApplet;

import java.util.*;
import java.awt.*;

/**
 * PieChart allows the simple creation of pie charts using AWT.  It
 * gets the data to chart from a {@link HashMap}, and then all you need to tell
 * it are the pixel locations where you want to plot the chart and it's legend.
 * <p>
 * It changes the {@link Color} of each pie slice according to the following order:
 * <ol>
 * <li><code>Color.green</code>
 * <li><code>Color.yellow</code>
 * <li><code>Color.red</code>
 * <li><code>Color.orange</code>
 * <li><code>Color.cyan</code>
 * <li><code>Color.pink</code>
 * <li><code>Color.magenta</code>
 * <li><code>Color.lightGray</code>
 * <li><code>Color.white</code>
 * </ol>
 * <p>
 * If the number of slices exceeds the number of colors, the list cycles to the beginning.
 * 
 * @author Tim Cheadle
 */
public class PieChart {
	private HashMap data;
	
	private Color colors[] = new Color[9];
	
	/**
	 * Initializes the list of available slice colors and stores the series to chart.
	 *   
	 * @param series Data to chart
	 */
	public PieChart(HashMap series) {
		data = series;
		
		colors[0] = Color.green;
		colors[1] = Color.yellow;
		colors[2] = Color.red;
		colors[3] = Color.orange;
		colors[4] = Color.cyan;
		colors[5] = Color.pink;
		colors[6] = Color.magenta;
		colors[7] = Color.lightGray;
		colors[8] = Color.white;
	}
	
	/**
	 * Draws the pie chart and it's legend at the given coordinates using the given
	 * width and height.
	 * 
	 * @param g       {@link Graphics} object to draw to
	 * @param x       X coordinate of the middle of the pie chart circle
	 * @param y       Y coordinate of the middle of the pie chart circle 
	 * @param width   Width of the pie chart circle 
	 * @param height  Height of the pie chart circle
	 * @param legendX X coordinate of the top-left corner of the legend rectangle
	 * @param legendY Y coordinate of the top-left corner of the legend rectangle
	 * @see   Graphics
	 */
	public void draw(Graphics g, int x, int y, int width, int height, int legendX, int legendY) {
		// First we have to calculate the total of all the values in the hash
		// (which we're assuming are Integers)
		Collection values = data.values();
		int total = 0;
		for (Iterator i = values.iterator(); i.hasNext(); ) {
			total += ((Integer)i.next()).intValue(); 
		}
		
		// Now we have to draw each arc, with each local angle defined as follows:
		//    angle = (value / total) * 360;
		// This means that each slice will represent it's percentage of the circle
		Set keys = data.keySet();
		int startAngle = 0; // Angle to start the arc at (rotates)
		int color = 0; // Keep an index in our color array to change colors
		int curLegendY = legendY;
		
		for (Iterator i = keys.iterator(); i.hasNext(); ) {
			String k = i.next().toString();
			
			System.out.println("key = " + k);
			
			// If the key doesn't have a value pairing, don't draw an arc
			// because doing so would be very bad (and non-sensical)
			if (data.get(k) == null) {
				continue;
			}
			
			// Calculate our percents and angles
			int value = ((Integer)data.get(k)).intValue();
			double percent = (double)value / (double)total;
			int angle = (int)(percent * 360);
			
			System.out.println(
				"Drawing... value = " + (double)value +
				", total = " + (double)total +
				", percent = " + percent +
				", startAngle = " + startAngle +
				", angle = " + angle
			);
			
			// Now draw the arc
			g.setColor(colors[color]);
			g.fillArc(x, y, width, height, startAngle, angle);
			color++;
			startAngle += angle;
			
			// Now put a line in the legend
			g.setFont(new Font("Helvetica", Font.BOLD, 14));
			g.drawString(k, legendX, curLegendY);
			curLegendY += 14;
		}
	}
}
