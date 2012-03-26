/**
 * used to display help messages
 *
 * @author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.0
 * @since 1.4
 */

import cs471.*;
import java.io.*;
import java.util.*;
import java.io.File;
import java.util.regex.*;

public class help extends Command{

  /** allows command to be run as thread */
  public void run(){
    // standard command init stuff
    super.setRunningState(true);
    String input_handle = super.getInputHandle();
    String output_handle = super.getOutputHandle();
    HashMap env = super.getEnvironment();
    PipeManager pm = super.getPipeManager();
    HashSet commands = new HashSet();
    ArrayList args = super.getArgs();

    Pattern p = Pattern.compile("^(\\S+)\\.class$");
    Matcher m;

    // actual command stuff
    String[] dir_contents = new File( (String)env.get("path") ).list();
    for (int i=0; i < dir_contents.length; i++){
        m = p.matcher( dir_contents[i] );
        if (m.find()){
            commands.add(m.group(1));
        }
    }

    // if arg was passed, print help file for arg, if it exists
    if ( args != null && args.size() > 0){
        File help_file = new File( (String)env.get("path") + File.separator + (String)args.get(0) + ".txt");
        String line;
        if (help_file.isFile()){
            try {
                BufferedReader is = new BufferedReader(new FileReader( help_file ));
                pm.write(output_handle, "--------------------");
                while(( line = is.readLine()) != null ){
                    pm.write(output_handle, (Object)line );
                }
                pm.write(output_handle, "--------------------");
            } catch (Exception e){
                pm.write(output_handle, "error: " + e.toString());
                super.setExitStatus(false);
            }
        } else {
            pm.write(output_handle, "error:  no help file for " + (String)args.get(0));
            super.setExitStatus(false);
        }
    } else {
        // just show a list of available commands
        Iterator itr = commands.iterator();
        int print_count = 0;
        StringBuffer sb = new StringBuffer();
        while (itr.hasNext()){
            print_count++;
            sb.append( (String)itr.next() + "   ");
            if (print_count == 3){
                pm.write(output_handle, sb.toString());
                sb.delete(0, sb.length());
                print_count = 0;
            }
        }
        if (print_count > 0){
            pm.write(output_handle, sb.toString());
        }
    }

    pm.write(output_handle, "_EOF");
    // command cleanup
    super.setRunningState(false);
    super.setExecuted(true);
  }

}