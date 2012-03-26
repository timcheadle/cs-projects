
import cs471.*;
import java.util.*;
import java.io.File;
/**
 * logout:  command to signal shell to exit
 * syntax:  <code>cd</code> <I>new_directory</I>
 *
 * @author Dave Steinbrunn
 * @since 1.4
 * @version 1.0
 */

public class logout extends Command {

    /** used to run command as thread */
    public void run(){

        // set up command
        super.setRunningState(true);
        String input_handle = super.getInputHandle();
        String output_handle = super.getOutputHandle();
        HashMap env = super.getEnvironment();
        PipeManager pm = super.getPipeManager();
        ArrayList args = super.getArgs();
        String current_dir = null;

        pm.write(output_handle, "_SET_ENV logout true");

        // command cleanup
        super.setRunningState(false);
        super.setExecuted(true);

    }
}
