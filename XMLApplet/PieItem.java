/*
 * Created on Feb 27, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package XMLApplet;

import java.util.*;

/**
 * @author session
 */
public class PieItem {
	public String label; // Each one has a label
	public int value;    // and a value

	/**
	 * Constructor
	 * @param s
	 */
	PieItem (String s) {
		value = Double.valueOf(t.nextToken()).doubleValue();
		label = t.nextToken();
	}
}
