/**
 * command used to list who is logged in
 *
 * @author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.0
 * @since 1.4
 */

import cs471.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class who extends Command{

  /** allows command to be run as a thread */
  public void run(){

    // standard command init stuff
    super.setRunningState(true);
    String input_handle = super.getInputHandle();
    String output_handle = super.getOutputHandle();
    PipeManager pm = super.getPipeManager();
    HashMap env = super.getEnvironment();
    boolean error = false;
    HashSet user_list = null;

    // retrieve data from MessageServlet
    try {
        URL server_url = new URL( (String)env.get("message_server") + "?listRegistered=1");

        try {
            ObjectInputStream response = new ObjectInputStream(server_url.openStream());
            user_list = (HashSet)response.readObject();
            response.close();
        } catch (Exception e){

            pm.write(output_handle, "error:  Can't build object:  " + e.toString() );
            super.setExitStatus(false);
            error = true;
        }


    } catch (Exception e){
        pm.write(output_handle, "error:  Can't build " + (String)env.get("message_server") + "?listRegistered=1" );
        super.setExitStatus(false);
        error = true;
    }

    // actual command stuff
    if (! error){
        Iterator it = user_list.iterator();
        while(it.hasNext()){
            pm.write(output_handle, (String)it.next() );
        }
    }



    // command cleanup
    super.setRunningState(false);
    super.setExecuted(true);
  }
}