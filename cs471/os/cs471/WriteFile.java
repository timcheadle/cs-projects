package cs471;

/**
class used by os to write files (called by > pipe)

@author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
@version 1.0
@since 1.4

*/

import java.io.*;
import cs471.*;

public class WriteFile extends Thread{

    private String file = null;
    private PrintWriter out;
    private PipeManager pm = PipeManager.getInstance();
    private String input_handle = null;
    private boolean writing = false;


    /** constructor, takes path as argument, builds outputstream
     * @param file_path name of file to output text to
     * @throws java.io.IOException if file can't be opened */
    public WriteFile(String file_path) throws java.io.IOException {
           file = file_path;
           out = new PrintWriter( new FileWriter( file ));

    }

    /** indicates if class is writing to a file
     * @return boolean indicating write status
     */
    public boolean isWriting(){
        return writing;
    }

    /** sets input pipe
     * @param new_handle name of input pipe
     */
    public void setInputHandle(String new_handle){
      input_handle = new_handle;
    }

    /** @return name of input pipe */
    public String getInputHandle(){ return input_handle; }

    /** used to write file */
    public void run(){
      // wait for something to come from input_handle, then just write it to the file
      writing = false;
      boolean running = true;
      boolean got_text = false;

      String input_line;
      while (running){
        input_line = (String)pm.read(input_handle);
        if (input_line != null){
          got_text = true;
          out.println( input_line );
        } else if (got_text){
          running = false;
        }
      }
      writing = true;
    }

    /** closes file being written to */
    public void close() {
      out.close();
    }
}
