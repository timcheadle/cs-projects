/**
 * @author Faye Wong
 */
public class Simulation {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: Simulation <filename>");
			System.exit(1);
		}
		String filename = args[0];
		Scheduler scheduler;
				
		FileReader reader = new FileReader();
		scheduler = reader.readFile(filename);
		scheduler.run();
		
		
	}
}
