/**
 * command used to display current working directory
 *
 * @author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.0
 * @since 1.4
 */

import cs471.*;
import java.util.HashMap;

public class pwd extends Command{

  /** allows command to be run as a thread */
  public void run(){

    // standard command init stuff
    super.setRunningState(true);
    String input_handle = super.getInputHandle();
    String output_handle = super.getOutputHandle();
    HashMap env = super.getEnvironment();
    PipeManager pm = super.getPipeManager();

    // actual command stuff
    pm.write(output_handle, (String)env.get("current_dir"));
    pm.write(output_handle, "_EOF");

    // command cleanup
    super.setRunningState(false);
    super.setExecuted(true);
  }
}



