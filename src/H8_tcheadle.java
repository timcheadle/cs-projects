/**
 * @author Tim Cheadle
 * 
 * SWE432
 * Homework 8
 * $Date: 2003-11-13 05:49:14 $
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
import javax.servlet.*;
import javax.servlet.http.*;

public class H8_tcheadle extends HttpServlet {

	// Default height for pictures.
	private static int PICTUREHEIGHT = 600;
	
	// Maximum number of photos
	private static int MAXPHOTOS = 20;
	
	// Regular expressions
	private static String COLORREGEX = "^[a-fA-F0-9]{6}$";
	private static String URLREGEX = "^http://\\S+(?:(?:gif)|(?:jpg)|(?:jpeg)|(?:png))$";
	
	// Directory to print HTML
	private static String HTMLDIR = "/home/tcheadle/public_html/";

	// Collection information
	private String collectionName;
	private String collectionDescription;
	private String indexPhoto;
	private String bgColor;
	private ArrayList photos = new ArrayList();
	
	// Error tracking
	private ArrayList errors = new ArrayList();
	
	// Inner class to represent individual Photo information
	private class Photo {
		String url;
		String description;
		boolean valid = false;
		
		public Photo(String url, String desc) {
			this.url = url;
			this.description = desc;
			if (url != null && desc != null && url.matches(URLREGEX)) {
				this.valid = true;
			}
		}
		
		public String getUrl() { return url; }
		public String getDescription() { return description; }
		public boolean isValid() { return valid; }
	}

	public void doPost (HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException { 

		// Get collection parameters
		collectionName = req.getParameter("collectionName");
		collectionDescription = req.getParameter("collectionDescription");
		indexPhoto = req.getParameter("indexPhoto");
		bgColor = req.getParameter("bgColor");
		for (int i=0; i < MAXPHOTOS; i++) {
			String url = req.getParameter("photo" + i + "URL");
			String desc = req.getParameter("photo" + i + "Description");
			photos.add(new Photo(url, desc));
		}

		validateParams();
		generateHTML();
	}

	private boolean validateParams() {
		boolean valid = true;
		
		// Check the collection name
		if (collectionName == null) {
			errors.add("Collection Name was blank; please enter a name for the collection.");
			valid = false;
		}
		
		// Make sure the index photo was specified and points to a valid photo
		if (indexPhoto == null) {
			errors.add("Index photo was not selected; please select which photo should be on the menu page.");
			valid = false;
		} else {
			int index = Integer.parseInt(indexPhoto);
			if (index < 1 || index > MAXPHOTOS) {
				errors.add("Index Photo number needs to be between 1 and 20.");
				valid = false;
			} else if (!((Photo)photos.get(index)).isValid()) {
				errors.add("Index Photo is an invalid photo; make sure URL is valid and description is not blank.");
			}
			
		}
		if (bgColor == null) {
			errors.add("No background color was selected; please type a background color.");
			valid = false;
		} else if (!bgColor.matches(COLORREGEX)) {
			errors.add("Background color needs to be in RRGGBB format.");
			valid = false;
		}
		
		// Check to see if each photo URL is valid
		for (int i = 0; i < photos.size(); i++) {
			Photo p = (Photo)photos.get(i);
			if (!p.isValid()) {
				if (p.getUrl() != null) {
					errors.add("Photo " + i + " has an invalid URL.");
					valid = false;
				}
				if (p.getUrl() == null && p.getDescription() != null) {
					errors.add("Photo " + i + " has a description but no URL.");
					valid = false;
				}
				if (p.getUrl() != null && p.getDescription() == null) {
					errors.add("Photo " + i + " has a valid URL but no description.");
					valid = false;
				}
			}
		}
		
		return valid;
	}
	
	private void generateHTML() {
		try {
			if (photos.size() > 0) {
				writeIndexHTML();
			}
		} catch (IOException e) {
			System.err.println("Could not write index.html: " + e.getMessage());
		}

		// If we actually found some images, print them out
		for (int i = 0; i < photos.size(); i++) {
			try {
				// If we're at the first image, ignore the previous image
				if (i == 0) {
					writeOneHTML(
						"",
						((Photo)photos.get(i)).getUrl(),
						((Photo)photos.get(i + 1)).getUrl(),
						((Photo)photos.get(i)).getDescription()
					);
				}
				// If we at the last image, ignore the next image
				else if (i == photos.size() - 1) {
					writeOneHTML(
						((Photo)photos.get(i - 1)).getUrl(),
						((Photo)photos.get(i)).getUrl(),
						"",
						((Photo)photos.get(i)).getDescription()
					);
				}
				// Otherwise, select the previous, current and next image
				else {
					writeOneHTML(
						((Photo)photos.get(i - 1)).getUrl(),
						((Photo)photos.get(i)).getUrl(),
						((Photo)photos.get(i + 1)).getUrl(),
						((Photo)photos.get(i)).getDescription()
					);
				}
			} catch (IOException e) {
				System.err.println("Could not write file for image '" + ((Photo)photos.get(i)).getUrl() + "': " + e.getMessage());
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
	private void writeIndexHTML() throws IOException {
		FileWriter fout = new FileWriter(HTMLDIR + "index.html");
	
		fout.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">\n");
		fout.write("<html>\n");

		fout.write("<head>\n");
		fout.write("\t<title>" + collectionName + "</title>\n");
		fout.write("</head>\n");

		fout.write("<body BGColor=\"" + bgColor + "\">\n");

		fout.write("<center>\n");
		fout.write("<h1><i>" + collectionName + "</i></h1>\n\n");

		fout.write("</center>\n\n");

		fout.write("<table>\n");
		fout.write("<tr>\n");
		fout.write("<td>\n");
		fout.write("<ol>\n");

		for (int i=0; i < photos.size(); i++) {
			Photo p = (Photo)photos.get(i);
			String imageName = parseName(p.getUrl());
			String imageTitle = p.getDescription();
			fout.write("<li><a HRef=\"" + imageName + ".html\">" + imageTitle + "</a>\n");
		}

		fout.write("</ol>\n");
		fout.write("</td>\n");

		// Write the index picture (first picture in the array)
		fout.write("<td>\n");
		
		String indexImageURL = ((Photo)photos.get(Integer.parseInt(indexPhoto))).getUrl();
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
	private void writeOneHTML(
		String prev,
		String cur,
		String next,
		String title
	) throws IOException {

		// Parse image names from URLs
		String prevImageName = parseName(prev);
		String curImageName  = parseName(cur);
		String nextImageName = parseName(next);
		
		FileWriter fout = new FileWriter(HTMLDIR + curImageName + ".html");

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
	
	private String parseName(String url) {
		url = url.replaceAll(".*/", "");
		url = url.replaceFirst(".[^.]+$", "");
		return url;
	}
}
