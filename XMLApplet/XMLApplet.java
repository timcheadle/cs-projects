/*
 * Created on Feb 20, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package XMLApplet;

import XMLApplet.Parser;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.net.*;

/**
 * @author session
 */
public class XMLApplet extends JApplet {
	private Parser p;
	private String url;
	private String filename;
	
	public void init() {
		String message = "Pie Chart Applet";
		JButton b1 = new JButton("Ages");
		getContentPane().add(b1);
	}
	
	public void start() {
		// Construct the URL
		filename = getParameter("data");
		if (filename == null) {
			filename = "students.xml";
		}
		url = getCodeBase() + filename;
		
		// Initialize the parser
		p = new Parser();
		
		// Try to parse the given URL
		try {
			URL urlStream = new URL(url);
			p.parse(urlStream.openStream());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public void paint(Graphics g) {
		Dimension d = getSize();
		g.setColor(Color.gray);
		g.fillRect(0, 0, d.width, d.height);
		g.setFont(new Font("Helvetica", Font.BOLD, 14));
		g.setColor(new Color(0, 0, 128));
		
		HashMap ageHash = p.getAges();
		Set ages = ageHash.keySet();
		
		Iterator i;
		//g.drawString(getCodeBase() + "students.xml", 10, 10);
		g.drawString(url, 10, 10);
		
		int y = 30;
		for (i = ages.iterator(); y < d.height && i.hasNext(); y+=24) {
				String key = i.next().toString();
				String value = ageHash.get(key).toString();
				g.drawString("key/value = " + key + "/" + value, 10, y);
		}
	}

	public static void main(String[] args) {
		Parser p = new Parser();
		p.parse(args[0]);
		
		HashMap ageHash = p.getAges();
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
