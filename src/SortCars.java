import java.util.*;
import java.io.*;
import Car;

/**
 * @author session
 */
public class SortCars {

	public static void main(String[] args) {
		String filename = "";
		boolean generateFile = false;
		
		// Check command line arguments
		if (args.length != 2 && args.length != 3) {
			usage();
			return;
		}
		
		if (args[0].equals("-g") && args.length == 3) {
			generateFile(args[1], args[2]);
			return;
		} else if (args[0].equals("-s") && args.length == 2) {
			sortFile(args[1]);
			return;
		} else {
			usage();
			return;
		}
	}
	
	private static void usage() {
		System.err.println("Usage: SortCars [-g <N>|-s] <filename>");
		System.err.println("   -g <N>  Generate file with N cars");
		System.err.println("   -s      Sort file (outputs file.sorted)");
		System.err.println("   file    File to input (or output w/ -g)");
	}
	
	private static void generateFile (String file, String n) {
		int lines = Integer.parseInt(n);
		
		Random r = new Random();
		
		// Set up arrays of characteristics
		String makes[]  = {"Acura", "BMW", "Audi", "Mercedes-Benz", "Subaru", "Porsche"};
		String models[] = {"RSX", "A4", "SL55", "WRX", "Carrera GT2", "M3"}; 
		String colors[] = {"Black", "White", "Silver", "Blue", "Red", "Grey"};
		int years[]     = {2003, 2002, 2001, 2000, 1999, 1998};
		int hp[]        = {175, 189, 227, 240, 333, 459};
		int torque[]    = {180, 200, 232, 310, 342, 510};
		int cylinders[] = {4, 6, 8, 12};
					
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			for (int i = 0; i < lines; i++) {
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
	
	private static void sortFile (String file) {
		ArrayList cars = new ArrayList();
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line, word;
			String delim = ",";
			while((line = in.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, delim);
				while(st.hasMoreTokens()) {
					String make    = st.nextToken();
					String model   = st.nextToken();
					int modelYear  = Integer.parseInt(st.nextToken());
					String color   = st.nextToken();
					int horsepower = Integer.parseInt(st.nextToken());
					int torque     = Integer.parseInt(st.nextToken());
					int cylinders  = Integer.parseInt(st.nextToken());
					
					Car c = new Car(make, model, modelYear, color, horsepower, torque, cylinders);
					cars.add(c);
				}
			}
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file + ".sorted")));
			Collections.sort(cars);
			
			for(Iterator i = cars.iterator(); i.hasNext(); ) {
				out.println(i.next().toString());
			}
			
			out.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		

	}
}
