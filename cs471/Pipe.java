package cs471;

import java.util.Vector;

/** class used to represent a pipes
 *
 * @author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.0
 * @since 1.4
 */
public class Pipe {

    private Vector queue;         // used to store messages
    private String name;
    boolean gather_input = false; // used to indicated blocking input

    /** constructor, uses a Vector since its threadsafe
     * @param pipe_name name used when refering to pipe
     */
    public Pipe(String pipe_name) {
        name = pipe_name;
        queue = new Vector();
    }

    /** used to send a message through pipe
     *  @param message message to be sent
     */
    public void send (Object message){
        queue.addElement(message);
    }

    /** used to get name of pipe
     *  @return pipe name
     */
    public String getName(){
      return name;
    }

    /** returns true if process reading from this pipe is waiting
     *  for input from the process writing to this pipe
     *  @return boolean indicating wait state
     */
    public boolean gatherInput(){
        return gather_input;
    }

    /** used to indicate if process reading from this pipe is waiting
     *  for input from the process writing to this pipe
     *  @param value boolean indicating wait state
     */
    public void setGatherInput(boolean value){
        gather_input = value;
    }

    /** used by process writing to pipe to tell process reading from pipe that
     *  input has been gathered
     */
    public void inputGathered(){
        gather_input = false;
    }

    /** used to dump contents of pipe to System.out for debugging purposes */
    public void dumpContents(){
      System.out.println("Contents of " + name);
      for (int i=0; i < queue.size(); i++){
        System.out.println( i + " " + queue.get(i));
      }
    }

    /** used to read from pipe
     *  @return message from pipe
     */
    public Object read(){

        Object item;
        if (queue.size() == 0){
            return null;
        } else {
            item = queue.remove(0);
            return item;
        }
    }

}