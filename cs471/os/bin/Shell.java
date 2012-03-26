/**
 * class to act as shell for cs471 os simulator
 *
 * to do:
 *      delete completed vms
 *      delete completed pipes
 *      have ReadFile use current_working_dir
 *      executing this command creates new shell to replace exisiting shell
 *      have login command return a shell instance
 *
 * @author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.0
 * @since 1.4
 */

import java.io.*;
import java.util.*;
import java.util.regex.*;
import cs471.*;
import java.net.*;

public class Shell extends Command implements Runnable {

    private String STDIN;
    private String STDOUT;
    private HashMap env;
    private boolean exit = false;
    private PipeManager pm;
    private String input_line;
    private boolean exit_status = true;
    private static final boolean DEBUG = false;
    private boolean parse_error;
    private Thread t;
    private ArrayList command_script;


    public void run(){

        command_script = new ArrayList();
        boolean parse_error = false;
        ArrayList commands = new ArrayList();
        ArrayList write_files = new ArrayList();
        ArrayList wf_pipes = new ArrayList();
        STDIN = super.getInputHandle();
        STDOUT = super.getOutputHandle();
        Pattern p = Pattern.compile("^VM(\\d+)");
        pm = super.getPipeManager();
        env = super.getEnvironment();
        super.setRunningState(true);
        int shell_pid = super.getPid();
        String command_name, oh;
        String output_pipe = null;
        VmManager vmm = VmManager.getInstance();
        boolean set_output = false;
        boolean set_input = false;
        int oh_num;
        int write_count = 0;
        boolean open_wfs = false;
        Matcher m;



        // wait for input from STDIN
        input_line = null;
        while (! exit){
            if ( ((String)env.get("logout")).equals("true")){
                System.out.println("shell is exiting");
                exit = true;
            }
            input_line = (String)pm.read(STDIN);
            if (input_line != null){
                if (DEBUG) System.out.println("Shell:  received: " + input_line);
                    parse_error = false;
                    command_script = this.parseCommands(input_line);

                    // print command script if debug is on
                    if (DEBUG){
                        if (! exit && ! parse_error){
                            pm.write(STDOUT, "command script: ");
                            for (int i = 0; i < command_script.size(); i++){
                                HashMap temp = (HashMap)command_script.get(i);
                                Iterator it = temp.entrySet().iterator();
                                while (it.hasNext()){
                                    Map.Entry e = (Map.Entry)it.next();
                                    if ( ((String)e.getKey()).equals("args") ){
                                        pm.write(STDOUT, "args passed: " + ((ArrayList)e.getValue()).toString() );
                                    } else {
                                        pm.write(STDOUT, (String)e.getKey() + " = " + (String)e.getValue() );
                                    }
                                }
                            }
                        }
                    }

                    // get a list of valid commands that are in the bin directory
                    String[] valid_commands = new File( (String)env.get("path")).list();
                    // to save time, just build a list of the files and store in a hashset
                    Pattern class_pattern = Pattern.compile("(\\S+)\\.class");
                    HashSet known_commands = new HashSet();
                    for (int x=0; x < valid_commands.length; x++){
                        Matcher n = class_pattern.matcher( valid_commands[x] );
                        if (n.find()){
                            known_commands.add( n.group(1) );
                        }
                    }

                    // create necessary pipes and virtual machines
                    // construct vm's
                    for( int i=0; i < command_script.size(); i++){
                        command_name = (String)(((HashMap)command_script.get(i)).get("command"));
                        // make sure its a valid command
                        if ( known_commands.contains(command_name)){
                            args = (ArrayList)(((HashMap)command_script.get(i)).get("args"));

                            /* get vm, store in arraylist.  send output to console by default,
                             * we can change it later if we need to
                             */

                            commands.add(i, vmm.create(command_name, args, STDIN, STDOUT, shell_pid, env)    );
                            // modify inputs/outputs if necessary
                            if (set_input){

                                // current process receives its input from previous process
                                ((VirtualMachine)(commands.get(i))).setInput( output_pipe );
                                set_input = false;

                            }
                            // determine where we are sending output, and change it if necessary
                            if (! (((HashMap)command_script.get(i)).get("output_handle")).equals("STDOUT") ){
                                // yes, we need to change it.  see if its a file or another process
                                oh = (String)((HashMap)command_script.get(i)).get("output_handle");
                                /* if oh is a string starting with VM and ending with an integer, its another
                                 * process, otherwise treat is as a file
                                 */
                                m = p.matcher(oh);
                                if (m.find()){
                                    // its a process, create a pipe to use and set current processes output
                                    // to this pipe
                                    output_pipe = pm.createPipe();
                                    ((VirtualMachine)(commands.get(i))).setOutput( output_pipe );
                                    /* set a flag alert the next process that its input should be
                                     * this process
                                     */
                                    set_input = true;
                                } else {
                                    // sending output to a file
                                    try {
                                        write_files.add( write_count, new WriteFile(oh) );
                                        ((WriteFile)(write_files.get(write_count))).setInputHandle( pm.createPipe() );
                                        ((VirtualMachine)(commands.get(i))).setOutput( ((WriteFile)(write_files.get(write_count))).getInputHandle() );
                                        ((WriteFile)(write_files.get(write_count))).start();
                                        write_count++;
                                        open_wfs = true;
                                    } catch (Exception e){
                                        pm.write(STDOUT, "error:  can't open file " + oh + ": " + e.toString());
                                        exit_status = false;
                                    }
                                }
                            }
                            // see where we are getting input from and set if necessary
                            if (! (((HashMap)command_script.get(i)).get("input_handle")).equals("STDIN") ){
                               // yes, we need to change it.  see if its a file or another process
                                oh = (String)((HashMap)command_script.get(i)).get("input_handle");
                                /* if oh is a string starting with VM and ending with an integer, its another
                                 * process, otherwise treat is as a file
                                 */
                                m = p.matcher(oh);
                                if (m.find()){
                                    // its a process, store it
                                    oh_num = (new Integer(m.group(1))).intValue();
                                    /* set a flag alert the next process that its input should be
                                     * this process, and reset this processes put to
                                     * the next process
                                     */
                                    set_input = true;
                                } else {
                                    // getting input from a file
                                    // make sure file exits
                                    File testfile = new File(oh);
                                    if ( testfile.isFile()){
                                        // create a pipe
                                        String read_pipe = pm.createPipe();
                                        ReadFile rf = new ReadFile( oh, read_pipe);
                                        ((VirtualMachine)(commands.get(i))).setInput(read_pipe);
                                        rf.start();
                                    } else {
                                        parse_error = true;
                                        pm.write(STDOUT, "error:  can't find input file " + oh);
                                    }
                                }


                            }
                        } else {
                            i = command_script.size();
                            parse_error = true;
                            pm.write(STDOUT, "error:  " + command_name + " is not a valid command");
                        }
                    }

                if (exit_status && ! parse_error){
                    // kick off vms
                    for (int i = 0; i < commands.size(); i++){
                        ((VirtualMachine)commands.get(i)).run();
                    }

                    // get exit status, and return it
                    boolean cont_check = true;
                    int process_count = commands.size();
                    int processes_checked = 0;

                    if (! exit){
                        while (cont_check){
                            if ( ((VirtualMachine)(commands.get(processes_checked))).commandExecuted()){
                                if (! ((VirtualMachine)(commands.get(processes_checked))).getRunningStatus()){
                                    if (! ((VirtualMachine)(commands.get(processes_checked))).getExitStatus()){
                                        exit_status = false;
                                        cont_check = false;
                                    } else {
                                        processes_checked++;
                                        if (processes_checked == process_count){
                                            cont_check = false;
                                        }
                                    }
                                }
                            } else {
                                processes_checked = 0;
                            }
                        }
                    }
                }

                // close any open WriteFiles and delete pipes
                if (open_wfs){
                    open_wfs = false;
                    boolean checking_wf = true;
                    int current_wf = 0;
                    while (checking_wf){
                        if ( ((WriteFile)(write_files.get(current_wf))).isWriting()){
                            ((WriteFile)(write_files.get(current_wf))).close();
                            pm.closePipe( ((WriteFile)(write_files.get(current_wf))).getInputHandle() );
                            current_wf++;
                            if (current_wf >= write_files.size()){
                                checking_wf = false;
                            }
                        }
                    }
                }
                commands.clear();
                //write_files.clear();
                pm.write(STDOUT, "_END_OUTPUT");
            }
        }
        super.setRunningState(false);
    }

