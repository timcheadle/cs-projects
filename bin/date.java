/** date command for os project
 *
 * @author Tim Cheadle <BR> Brian Skapura <BR> Dave Steinbrunn
 * @version 1.0
 * @since 1.4
 */

import cs471.*;
import java.util.HashMap;
import java.util.Date;
import java.text.DateFormat;

public class date extends Command{

  /** used to run command as a thread */
  public void run(){

    // standard command init stuff
    super.setRunningState(true);
    String input_handle = super.getInputHandle();
    String output_handle = super.getOutputHandle();
    HashMap env = super.getEnvironment();
    PipeManager pm = super.getPipeManager();

    // actual command stuff
    Date now = new Date();
    String date = DateFormat.getDateTimeInstance().format(now);
    pm.write(output_handle, date);
    pm.write(output_handle, "_EOF");

    // command cleanup
    super.setRunningState(false);
    super.setExecuted(true);
  }
}



