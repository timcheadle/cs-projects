/**
 * @author Faye Wong
 */
public class Simulation {
	public static void main(String[] args) {
		String filename = new String(args.toString());
		Scheduler scheduler;
				
		FileReader reader = new FileReader();
		scheduler = reader.readFile(filename);
		scheduler.run();
		
		
	}
}