    private ArrayList parseCommands( String input_line ){

        boolean command_stored = false;
        boolean set_input = false;
        boolean store_command = true;
        boolean store_args = false;
        boolean set_output = false;
        boolean cont_parsing = true;
        boolean pipe_seen = false;   // used to track if user typed two pipes in a row (e.g. cat >>)
        String command = null;
        ArrayList args = null;
        ArrayList command_script = new ArrayList();
        HashMap temp;
        String token;
        StringTokenizer stok;
        int vmcount = 0;
        parse_error = false;
        String error_msg = null;

        stok = new StringTokenizer(input_line);
        while (cont_parsing){

            if (stok.hasMoreTokens()){
                token = stok.nextToken();
                // check to see if this token is a command (i.e.  first token ecountered)
                if (! command_stored){
                    /*if (token.equals("exit")){
                        exit = true;
                        //pm.write(STDOUT, "_END_OUTPUT");
                        cont_parsing = false;
                    */
                    if (token.equals("|") || token.equals("<") || token.equals(">")){
                        parse_error = true;
                        cont_parsing = false;
                        error_msg = "Syntax error:  no command provided before " + token;
                    } else {
                        pipe_seen = false;
                        // need to create a new hashmap to store command info in the command script
                        command_script.add(vmcount, new HashMap());
                        // store the vm name
                        ((HashMap)command_script.get(vmcount)).put("name", "VM" + vmcount);
                        /* if set_input tag is set, set the input_handle to the previous vm,
                         * otherwise just set it to STDIN (keyboard)
                         */
                        if (set_input){
                            ((HashMap)command_script.get(vmcount)).put("input_handle", "VM" + (vmcount - 1));
                            set_input = false;
                        } else {
                            ((HashMap)command_script.get(vmcount)).put("input_handle", "STDIN");
                        }

                        // if set_output tag is set, current vm will send its output to next command
                        if (set_output){
                            ((HashMap)command_script.get(vmcount - 1)).put("output_handle", "VM" + (vmcount));
                            ((HashMap)command_script.get(vmcount)).put("output_handle", "STDOUT");
                            set_output = false;
                        } else {
                            ((HashMap)command_script.get(vmcount)).put("output_handle", "STDOUT");
                        }

                        // save the command name
                        ((HashMap)command_script.get(vmcount)).put("command", token);

                        // store any args from previous command
                        if (store_args){
                            ((HashMap)command_script.get(vmcount - 1)).put("args", args);
                            store_args = false;
                        }
                        // build a new string buffer to store args
                        args = new ArrayList();
                        command_stored = true;
                    }
                } else {
                    if (token.equals("|")){
                        /* pipe, next command encountered needs to have its
                         * input set to current command, and current command needs
                         * its output sent to next command
                         */
                        if (! pipe_seen){
                            set_input = true;
                            set_output = true;
                            pipe_seen = true;
                            // store next token as a command
                            command_stored = false;
                            vmcount++;
                        } else {
                            // 2nd pipe in a row encountered, error out
                            parse_error = true;
                            cont_parsing = false;
                            error_msg = "Syntax Error: invalid pipe structure";
                        }
                    } else if (token.equals("<")){
                        if (! pipe_seen){
                            pipe_seen = true;
                            /* next token is a file we need to use as the input_handle
                             * for current vm */
                            if (stok.hasMoreTokens()){
                                ((HashMap)command_script.get(vmcount)).put("input_handle", stok.nextToken());
                            } else {
                                parse_error = true;
                                cont_parsing = false;
                                error_msg = "Syntax Error:  no file specified for input with \"<\"";
                            }
                        } else {
                            // 2nd pipe in a row encountered, error out
                            parse_error = true;
                            cont_parsing = false;
                            error_msg = "Syntax Error: invalid pipe structure";
                        }
                    } else if (token.equals(">")){
                        if (! pipe_seen){
                            pipe_seen = true;
                            // output of current command is set to next token, which is a file
                            if (stok.hasMoreTokens()){
                                ((HashMap)command_script.get(vmcount)).put("output_handle", stok.nextToken());
                            } else {
                                parse_error = true;
                                cont_parsing = false;
                                error_msg = "Syntax Error:  no file specified for output with \">\"";
                            }
                        } else {
                            // 2nd pipe in a row encountered, error out
                            parse_error = true;
                            cont_parsing = false;
                            error_msg = "Syntax Error: invalid pipe structure";
                        }

                    } else {
                        pipe_seen = false;
                        //text, treat it as an argument
                        args.add(token);
                        // set the flag to store args
                        store_args = true;
                    }
                }
            } else {
                cont_parsing = false;
            }
        }
        if (store_args){
            // store any remaining arguments
            try {
                ((HashMap)command_script.get(vmcount)).put("args", args);
            } catch (Exception e) {
                parse_error = true;
                error_msg = "Syntax Error:  no command provided after |";
            }
        }

        if (parse_error){
            pm.write(STDOUT, error_msg );
            pm.write(STDOUT, "_END_OUTPUT");
        }

        return command_script;

    }


}
