/*
 * Created on Feb 20, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package XMLApplet;

import XMLApplet.Parser;
import java.util.*;

/**
 * @author session
 */
public class XMLApplet {

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
