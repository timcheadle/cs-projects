/*
 * Created on Feb 20, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package XMLApplet;

import XMLApplet.Parser;

/**
 * @author session
 */
public class XMLApplet {

	public static void main(String[] args) {
		Parser p = new Parser();
		p.parse(args[0]);
	}
}
