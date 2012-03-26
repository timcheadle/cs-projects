/**
 * This program emulates (very basically) the 'mkdir' command
 * found in most UNIX flavors.
 *
 * @author  Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.0
 * @since 1.4
 */


import cs471.*;
import java.util.*;
import java.io.File;

public class mkdir extends Command {

	private static ArrayList args;
	private static String input_handle;
	private static String output_handle;
	private static HashMap env;
	private static PipeManager pm;

	/** allows command to be run as thread */
    public void run() {
        args = super.getArgs();
		super.setRunningState(true);
		input_handle = super.getInputHandle();
		output_handle = super.getOutputHandle();
		env = super.getEnvironment();
		pm = super.getPipeManager();

		// check args to see if any directories were given
		if (args.size() <= 0) {
			pm.write(output_handle, "mkdir: too few arguments");
			super.setRunningState(false);
			super.setExecuted(true);
		}

		for (int i = 0; i < args.size(); i++) {
			String file = (String)args.get(i);
			String cur_dir = (String)env.get("current_dir");
			String relative_file = cur_dir + File.separator + file;

			// check if file was given as a relative path or absolute
			if (file.substring(0,1).equals(File.separator)) {
				File f = new File(file);
				if (!f.exists()) {
					f.mkdirs();
				} else {
					pm.write(output_handle, "Cannot create " + file + ": Already exists");
				}
			} else {
				File f = new File(relative_file);
				if (!f.exists()) {
					f.mkdirs();
				} else {
					pm.write(output_handle, "Cannot create " + relative_file + ": Already exists");
				}
			}
		}

		super.setRunningState(false);
		super.setExecuted(true);
	}
}
