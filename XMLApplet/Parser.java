/*
 * Created on Feb 19, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package XMLApplet;

import java.io.*;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory; 
//import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

/**
 * @author session
 */
public class Parser extends DefaultHandler {
	
	static private HashMap ages;
	static private HashMap majors;
	static private HashMap gpas;
	static private String element;
	
	/**
	 * Constructor
	 */
	Parser() {
		// Set up value hashes
		ages = new HashMap();
		majors = new HashMap();
		gpas = new HashMap();
		
		// Initialize elementBuffer
		element = new String("");
	}
	
	public void parse (String fileName) {
		// Use the default (non-validating) parser
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		try {
			// Parse the input 
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new File(fileName), this);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public void startElement(
		String namespaceURI,
		String sName, // simple name
		String qName, // qualified name
		Attributes attrs)
	throws SAXException {
		element = sName;
		// Check for empty namespace
		if (element.equals("")) element = qName;
	}
	
	public void endElement(
		String namespaceURI,
		String sName, // simple name
		String qName) // qualified name
	throws SAXException {
		element = "";
	}
	
	public void characters(char buf[], int offset, int len) throws SAXException {
		String s = new String(buf, offset, len);
		int value;
		
		if (element.equals("age")) {
			value = ((Integer)ages.get(s)).intValue();
			ages.put(s, new Integer(value+1));
		} else if (element.equals("major")) {
			value = ((Integer)majors.get(s)).intValue();
			majors.put(s, new Integer(value+1));
		} else if (element.equals("gpa")) {
			value = ((Integer)gpas.get(s)).intValue();
			gpas.put(s, new Integer(value+1));
		}
	}
	
	public HashMap getAges() {
		return ages;
	}

	public HashMap getMajors() {
		return majors;
	}
	
	public HashMap getGPAs() {
		return gpas;
	}
}
