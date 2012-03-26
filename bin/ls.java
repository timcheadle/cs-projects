

/**
 * used to list files in a directory
 *
 * @author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.0
 * @since 1.4
 */

import cs471.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ls extends Command{

  /** allows command to be run as thread */
  public void run(){
    // standard command init stuff
    super.setRunningState(true);
    String input_handle = super.getInputHandle();
    String output_handle = super.getOutputHandle();
    HashMap env = super.getEnvironment();
    PipeManager pm = super.getPipeManager();
    ArrayList args = super.getArgs();

    // actual command stuff
    String working_dir = (String)env.get("current_dir");
    // figure out what user wants to look at
    String current_dir = null;
    if (args != null){
        current_dir = (String)args.get(0);
    } else {
        current_dir = working_dir;
    }
    File ls_dir = new File(current_dir);
    ArrayList ls_result = new ArrayList();

    if (ls_dir.isDirectory()){
        String[] dir_contents = ls_dir.list();
        for (int i=0; i < dir_contents.length; i++){
            ls_result.add( dir_contents[i] );
        }
    } else if (ls_dir.isFile()){
        ls_result.add( ls_dir.toString() );
    } else {

        // check to see if we add current working dir if we can find it
        ls_dir = new File(working_dir + File.separator + ls_dir.toString());
        if (ls_dir.isDirectory()){
            String[] dir_contents = ls_dir.list();
            for (int i=0; i < dir_contents.length; i++){
                ls_result.add( dir_contents[i] );
            }
        } else if (ls_dir.isFile()){
            ls_result.add( ls_dir.toString() );
        } else {
            // one last try, see if the arg compiles as a regex and use it against
            // files in the working directory
            if (args != null){
                Pattern file_pattern = Pattern.compile( (String)args.get(0) );
                Matcher n;
                ls_dir = new File( working_dir );
                String[] dir_contents = ls_dir.list();
                for (int k=0; k < dir_contents.length; k++){
                    n = file_pattern.matcher( dir_contents[k] );
                    if ( n.find()){
                        ls_result.add( dir_contents[k] );
                    }
                }
            } else {
                ls_result.add("error:  " + ls_dir.toString() + " not found");
            }
        }
    }

    for (int j=0; j < ls_result.size(); j++){
        pm.write(output_handle, (String)ls_result.get(j));
    }
    pm.write(output_handle, "_EOF");
    // command cleanup
    super.setRunningState(false);
    super.setExecuted(true);
  }

}