package cs471;

/**
 * class used to get input from a file (used with < pipe
 *
 * @author:  Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version:  1.0
 * @since: 1.4
 *
 */

import java.io.*;
import cs471.*;

public class ReadFile extends Thread {

    String file, output_handle;
    PipeManager pm = PipeManager.getInstance();


    /** constructor
     * @param file_name file to be parsed
     * @param output pipe to send output to
     */
    public ReadFile(String file_name, String output){
        file = file_name;
        output_handle = output;
    }

    /** parses file and sends to output_handle.  Does no error checking to
     * make sure file exists. Adds _EOF to signal end of file
     */
    public void run(){

        String cat_file, line;

        try {
            BufferedReader is = new BufferedReader(new FileReader( file ));
            while(( line = is.readLine()) != null ){
                pm.write(output_handle, line );
            }
            pm.write(output_handle, "_EOF");
        } catch (java.io.FileNotFoundException e){
            pm.write(output_handle, "error:  can't find " + file + "\n");
        } catch (Exception e){
            pm.write(output_handle, "error opening file: " + e.toString() + "\n");
        }

    }
}