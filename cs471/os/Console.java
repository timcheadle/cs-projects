/**
 * console class for cs471 os simulator
 * <P>
 * <B>Note</B>:  /bin and /etc must be in user home directory, or <CODE>-bin</CODE> <I>path
 * </I> and <CODE>-etc</CODE> <I>etc_path</I> must be passed as args
 * <P>
 * <B>Summary of Changes</B>
 * <TABLE CELLPADDING="7">
 * <TR><TH>1.2</TH>
 * <TD>added environment change ability in pipe to console (STDOUT),
 *     also cleaned up code, adding login() method </TD>
 * </TR><TR>
 * <TH>1.3</TH>
 * <TD> added storing of user_id in env hashmap, _EOF is ignored, and messagebox notification </TD>
 * </TR>
 * </TABLE>
 * @author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.3
 * @since 1.4
 */

/* to do:
 *   ls work with arguments
 *   login returns shell instance used
 *   edit command
 *   fix cat > file  to work like edit <file>
 *
 */

import java.io.*;
import java.util.*;
import java.util.regex.*;
import cs471.*;
import java.net.*;

public class Console implements Runnable {

    // put in the url for the MessageServlet here
    private String SERVLET_URL = "http://localhost:8080/MessageServlet/message";

    private static Console console;
    private HashMap env = new HashMap();  // stores user environment
    private static final boolean DEBUG = false;
    // we create a vmm and a pm, but pass them to the shell later
    private VmManager vmm;
    private PipeManager pm;
    private String STDIN, STDOUT, SHELL_IN;
    private VirtualMachine shell;


    /** constructor, sets up console environment
     *@param args args passed to main()
     */
    public Console(String[] args){

        if (DEBUG) System.out.println("Console:  initializing");
        boolean bin_set = false;
        boolean etc_set = false;

        // just cycle through the args array
        for (int i=0; i < args.length; i++){
            if ( args[i].equals("-bin")){
                env.put("path", args[i+1]);
                i++;
                bin_set = true;
            } else if ( args[i].equals("-etc")){
                env.put("etc", args[i+1]);
                i++;
                etc_set = true;
            }
        }

        // store messageservlet url
        env.put("message_server", SERVLET_URL);
        env.put("logout", "false");
        // get current directory
        String current_dir = System.getProperty("user.home",".");
        env.put("current_dir", current_dir);

        // if no args were passed, see if the are in a subdir of the current dir
        if (! etc_set || ! bin_set){

            // see if an etc dir exists for config files
            if (! etc_set){
                env.put("etc", current_dir + File.separator + "etc");
                File g = new File( (String)env.get("etc") );
                if (! g.isDirectory()){
                    System.out.println("Error:  can't determine path to /etc directory");
                    System.exit(0);
                }
            }

            // see if a bin dir exists
            if (! bin_set){
                env.put("path", current_dir + File.separator + "bin");
                File f = new File( (String)env.get("path") );
                if (!  f.isDirectory()){
                    System.out.println("Error:  can't determine path to /bin directory");
                    System.exit(0);
                }
            }
        }
        if (DEBUG){
            Iterator it = env.entrySet().iterator();
            System.out.println("Console:  Environmental Settings:");
            while (it.hasNext()){
                Map.Entry e = (Map.Entry)it.next();
                System.out.println("Console:      " + e.getKey() + ": " + e.getValue());
            }
        }


        // create the pipes we need to get input/output from console
        pm = PipeManager.getInstance();
        STDIN = pm.createPipe();
        STDOUT = pm.createPipe();

        // create a VmManager so we can get a shell instance
        vmm = VmManager.getInstance();

    }

    /** handles intial login
     * @return boolean indicating login success/fail
     */
    public boolean login(){

        boolean login_success = false;

        System.out.println("Welcome to l33t OS");
        System.out.println("You must log in before you can continue!");

        // need to run login to verify the user
        VirtualMachine login = vmm.create( "login", null, STDIN, STDOUT, 0, env);
        boolean waiting_for_response = true;
        String input_line;
        login.run();
        String login_msg;
        // login prints "login:"
        while (waiting_for_response){
          login_msg = (String)pm.read(STDOUT);
          if (login_msg != null){
            System.out.print( login_msg );
            waiting_for_response = false;
          }
        }
        // user enters user_name
        waiting_for_response = true;
        while (waiting_for_response){
          try{
	    BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
	    input_line = is.readLine();
            if (input_line != null){
              env.put("user_id", input_line);
              pm.write(STDIN, input_line);
              waiting_for_response = false;
            }
          } catch (Exception e){
            System.out.println( e.toString() );
          }
        }
        // login prints "password:"
        login_msg = null;
        waiting_for_response = true;
        while (waiting_for_response){
          login_msg = (String)pm.read(STDOUT);
          if (login_msg != null){
            System.out.print( login_msg );
            waiting_for_response = false;
          }
        }
        // user enters password
        waiting_for_response = true;
        while (waiting_for_response){
          try{
	    BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
	    input_line = is.readLine();
            if (input_line != null){
              pm.write(STDIN, input_line);
              waiting_for_response = false;
            }
          } catch (Exception e){
            System.out.println( e.toString());
          }
        }
        waiting_for_response = true;
        while (waiting_for_response){
          if (! login.getRunningStatus()){
            waiting_for_response = false;
            if ( login.getExitStatus() ){
              login_success = true;
            }
          }
        }

        return login_success;

    }

