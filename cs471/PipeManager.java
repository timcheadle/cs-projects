package cs471;


import cs471.*;
import java.util.*;

/** class to handle interaction with pipes.  This class is a singleton, use
    the getInstance() method to get access to this class.  This class is meant
    to be used to interact and manage all pipes

@authors:  Dave Steinbrunn, <BR> Tim Cheadle <BR> Brian Skapura
@version:  1.0
@since  1.4

*/
public class PipeManager {

    /* stores pipes that have been created.  use a hashmap since we can delete
     * pipes later on, and deleting from an arraylist causes all elements in
     * arraylist to shift down
     */
    protected static PipeManager pm;
    HashMap pipes = new HashMap();
    int pipe_count = 0;
    private static final boolean DEBUG = false;

    // static code to initialize singleton pm
    static{
        String pmClass = null;
        try {
        pm = (PipeManager)Class.forName("cs471.PipeManager").newInstance();
        } catch (Exception e){
        System.out.println("Can't init PipeManager: " + e.toString());
        }
    }

    /** protected constructor, use getInstance() to get access to pipe manager
     */
    protected PipeManager(){}

    /** factory method for singleton class
     * @return PipeManager instance */
    public static PipeManager getInstance(){
        return pm;
    }

    /** used to create a new pipe
     *  @return name of pipe created
     */
    public String createPipe(){
        String pipe_handle = "pipe" + pipe_count;
        if (DEBUG) System.out.println("PipeManager:  creating new pipe: " + pipe_handle);
        pipes.put( pipe_handle, new Pipe(pipe_handle) );
        pipe_count++;
        return pipe_handle;
    }

    /** closes pipe
     * @param pipe_name name of pipe to close
     */
    public void closePipe(String pipe_name){
        pipes.remove(pipe_name);
    }

    /** used to dump contents of pipe to System.out
     * @param pipe_name name of pipe to dump contents of
     */
    public void dumpContents(String pipe_name){
        ((Pipe)pipes.get(pipe_name)).dumpContents();
    }

    /** writes Object to pipe
     * @param pipe_name name of pipe
     * @param message object to be passed through pipe
     */
    public void write(String pipe_name, Object message){
        ((Pipe)pipes.get(pipe_name)).send(message);
    }

    /** reads data from pipe
     * @param pipe_name name of pipe to read from
     * @return message from pipe
     */
    public Object read(String pipe_name){
        return ((Pipe)pipes.get(pipe_name)).read();
    }

    /** used to signal process at other end of input pipe that input should be
     * gathered
     * @param pipe_name name of pipe
     * @return boolean indicating that process is waiting for input
     */
    public boolean gatherInput(String pipe_name){
        return ((Pipe)pipes.get(pipe_name)).gatherInput();
    }

    /** used to set boolean indicating that process is waiting for input
     * @param pipe_name name of pipe
     * @param value boolean indicating wait status
     */
    public void setGatherInput(String pipe_name, boolean value){
        ((Pipe)pipes.get(pipe_name)).setGatherInput(value);
    }

    /** used by process at input end of pipe to signal that input was gathered
     * @param pipe_name name of pipe
     */
    public void inputGathered(String pipe_name){
        ((Pipe)pipes.get(pipe_name)).inputGathered();
    }
}