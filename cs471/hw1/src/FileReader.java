import java.util.regex.*;
import java.io.*;
/**
 * @author Faye Wong
 */
public class FileReader {
	
	public Scheduler readFile(String filename) {
		

		Scheduler scheduler = null;		
		int simLength = 0;	
		
		try {
			Pattern configPattern = Pattern.compile("^(.+):\\s+(\\S+)");
   
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line;
			Process p = null;
			
			while((line = in.readLine()) != null) {
   				Matcher m = configPattern.matcher(line);
   				if (m.find()) { 
   					if (m.group(1).equals("Length of the simulation")) {
   						simLength = Integer.parseInt(m.group(2));
   					}  	 		
   					if (m.group(1).equals("Scheduling Algorithm")) {
   						if(m.group(2).equals("FCFS")) {
							scheduler = new FCFSScheduler();	
							scheduler.simLength = simLength;
   						}
						if(m.group(2).equals("SJF")) {
   							scheduler = new SJFScheduler();
   							scheduler.simLength = simLength;
						}
						if(m.group(2).equals("SRTF")) {
							scheduler = new SRTFScheduler();
							scheduler.simLength = simLength;
						}
   					}   							
   					if (m.group(1).equals("Process ID")) {
   				 		if (p != null) {
   				 			scheduler.addProcess(p);
   				 		}
   				 		p = new Process(Integer.parseInt(m.group(2)));
   				 	} 
					if (m.group(1).equals("Arrival Time")) {
						p.setTimeArrive(Integer.parseInt(m.group(2)));	
					}   				 	
   				 	if (m.group(1).equals("CPU burst")) {
   				 		p.addBurst(Integer.parseInt(m.group(2)));
   				 		//System.out.println("Burst: " + Integer.parseInt(m.group(2)));
  					} 
  					if (m.group(1).equals("I/O burst")) {
						p.addBurst(Integer.parseInt(m.group(2)));
					}  				
				}
					
			}
	
			if (p != null) {
				scheduler.addProcess(p);
			}
			
			in.close();
		}catch (Throwable t) {
			t.printStackTrace();
		}
		
	
	return (Scheduler)scheduler; 
	
	} 

}

