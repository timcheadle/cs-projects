/**
 *  java class to mimic unix cat command
 *
 * @author Dave Steinbrunn <BR> Brian Skapura <BR> Tim Cheadle
 * @version 1.0
 * @since 1.4
 */

import cs471.*;
import java.util.*;
import java.io.*;


public class cat extends Command implements Runnable {

    private ArrayList args;

    /** allows arguments to be passed
     *  @param arguments ArrayList containing path to each file
     */
    public void setArgs(ArrayList arguments){
        args = arguments;
    }


    /** used to execute cat command */
    public void run(){

        super.setRunningState(true);
        String input_handle = super.getInputHandle();
        String output_handle = super.getOutputHandle();
        PipeManager pm = super.getPipeManager();
        String line;
        String cat_file = null;

        try {
          if ( args.size() > 0 ){
            for (int i=0; i < args.size(); i++){

              File testfile = new File((String)args.get(i));
              boolean found_file = false;

              // see if the file exists
              if (testfile.isFile()){
                  cat_file = (String)args.get(i);
                  found_file = true;
              } else {
                  // see if we append the current working directory, if that file exists
                  String new_file = (String)(super.getEnvironment()).get("current_dir") + File.separator + (String)args.get(i);
                  testfile = new File( new_file );
                  if (testfile.isFile()){
                      cat_file = new_file;
                      found_file = true;
                  }
              }
              if (found_file){
                  try {
                      BufferedReader is = new BufferedReader(new FileReader( cat_file ));
                      while(( line = is.readLine()) != null ){
                          pm.write(output_handle, (Object)line );
                      }
                      pm.write(output_handle, "_EOF");
                  } catch (java.io.FileNotFoundException e){
                      pm.write(output_handle, "error:  can't find " + args.get(i) + "\n");
                  } catch (Exception e){
                      pm.write(output_handle, "error opening file: " + e.toString() + "\n");
                  }
              } else {
                  pm.write(output_handle, "error:  " + (String)args.get(i) + " does not exist");
              }
            }
          }

        } catch (Exception e){
            // need to read from another pipe
            //System.out.println("cat:  reading from pipe " + input_handle);
            pm.setGatherInput(input_handle, true);
            boolean cont = true;
            while (cont){
                pm.setGatherInput(input_handle, true);
                line = (String)pm.read(input_handle);
                if (line != null){
                    pm.setGatherInput(input_handle, false);
                    if (line.equals("_EOF")){
                        cont = false;
                    } else {
                        pm.write(output_handle, line);
                    }
                }
            }
        }

        super.setRunningState(false);
        super.setExecuted(true);

    }
}

