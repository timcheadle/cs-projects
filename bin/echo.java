/**
 * used to test pipes, asks for user input and prints it to console
 *
 * @author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.0
 * @since 1.4
 */

import cs471.*;
import java.io.*;
import java.util.*;

public class echo extends Command {

    private ArrayList args;

    /** allows command to be run as a thread */
    public void run(){

        super.setRunningState(true);
        String input_handle = super.getInputHandle();
        String output_handle = super.getOutputHandle();
        boolean waiting_for_response = true;
        PipeManager pm = super.getPipeManager();
        pm.write(output_handle, "echo: ");
        pm.setGatherInput(input_handle, true);
        while (waiting_for_response){
            String console_response = (String)pm.read(input_handle);
            if (console_response != null){
                pm.write(output_handle, "echo response: " + console_response );
                waiting_for_response = false;
            }
        }
        super.setRunningState(false);
        super.setExecuted(true);

    }

}