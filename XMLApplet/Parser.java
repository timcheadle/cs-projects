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
	
	/**
	 * @param fileName The XML file to parse
	 */
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
	
	public void parse (InputStream url) {
		// Use the default (non-validating) parser
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		try {
			// Parse the input 
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(url, this);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	/**
	 * @return HashMap Hash with keys of different ages found and values of their occurences
	 */
	public HashMap getAges() {
		return ages;
	}

	public HashMap getMajors() {
		return majors;
	}
	
	public HashMap getGPAs() {
		return gpas;
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
		String key = new String(buf, offset, len);
		int value = 0;
	
		if (element == null || element.equals("")) {
			return;
		} else if (element.equals("age")) {
			// Check to see if a key/value pair exists for this age
			if (ages.containsKey(key) && (ages.get(key) != null)) {
				value = ((Integer)ages.get(key)).intValue();
			}
			value++;
			ages.put(key, new Integer(value));
		} else if (element.equals("major")) {
			// Check to see if a key/value pair exists for this age
			if (majors.containsKey(key) && (majors.get(key) != null)) {
				value = ((Integer)majors.get(key)).intValue();
			}
			value++;
			majors.put(key, new Integer(value));
		} else if (element.equals("gpa")) {
			// Check to see if a key/value pair exists for this age
			if (gpas.containsKey(key) && (gpas.get(key) != null)) {
				value = ((Integer)gpas.get(key)).intValue();
			}
			value++;
			gpas.put(key, new Integer(value));
		}
	}
}
