package cs471;

/** class used to represent virtual machine in os, acts as a standard interface
 * used by os to run and interact with commands.
 *
 * @author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.1  (added setArgs)
 * @since 1.4
 *
 */

import java.util.*;
import java.lang.*;
import java.lang.reflect.*;
import java.io.*;
import cs471.*;

public class VirtualMachine extends ClassLoader implements Runnable {

    private String input, output;
    int parent_id;
    private ArrayList siblings;
    private int pid, undone;
    private String command_file;
    private ArrayList args;
    private HashMap env;
    private Command command = null;    // command we have autoloaded
    private boolean exit_state = true;   // true indicates success, set to false for error
    private boolean is_running = false;  // used to indicate if the process is running
    private PipeManager pm;
    private static final boolean DEBUG = false;

    /** constructor
     *  @param command_name command to execute
     *  @param input_handle pipe to receive input from
     *  @param output_handle pipe to send output to
     *  @param process_id pid of this process
     *  @param argument_list arguments for process to use
     *  @param parent_pid parent process id
     *  @param sibling_handles handles to sibling processes
     *  @param undone_count number of children still executing
     *  @param environment environment of console
     */
    public VirtualMachine(String command_name,
                          String input_handle,
                          String output_handle,
                          int process_id,
                          ArrayList argument_list,
                          int parent_pid,
                          ArrayList sibling_handles,
                          int undone_count,
                          HashMap environment) {

        input = input_handle;
        output = output_handle;

        pid = process_id;
        parent_id = parent_pid;
        siblings = sibling_handles;
        undone = undone_count;
        env = environment;
        command_file = command_name;
        args = argument_list;
        pm = PipeManager.getInstance();
        if (DEBUG){
           System.out.println("VirtualMachine:  creating vm for " + command_file);
           System.out.println("VirtualMachine:  " + command_file + " input set to " + input);
           System.out.println("VirtualMachine:  " + command_file + " output set to " + output);
        }

        // try to load the command
        Class c = null;

        String full_command = new String( (String)env.get("path") + File.separator + command_file + ".class");
        File classFile = new File(full_command);
        if ( classFile.isFile() ){

            FileInputStream fd = null;
            try {
     	        fd = new FileInputStream(classFile);
 	    } catch(Exception e) {
     	        System.out.println("error:  unknown command + " + command_file);
                exit_state = false;
            }

            if (exit_state){
                long len = classFile.length();
                byte[] content = new byte[(int)len];
                try {
     	            fd.read(content);  // read the class file
                } catch(Exception e) {
                    System.out.println("error:  error loading command: " + e.toString());
                    exit_state = false;
      	        }

                c = defineClass(command_file, content, 0, content.length);
                // class is loaded
                Class pc = c.getSuperclass();
      	        String pName = pc.getName();
                if(pName.equals("cs471.Command")) {
                    try {
                        Constructor con = c.getConstructor( null );
                        Object obj[] = new Object[]{};
                        //System.out.println("command loaded");
   		        command = (Command)con.newInstance(obj);
                        command.setArgs(args);
                        command.setEnvironment( env );
                        command.setInput(input);
                        command.setOutput(output);
                        command.setPid(pid);

                    } catch (Exception e){
                        System.out.println("error:  cannot load " + classFile + ":  " + e.toString());
                        e.printStackTrace();
                        exit_state = false;
                    }
                } else {
                    System.out.println("error:  cannot execute " + command_file);
                    exit_state = false;
                }
            } else {
                System.out.println("error:  unknown command + " + command_file);
                exit_state = false;
            }
        } else {
            System.out.println( "error:  invalid command " + command_file);
            exit_state = false;
        }
    }

    /** used to execute command loaded */
    public void run(){

        Thread t;
        try {
          t = new Thread(command);
          t.start();
          if (DEBUG) System.out.println("Virtual Machine:  " + command_file + " started");
          exit_state = command.getExitStatus();
        } catch (Exception e){
          e.printStackTrace();
          System.out.println("error executing " + command_file + ": " + e.toString());
          exit_state = false;
        }


    }

    /** @return exit status of command */
    public boolean getExitStatus(){ return command.getExitStatus(); }

    /** @return boolean indicating if command is running */
    public boolean getRunningStatus(){ return command.isRunning(); }

    /** @return boolean indicating if command actually ran */
    public boolean commandExecuted(){ return command.commandExecuted(); }

    /** sets input pipe
     * @param input_handle name of input pipe */
    public void setInput(String input_handle){ command.setInput( input_handle ); }

    /** sets output pipe
     * @param output_handle name of output pipe */
    public void setOutput(String output_handle){ command.setOutput( output_handle ); }

    /** sets command environment
     * @param environment environment hashmap
     */
    public void setEnvironment(HashMap environment){ command.setEnvironment(environment); }

    /** @return returns pid */
    public int getPid(){ return pid; }


    public void setArgs(ArrayList args){ command.setArgs( args ); }


}
