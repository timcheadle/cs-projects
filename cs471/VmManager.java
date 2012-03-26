package cs471;
/**
 * used to manage virtual machines in os. runs as a singleton, use factory
 * method getInstance() to access class
 *
 * @author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.0
 * @since 1.4
 *
 */
import java.util.*;
import java.util.regex.*;
import cs471.*;


public class VmManager {

    private int pid_count = 0;
    protected static VmManager vmm;

    // static code to initialize singleton vmm
    static{
      String vmmClass = null;
      try {
        vmm = (VmManager)Class.forName("cs471.VmManager").newInstance();
      } catch (Exception e){
        System.out.println("Can't init VmManager: " + e.toString());
      }
    }

    /** protected constructor, use factory method getInstance to access class */
    protected VmManager(){}

    /** factory method
     * @return VmManager instance
     */
    public static VmManager getInstance(){ return vmm; }

    /** used to create a new virtual machine to run a command in
     * @param command_name name of command to execute
     * @param args ArrayList of arguments
     * @param input_handle name of input pipe
     * @param output_handle name of output pipe
     * @param parent_pid pid of parent vm
     * @param environment HashMap containing environment for command
     * @return virtual machine of command
     */
    public VirtualMachine create(String command_name, ArrayList args, String input_handle, String output_handle, int parent_pid, HashMap environment){

        // create a new virtual machine
        VirtualMachine temp = new VirtualMachine( command_name, input_handle, output_handle, pid_count, args, parent_pid, null, 0, environment);
        pid_count++;
        return temp;

    }


}