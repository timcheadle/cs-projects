/*
 * Created on Feb 27, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package XMLApplet;

import java.awt.*;
import java.lang.Math;

/**
 * @author session
 */
public class PieChart extends Canvas {
	PieItem[] wedges;   // The data for the pie
	double total = 0.0; // Total of all wedges

	Color wedgeColor[] = new Color[7];

	int pieViewSize; // size of square to incise pie into
	static final int pieBorderWidth = 10; // pixels from circle edge to side
	int pieDiameter; // derived from the view size
	int pieRadius; // ..
	int pieCenterPos; // ..

	PieChart(int asize, PieItem[] avec) { // constructor
		this.pieViewSize = asize; // copy args
		this.wedges = avec;

		pieDiameter = pieViewSize - 2 * pieBorderWidth;
		pieRadius = pieDiameter / 2;
		pieCenterPos = pieBorderWidth + pieRadius;
		this.setFont(new Font("Helvetica", Font.BOLD, 12));
		this.setBackground(Color.white);
		//this.setBackground(new Color(255,255,183));

		for (int i = 0; i < wedges.length; i++) {
			total += wedges[i].frac;
		}

		wedgeColor[0] = Color.green; // colors that black looks good on
		wedgeColor[1] = Color.yellow;
		wedgeColor[2] = Color.red;
		wedgeColor[3] = Color.orange;
		wedgeColor[4] = Color.cyan;
		wedgeColor[5] = Color.pink;
		wedgeColor[6] = Color.magenta;
	} // constructor

	public void paint(Graphics g) {
		int startDeg = 0;
		int arcDeg;
		int arcDegCount = 0;
		int x, y;
		double angleRad;

		g.setColor(Color.lightGray); // shadow
		g.fillOval(
			pieBorderWidth + 3,
			pieBorderWidth + 3,
			pieDiameter,
			pieDiameter);
		g.setColor(Color.gray); // "other" is gray
		g.fillOval(pieBorderWidth, pieBorderWidth, pieDiameter, pieDiameter);

		int wci = 0;
		int i;
		for (i = 0; i < this.wedges.length; i++) { // draw wedges
			arcDeg = (int) Math.round(((this.wedges[i].frac / total) * 360));
			arcDegCount += arcDeg;
			if ((i + 1) == this.wedges.length & arcDegCount != 360) {
				arcDeg += (360 - arcDegCount);
				// Avoid a gray wedge due to roundoff.
			}
			g.setColor(wedgeColor[wci++]);
			g.fillArc(
				pieBorderWidth,
				pieBorderWidth,
				pieDiameter,
				pieDiameter,
				startDeg,
				arcDeg);
			if (wci >= wedgeColor.length) {
				wci = 1;
				// Rotate colors.  Don't reuse 0 in case first and last are same.
			}
			startDeg += arcDeg;
		} // draw wedges
		startDeg = 0; // Do labels so they go on top of the wedges.
		for (i = 0; i < this.wedges.length; i++) {
			arcDeg = (int) Math.round(((this.wedges[i].frac / total) * 360));
			if (arcDeg > 3) { // Don't label small wedges.
				g.setColor(Color.black);
				angleRad =
					(float) (startDeg + (arcDeg / 2))
						* java.lang.Math.PI
						/ 180.0;
				x =
					pieCenterPos
						+ (int) ((pieRadius / 1.3)
							* java.lang.Math.cos(angleRad));
				y =
					pieCenterPos
						- (int) ((pieRadius / 1.3)
							* java.lang.Math.sin(angleRad))
						+ 5;
				// 5 is about half the height of the text
				g.drawString(this.wedges[i].label, x, y);
			}
			startDeg += arcDeg;
		}
	}

	public Dimension preferredSize() {
		return new Dimension(pieViewSize, pieViewSize);
	}
}
