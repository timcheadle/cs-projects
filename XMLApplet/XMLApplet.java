/*
 * Created on Feb 20, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package XMLApplet;

import XMLApplet.*;
import java.awt.*;
import java.applet.Applet;
import java.util.*;
import java.net.*;

/**
 * @author session
 */
public class XMLApplet extends Applet {
	private Parser p;
	private String url;
	private String filename;
	
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
		g.setColor(new Color(40, 40, 40));
		g.fillRect(0, 0, d.width, d.height);
		
		HashMap ageHash = p.getAges();
		HashMap gpaHash = p.getGPAs();
		HashMap majorHash = p.getMajors();
		
		//g.drawString(getCodeBase() + "students.xml", 10, 10);
		g.setFont(new Font("Helvetica", Font.BOLD, 14));
		g.setColor(Color.white);
		g.drawString("Reading from: " + url, 10, 15);
		
		// Ages chart
		g.setFont(new Font("Helvetica", Font.BOLD, 14));
		g.setColor(Color.white);
		g.drawString("Ages", 10, 55);
		PieChart ageChart = new PieChart(ageHash);
		ageChart.draw(g, 40, 50, 100, 100, 10, 75);
		
		// GPAs chart
		g.setFont(new Font("Helvetica", Font.BOLD, 14));
		g.setColor(Color.white);
		g.drawString("GPAs", 180, 55);
		PieChart gpaChart = new PieChart(gpaHash);
		gpaChart.draw(g, 220, 50, 100, 100, 180, 75);
		
		// Majors chart
		g.setFont(new Font("Helvetica", Font.BOLD, 14));
		g.setColor(Color.white);
		g.drawString("Majors", 350, 55);
		PieChart majorChart = new PieChart(majorHash);
		majorChart.draw(g, 390, 50, 100, 100, 350, 75);
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
