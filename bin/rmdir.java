/**
 * This program emulates (very basically) the 'rmdir' command
 * found in most UNIX flavors.
 *
 * @author:  Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version: 1.0
 * @since 1.4
 */

import cs471.*;
import java.util.*;
import java.io.File;

public class rmdir extends Command {

	private static ArrayList args;
	private static String input_handle;
	private static String output_handle;
	private static HashMap env;
	private static PipeManager pm;

	/**
	 * Deletes the given file or directory (if recursion is requested)
	 * @param file	The file or directory to be deleted
	 */
	private static void delete(String file) {
		File f = new File(file);

		if (!f.exists()) {
			pm.write(output_handle, "Cannot delete " + file + ": File not found");
			return;
		}

		// If file is a directory, recursively delete
		if (f.isDirectory()) {
			String[] files = f.list();

			for (int i=0; i < files.length; i++) {
				delete(file+ File.separator +files[i]);
			}

			pm.write(output_handle, "removing: " + file);
			if (!f.delete()) {
				pm.write(output_handle, "Cannot remove " + file);
			}
		} else {
			pm.write(output_handle, "removing: " + file);
			if (!f.delete()) {
				pm.write(output_handle, "Cannot remove " + file);
			}
		}

		return;
	}

    /** allows command to be run as a thread */
    public void run() {
	    args = super.getArgs();
    	super.setRunningState(true);
		input_handle = super.getInputHandle();
		output_handle = super.getOutputHandle();
		env = super.getEnvironment();
		pm = super.getPipeManager();

		// check args to see if any files/dirs were given
		if (args.size() <= 0) {
			pm.write(output_handle, "rmdir: too few arguments");
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
				if (f.isDirectory()) {
					delete(file);
				} else {
					pm.write(output_handle, "Cannot delete " + file + ": Not a directory");
				}
			} else {
				File f = new File(relative_file);
				if (f.isDirectory()) {
					delete(relative_file);
				} else {
					pm.write(output_handle, "Cannot delete " + relative_file + ": Not a directory");
				}
			}
		}

		super.setRunningState(false);
		super.setExecuted(true);
	}
}
