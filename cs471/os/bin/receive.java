/**
 * command used to display messages from other users
 *
 * @author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.0
 * @since 1.4
 */

import cs471.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class receive extends Command{

  /** allows command to be run as a thread */
  public void run(){

    // standard command init stuff
    super.setRunningState(true);
    String input_handle = super.getInputHandle();
    String output_handle = super.getOutputHandle();
    PipeManager pm = super.getPipeManager();
    boolean error = false;

    // actual command stuff

    String user_id = (String)env.get("user_id");
    ArrayList messages = null;

    // get messages from the server
    try {
        URL server_url = new URL( (String)env.get("message_server") + "?getMessages=" + user_id);

        try {
            ObjectInputStream response = new ObjectInputStream(server_url.openStream());
            messages = (ArrayList)response.readObject();
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

    if (! error){
        if ( messages.size() > 0 ){
            for (int i=0; i < messages.size(); i++){
                pm.write(output_handle, (String)messages.get(i));
            }
        } else {
                pm.write(output_handle, "no new messages for " + user_id);
        }
    }


    // command cleanup
    super.setRunningState(false);
    super.setExecuted(true);
  }
}