
import cs471.*;
import java.util.*;
import java.io.File;

/**
 * mv:  command to rename and move a file
 * syntax:  <code>mv</code> <I>file</I> <I>new_file</I>
 *
 * @author Dave Steinbrunn
 * @since 1.4
 * @version 1.0
 */

public class mv extends Command {

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
            if (file.isFile()){
                try {
                    file.renameTo( new File( (String)args.get(1) ) );
                } catch (Exception e){
                    pm.write(output_handle, "error:  you must provide a new name");
                    super.setExitStatus(false);
                }
            } else {

                // see if its a directory in the current directory
                current_dir = (String)env.get("current_dir") + File.separator + file.toString();
                file = new File(current_dir);
                if (file.isFile()){
                    try {
                        file.renameTo( new File( (String)args.get(1) ));
                    } catch (Exception e){
                        pm.write(output_handle, "error:  you must provide a new name");
                        super.setExitStatus(false);
                    }
                } else {
                    pm.write(output_handle, "error:  can't find" + file.toString());
                    super.setExitStatus(false);
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