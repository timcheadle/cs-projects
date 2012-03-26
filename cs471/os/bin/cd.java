import cs471.*;
import java.util.*;
import java.io.File;
/**
 * cd:  command to change working director
 * syntax:  <code>cd</code> <I>new_directory</I>
 *
 * @author Dave Steinbrunn
 * @since 1.4
 * @version 1.0
 */

public class cd extends Command {

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

        // 1st argument should be the directory we want to change to
        try {
            File file = new File((String)args.get(0));
            if (file.isDirectory()){
                // send set_env message to console
                pm.write(output_handle, "_SET_ENV current_dir " + file.toString());
            } else {

                // see if its a directory in the current directory
                current_dir = (String)env.get("current_dir") + File.separator + file.toString();
                file = new File(current_dir);
                if (file.isDirectory()){
                    pm.write(output_handle, "_SET_ENV current_dir " + file.toString());
                } else {
                    pm.write(output_handle, "error:  " + file.toString() + " is not a valid directory");
                }
            }
        } catch (Exception e){
            pm.write(output_handle, "error:  you must provide an argument for command cd");
        }

        // command cleanup
        super.setRunningState(false);
        super.setExecuted(true);

    }
}