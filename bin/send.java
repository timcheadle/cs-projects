
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

public class send extends Command{

  /** allows command to be run as a thread */
  public void run(){

    String to_user = null;
    String from_user = null;
    StringBuffer message = new StringBuffer();
    boolean error = false;

    // standard command init stuff
    super.setRunningState(true);
    String input_handle = super.getInputHandle();
    String output_handle = super.getOutputHandle();
    PipeManager pm = super.getPipeManager();
    Boolean valid_user = new Boolean(false);
    String send_message = null;

    // make sure we have the necessary info
    ArrayList args = super.getArgs();
    try {
        to_user = (String)args.get(0);
    } catch (Exception e){
        pm.write( output_handle, "error:  you need to specify a user to send a message to");
        error = true;
    }

    // need to combine the remaining args into a message

    if (args.size() < 2){
        pm.write( output_handle, "error:  you need to type a message to send to " + to_user);
        error = true;
    }


    if (! error){

        message.append("From: " + env.get("user_id") + "  Message: ");
        for (int i=1; i < args.size(); i++){
            message.append( (String)args.get(i) + " ");
        }

        // make sure the user is logged in

        try {
            URL server_url = new URL( (String)env.get("message_server") + "?isRegistered=" + to_user);

            try {
                ObjectInputStream response = new ObjectInputStream(server_url.openStream());
                valid_user = (Boolean)response.readObject();
                response.close();
            } catch (Exception e){

                pm.write(output_handle, "error:  Can't build object:  " + e.toString() );
                super.setExitStatus(false);
                error = true;
            }
        } catch (Exception e){
            pm.write(output_handle, "error building url: " + e.toString());
        }

        if (valid_user.booleanValue()){
            // html encode the message
            try {
                send_message = URLEncoder.encode( message.toString(), "UTF-8");
                //pm.write(output_handle, "encoded message: " + send_message);
            } catch (Exception e){
                pm.write(output_handle, "can't encode message: " + e.toString());
            }
            try {
                //System.out.println("registering user with message server");
                URL register_url = new URL( (String)env.get("message_server") + "?sendMessage=" + to_user + "&message=" + send_message );
                //System.out.println("opening connection to " + register_url.toString());
                URLConnection uc = register_url.openConnection();
                InputStream result = uc.getInputStream();
                result.close();

                //System.out.println("connected");

            } catch (Exception e){
                System.out.println( "error: " + e.toString());
            }

        } else {
            pm.write(output_handle, to_user + " is not currently logged in");
            super.setExitStatus(false);
        }

    }

    // command cleanup
    super.setRunningState(false);
    super.setExecuted(true);
  }

}