    /** main routine to allow console to run as an app
     * @param args arguments passed to app
     */
    public static void main(String[] args){
        console = new Console(args);
        boolean login_success = console.login();
        if (! login_success){
            System.out.println("Error:  not valid user/password");
            System.exit(-1);
        } else {
            System.out.println("login successful!");
            System.out.println("type logout to quit");
            console.registerMessageServer();
            Thread t = new Thread(console);
            t.start();
        }
    }

    public void registerMessageServer(){
        // register user with message box servlet
        try {
            //System.out.println("registering user with message server");
            URL register_url = new URL( (String)env.get("message_server") + "?registerUser=" + (String)env.get("user_id") );
            //System.out.println("opening connection to " + register_url.toString());
            URLConnection uc = register_url.openConnection();
            InputStream result = uc.getInputStream();
            result.close();

            //System.out.println("connected");

        } catch (Exception e){
            System.out.println( "error: " + e.toString());
        }
    }

    /** creates shell and handles input/output from user */
    public void run(){

        if (DEBUG) System.out.println("Console:  running");
        String input_line = null;
        String shell_response = null;
        boolean waiting_for_response = false;
        Pattern set_env_pattern = Pattern.compile("^_SET_ENV\\s+(\\S+)\\s+(\\S.*)$");
        boolean print_prompt = true;

        // create a shell vm and start it
        if (DEBUG) System.out.println("Console:  creating shell");
        shell = vmm.create( "Shell", null, STDIN, STDOUT, 0, env);
        Thread t = new Thread(shell);
        t.start();

       // wait until shell running state is true before continuing
        while(! shell.getRunningStatus()){ }
        // interact with shell
        while( shell.getRunningStatus() ){
            // check to see if we have any messages
            if ( this.checkMessages() ){
                System.out.println("You have new messages available!  (Type \'receive\' to view)");
            }
            if (print_prompt){
                System.out.print("l33t--> ");

                try{
                    BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
	            input_line = is.readLine();
                } catch (Exception e){
	            System.out.println( "Error trying to get input: " + e.toString() );
                }

                pm.write(STDIN, input_line);
                waiting_for_response = true;
                while (waiting_for_response){
                    shell_response = (String)pm.read(STDOUT);
                    if (shell_response != null){
                        //System.out.println("console received: " + shell_response);
                        if ( shell_response.equals("_END_OUTPUT")){
                            waiting_for_response = false;
                        } else if (shell_response.equals("_EOF")){
                            //just ignore
                        } else {
                            /* check and see if _SET_ENV was passed
                             * format for SET_ENV is '_SET_ENV key value'
                             */
                            Matcher m = set_env_pattern.matcher(shell_response);
                            if (m.find()){
                                env.put( m.group(1), m.group(2));
                                // update shell with new environment
                                shell.setEnvironment(env);
                                // if logout is true, console should exit
                                if ( ((String)env.get("logout")).equals("true")){
                                    print_prompt = false;
                                }

                            } else {
                                // just plain ol' output to be sent to console
                                System.out.println( shell_response );
                            }
                        }
                    }
                    /* while we are waiting for shell to give control back,
                     * check and see if any process needs to get input from the
                     * console */
                    if (pm.gatherInput(STDIN)){
                        try{
	                    BufferedReader it = new BufferedReader(new InputStreamReader(System.in));
	                    input_line = it.readLine();
                            pm.write(STDIN, input_line);
                            pm.inputGathered(STDIN);
                        } catch (Exception e){
	                        System.out.println( "Error trying to get input: " + e.toString() );
                        }
                    }
                }
            }
        }

        // unregister user
        try {

        URL register_url = new URL( (String)env.get("message_server") + "?clearUser=" + (String)env.get("user_id") );
        //System.out.println("opening connection to " + register_url.toString());
        URLConnection uc = register_url.openConnection();
        InputStream result = uc.getInputStream();
        result.close();

        //System.out.println("connected");

        } catch (Exception e){
            System.out.println( "error: " + e.toString());
        }
        System.out.println("Goodbye!");

    }

    public boolean checkMessages(){

        Boolean rsp = null;

        try {
            URL server_url = new URL( (String)env.get("message_server") + "?hasMessages=" + (String)env.get("user_id"));
            ObjectInputStream response = new ObjectInputStream(server_url.openStream());
            rsp = (Boolean)response.readObject();
            response.close();
        } catch (Exception e){
            //pm.write(output_handle, "error building url: " + e.toString());
        }

        if (rsp.booleanValue()){
            return true;
        } else {
            return false;
        }
    }

}


