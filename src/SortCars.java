import java.util.*;
import java.io.*;
import Car;

/**
 * SortCars is a program that will both generate a file of car descriptions, as well as
 * read in that file and print out a sorted version of the file.  Cars are generated randomly
 * using a list of possible values for attributes such as make, model, colors, etc.
 * 
 * When the program is called in sorting mode, the sorted file is printed as filename.sorted.
 * 
 * @author Tim Cheadle
 */
public class SortCars {

	/**
	 * Runs the SortCars program and checks command line arguments.  It calls the proper methods
	 * based on the given arguments.
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		String filename = "";
		boolean generateFile = false;
		
		// Check command line arguments
		if (args.length != 2 && args.length != 3) {
			usage();
			return;
		}
		
		if (args[0].equals("-g") && args.length == 3) {
			generateFile(args[2], Integer.parseInt(args[1]));
			return;
		} else if (args[0].equals("-s") && args.length == 2) {
			sortFile(args[1]);
			return;
		} else {
			usage();
			return;
		}
	}
	
	/**
	 * Prints a usage statement describing how to run the program from the command line
	 */
	private static void usage() {
		System.err.println("Usage: SortCars [-g <N>|-s] <filename>");
		System.err.println("   -g <N>  Generate file with N cars");
		System.err.println("   -s      Sort file (outputs file.sorted)");
		System.err.println("   file    File to input (or output w/ -g)");
	}
	
	/**
	 * Generates the file containing a random list of cars.
	 * 
	 * @param file The filename to output to
	 * @param n The number of cars to generate
	 */
	private static void generateFile (String file, int n) {
		Random r = new Random();
		
		// Set up arrays of characteristics
		String makes[]  = {"Acura", "BMW", "Audi", "Mercedes-Benz", "Subaru", "Porsche"};
		String models[] = {"RSX", "A4", "SL55", "WRX", "Carrera GT2", "M3"}; 
		String colors[] = {"Black", "White", "Silver", "Blue", "Red", "Grey"};
		int years[]     = {2003, 2002, 2001, 2000, 1999, 1998};
		int hp[]        = {175, 189, 227, 240, 333, 459};
		int torque[]    = {180, 200, 232, 310, 342, 510};
		int cylinders[] = {4, 6, 8, 12};
		
		// Try to write to the file			
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			for (int i = 0; i < n; i++) {
				// Create a new random car
				Car c = new Car(
					makes[r.nextInt(makes.length)],
					models[r.nextInt(models.length)],
					years[r.nextInt(years.length)],
					colors[r.nextInt(colors.length)],
					hp[r.nextInt(hp.length)],
					torque[r.nextInt(torque.length)],
					cylinders[r.nextInt(cylinders.length)]
				);
				
				// Now print the description to the file
				out.println(c.toString());
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads in a file containing car descriptions, sorts it, and prints the sorted list out.
	 * 
	 * @param file The filename to read (the output will be file+".sorted")
	 */
	private static void sortFile (String file) {
		ArrayList cars = new ArrayList();
		
		try {
			// Create a buffered reader to get a line of input
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line, word;
			String delim = ",";
			
			// Read until the end of the file
			while((line = in.readLine()) != null) {
				// Break the line into tokens, comma delimited
				StringTokenizer st = new StringTokenizer(line, delim);
				
				// While we have more tokens to read
				while(st.hasMoreTokens()) {
					// Read each token and create the attributes to place in the Car object
					String make    = st.nextToken();
					String model   = st.nextToken();
					int modelYear  = Integer.parseInt(st.nextToken());
					String color   = st.nextToken();
					int horsepower = Integer.parseInt(st.nextToken());
					int torque     = Integer.parseInt(st.nextToken());
					int cylinders  = Integer.parseInt(st.nextToken());
					
					// Create the new Car object and add it to the list
					Car c = new Car(make, model, modelYear, color, horsepower, torque, cylinders);
					cars.add(c);
				}
			}
			
			// Now create a buffered output object
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file + ".sorted")));
			
			// Sort the list of cars
			Collections.sort(cars);
			
			// Now print the list of cars out
			for(Iterator i = cars.iterator(); i.hasNext(); ) {
				out.println(i.next().toString());
			}
			
			// Close the output file
			out.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
