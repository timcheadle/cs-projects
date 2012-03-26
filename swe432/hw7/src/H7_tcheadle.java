/**
 * @author Tim Cheadle
 * 
 * SWE432
 * Homework 7
 * $Date: 2003-11-12 20:44:36 $
 */

////////////////////////////////////////////
//
// Create HTML files for each picture.
// Instructions:
//	1. Place this file in a directory with JPG files.
//	2. Create a file named "H7-tcheadle-data.txt" that has
//	   A) A date (or other string) that is printed on each page.
//	   B) two lines for each JPG file:
//		 i) names of the JPG files *WITHOUT* the extension.
//		ii) A title for the picture
//	3. Run MakeHTML; for each filename f in H7-tcheadle-data.txt,
//	   it should create a file called "f.html".
//	4. Create an index.html if desired.
//
//	Customizations:
//	 * The default "HEIGHT" for pictures is PICTUREHEIGHT
// Should be a JSP
// Jeff Offutt, August 2001, update for titles Feb 2003
//				update for date in the file, March 2003
//
///////////////////////////////////////////
import java.io.*;
import java.util.*;

class H7_tcheadle {

	// Default height for pictures.
	private static int PICTUREHEIGHT = 600;
	private static String JPGFILE = "H7-tcheadle-data.txt";
	private static String URLREGEX = "^http://\\S+(?:(?:gif)|(?:jpg)|(?:jpeg)|(?:png))$";

	public static void main(String[] argv) throws IOException {
		ArrayList images = new ArrayList();
		ArrayList titles = new ArrayList();
		int numFiles = 0;

		// Read the list of file names from the file "H7-tcheadle-data.txt"
		// Try to open the file.
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(JPGFILE));
		} catch (FileNotFoundException e) {
			System.out.println("Need a file called \"" + JPGFILE + "\".");
			return;
		}

		String collectionName = "";
		String bgColor = "";
		try {
			// Read the repeating string (title)
			collectionName = in.readLine();
			bgColor = in.readLine();

			// Read the names from the input file
			String inString;
			while ((inString = in.readLine()) != null) {
				// If the image name isn't a valid URL, print an error and ignore it
				if (!inString.matches(URLREGEX)) {
					// Make sure to ignore the following title line too
					in.readLine();
					System.err.println("Not a valid image URL" + inString);
				} else {
					// If the URL was valid, add it and its description line to the proper arrays
					images.add(inString);
					inString = in.readLine();
					titles.add(inString);
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


		try {
			if (images.size() > 0) {
				writeIndexHTML(images, titles, collectionName, bgColor);
			}
		} catch (IOException e) {
			System.err.println("Could not write index.html: " + e.getMessage());
		}

		// If we actually found some images, print them out
		for (int i = 0; i < images.size(); i++) {
			try {
				// If we're at the first image, ignore the previous image
				if (i == 0) {
					writeOneHTML(
						"",
						(String)images.get(i),
						(String)images.get(i + 1),
						(String)titles.get(i),
						collectionName,
						bgColor
					);
				}
				// If we at the last image, ignore the next image
				else if (i == images.size() - 1) {
					writeOneHTML(
						(String)images.get(i - 1),
						(String)images.get(i),
						"",
						(String)titles.get(i),
						collectionName,
						bgColor
					);
				}
				// Otherwise, select the previous, current and next image
				else {
					writeOneHTML(
						(String)images.get(i - 1),
						(String)images.get(i),
						(String)images.get(i + 1),
						(String)titles.get(i),
						collectionName,
						bgColor
					);
				}
			} catch (IOException e) {
				System.err.println("Could not write file for image '" + (String)images.get(i) + "': " + e.getMessage());
			}
		}
	}

	/**
	 * Writes the index page for the photo collection
	 * @param images The list of image URLs
	 * @param titles The list of associated image titles
	 * @param name The name of the collection
	 * @param bgColor The background color for the index page
	 * @throws IOException
	 */
	public static void writeIndexHTML(
		ArrayList images,
		ArrayList titles,
		String name,
		String bgColor
	) throws IOException {
		FileWriter fout = new FileWriter("index.html");
		
		fout.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">\n");
		fout.write("<html>\n");

		fout.write("<head>\n");
		fout.write("\t<title>" + name + "</title>\n");
		fout.write("</head>\n");

		fout.write("<body BGColor=\"" + bgColor + "\">\n");

		fout.write("<center>\n");
		fout.write("<h1><i>" + name + "</i></h1>\n\n");

		fout.write("</center>\n\n");

		fout.write("<table>\n");
		fout.write("<tr>\n");
		fout.write("<td>\n");
		fout.write("<ol>\n");

		for (int i = 0; i < images.size(); i++) {
			String imageName = parseName((String)images.get(i));
			String imageTitle = (String)titles.get(i);
			fout.write("<li><a HRef=\"" + imageName + ".html\">" + imageTitle + "</a>\n");
		}

		fout.write("</ol>\n");
		fout.write("</td>\n");

		// Write the index picture (first picture in the array)
		fout.write("<td>\n");
		
		String indexImageURL = (String)images.get(0);
		fout.write("<img SRC=\"" + indexImageURL + "\" Height=200>\n");
		fout.write("</td>\n");
		fout.write("</tr>\n");
		fout.write("</table>\n\n");

		fout.write("</body>\n");
		fout.write("</html>\n");
		
		fout.close();
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++
	// Creates an HTML page for the given picture name.
	//
	public static void writeOneHTML(
		String prev,
		String cur,
		String next,
		String title,
		String collectionName,
		String bgColor
	) throws IOException {

		// Parse image names from URLs
		String prevImageName = parseName(prev);
		String curImageName  = parseName(cur);
		String nextImageName = parseName(next);
		
		FileWriter fout = new FileWriter(curImageName + ".html");

		fout.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">\n");
		fout.write("<HTML>\n");
		fout.write("\n");
		fout.write("<HEAD>\n");
		fout.write(" <TITLE>" + curImageName + "</TITLE>\n");
		fout.write("</HEAD>\n");
		fout.write("\n");
		fout.write("<BODY BGColor=\"" + bgColor + "\">\n");
		fout.write("\n");
		fout.write("<CENTER>\n");
		fout.write("<P>\n");
		fout.write(
			"<IMG SRC=\"" + cur + "\" Height=" + PICTUREHEIGHT + ">\n");
		fout.write("\n");
		fout.write("<P>\n");
		fout.write(title + " - <i>" + collectionName + "</i>\n");
		fout.write("\n");
		fout.write("<TABLE Border=5 CellSpacing=15>\n");
		fout.write(" <TR>\n");
		if (prev.length() != 0)
			fout.write("  <TD><A HREF=\"" + prevImageName + ".html\">Prev</A></TD>\n");
		else
			fout.write("  <TD>Prev</TD>\n");
		fout.write("  <TD><A HREF=\"./index.html\">Index</A></TD>\n");
		if (next.length() != 0)
			fout.write("  <TD><A HREF=\"" + nextImageName + ".html\">Next</A></TD>\n");
		else
			fout.write("  <TD>Next</TD>\n");
		fout.write(" </TR>\n");
		fout.write("</TABLE>\n");
		fout.write("\n");
		fout.write("</CENTER>\n");
		fout.write("\n");
		fout.write("</BODY>\n");
		fout.write("</HTML>\n");
		fout.write("\n");

		fout.close();
	}
	
	private static String parseName(String url) {
		url = url.replaceAll(".*/", "");
		url = url.replaceFirst(".[^.]+$", "");
		return url;
	}
}
