/*
 * Created on Feb 20, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package XMLApplet;

import XMLApplet.Parser;
import java.awt.*;
import java.applet.Applet;
import java.util.*;

/**
 * @author session
 */
public class XMLApplet extends Applet {

	public void paint(Graphics g) {
		Parser p = new Parser();
		p.parse("/home/session/src/cs332/XMLApplet/students.xml");
		
		Dimension d = getSize();
		g.setColor(Color.gray);
		g.fillRect(0, 0, d.width, d.height);
		g.setFont(new Font("Helvetica", Font.BOLD, 14));
		g.setColor(new Color(0, 0, 128));
		
		HashMap ageHash = p.getAges();
		Set ages = ageHash.keySet();
		
		Iterator i;
		int y = 20;
		for (i = ages.iterator(); y < d.height && i.hasNext(); y+=24) {
				String key = i.next().toString();
				String value = ageHash.get(key).toString();
				g.drawString("key/value = " + key + "/" + value, 10, y);
		}
	}

	public static void main(String[] args) {
		Parser p = new Parser();
		p.parse(args[0]);
		
		HashMap ageHash = p.getGPAs();
		System.out.println("hash: " + ageHash);
		Set ages = ageHash.keySet();
		for (Iterator i = ages.iterator(); i.hasNext(); ) {
			String key = i.next().toString();
			String value = ageHash.get(key).toString();
			System.out.println("  key: " + key);
			System.out.println("value: " + value);
		}
	}
}
