package edu.gmu.cs332.hw5;

import java.io.*;
import edu.gmu.cs332.hw5.shapes.*;

/**
 * This program reads in an input file containing information about shapes on each line
 * and translates that file to a different format.
 * 
 * After completion, this program will also take these shapes and display them in a
 * GUI browser/slideshow.
 * 
 * @author Tim Cheadle
 */
public class ShapeBrowser {
	/**
	 * Runs the main application; takes a filename as an argument.
	 * 
	 * @param args The filename to read shape from
	 */
	public static void main(String[] args) {
		if (args.length == 0 || args.length >= 2) {
			System.err.println("Usage: ShapeBrowser <filename>");
			System.exit(1);
		}
		
		String filename = args[0];
		translateFile(filename);
	}

	/**
	 * Reads the input file and creates new shape descriptions for each line.
	 * 
	 * @param filename The filename to read
	 */
	private static void translateFile(String filename) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line = "";
			
			while((line = in.readLine()) != null) {
				ShapeFactory sf = new TextShapeFactory(line);
				Shape shape = sf.makeShape();
				
				if (shape != null) {
					System.out.println(shape.toString());
				} else {
					System.out.println("Invalid line: " + line);
				}
			}
			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}