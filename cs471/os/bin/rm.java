/**
 * This program emulates (very basically) the 'rm' command
 * found in most UNIX flavors.
 *
 * @author  Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.0
 * @since 1.4
 */

import cs471.*;
import java.util.*;
import java.io.File;

public class rm extends Command {

	private static boolean recurse = false;
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

		// If file is a directory, check recurse
		if (f.isDirectory()) {
			// If recurse is true, go down the tree and delete
			if (recurse) {
				String[] files = f.list();

				for (int i=0; i < files.length; i++) {
					delete(file+ File.separator +files[i]);
				}

				pm.write(output_handle, "removing: " + file);
				if (!f.delete()) {
					pm.write(output_handle, "Cannot remove " + file);
				}
			} else {
				pm.write(output_handle, "'" + file + "' is a directory");
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

		// check args to see if any directories were given
		if (args.size() <= 0) {
			pm.write(output_handle, "rm: too few arguments");
			super.setRunningState(false);
			super.setExecuted(true);
		}

		// Check for -r flag
		if (((String)args.get(0)).equals("-r")) {
			recurse = true;
		}

		for (int i=recurse ? 1:0; i < args.size(); i++) {
			String file = (String)args.get(i);
			String cur_dir = (String)env.get("current_dir");
			String relative_file = cur_dir + File.separator + file;

			// check if file was given as a relative path or absolute
			if (file.substring(0,1).equals(File.separator)) {
				delete(file);
			} else {
				delete(relative_file);
			}
		}

		super.setRunningState(false);
		super.setExecuted(true);
	}
}
