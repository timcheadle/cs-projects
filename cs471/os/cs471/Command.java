/**
 * abstract class used by os commands
 * <P>
 * <B>Change Summary</B>
 * <TABLE CELLPADDING="7">
 * <TR><TH>1.1</TH><TD>added getArgs()</TD></TR>
 * </TABLE>
 *
 * @author Dave Steinbrunn <BR> Brian Skapura <BR> Tim Cheadle
 * @version 1.1
 * @since 1.4
 */

package cs471;
import cs471.*;
import java.util.*;


public abstract class Command implements Runnable {

    protected String input, output;
    protected boolean exit_status = true;
    protected HashMap env;
    protected ArrayList args;
    protected boolean is_running = false;
    protected int pid;
    protected boolean executed = false;

    public void setExecuted( boolean value ){ executed = value; }
    public boolean commandExecuted() { return executed; }
    public String getInputHandle() { return input; }
    public String getOutputHandle() { return output; }
    public void setRunningState( boolean state ){
      is_running = state;
    }

    /** @return boolean indicating command is currently running */
    public boolean isRunning(){ return is_running; }

    /** used to pass args to command
     *  @param arguments args string of arguments to command */
    public void setArgs( ArrayList arguments ) { args = arguments; }

    /** used to execute command */
    public void run() {}

    /** @param input_handle name of pipe to get input from */
    public void setInput(String input_handle){
        input = input_handle;
    }

    /** @param output_handle name of pipe to send output to */
    public void setOutput(String output_handle){
        output = output_handle;
    }

    /** @return indicates successful completion of command */
    public boolean getExitStatus(){ return exit_status; }

    /** @param success boolean indicating success of command */
    protected void setExitStatus( boolean success ){
        exit_status = success;
    }

    /** @param environment environment values in hashmap format */
    public void setEnvironment( HashMap environment ){
        env = environment;
    }

    /** @return hashmap containing environment values */
    public HashMap getEnvironment(){  return env; }

    /** @return PipeManager being used by command */
    public PipeManager getPipeManager(){ return PipeManager.getInstance(); }

    /** @param process_id pid assigned to command */
    public void setPid(int process_id){
      pid = process_id;
    }

    /** @return pid of command */
    public int getPid(){
      return pid;
    }

    /** @return args passed to command */
    public ArrayList getArgs(){  return args; }


}
