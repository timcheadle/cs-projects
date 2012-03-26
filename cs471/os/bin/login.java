/** used to verify users
 *
 * @author Dave Steinbrunn <BR> Tim Cheadle <BR> Brian Skapura
 * @version 1.0
 * @since 1.4
 */
import cs471.*;
import java.util.*;
import java.io.*;

public class login extends Command {

    private String PASSWD_FILE = "passwd.txt";
    private HashMap user_data = new HashMap();
    boolean login_success = false;

    /** allows command to be run as a thread */
    public void run(){

      super.setRunningState(true);
      String STDIN = super.getInputHandle();
      String STDOUT = super.getOutputHandle();
      PipeManager pm = super.getPipeManager();
      super.setExitStatus(false);
      String input_line = null;
      boolean waiting_for_response = true;
      String user_name = null;
      String password = null;
      String line;
      StringTokenizer stok;
      String token;
      boolean cont, get_user;

      String etc_path = (String)(super.getEnvironment()).get("etc");
      String password_file = etc_path + File.separator + PASSWD_FILE;

      // read in userids and passwords from file
      try {
        BufferedReader is = new BufferedReader(new FileReader( password_file ));
        while(( line = is.readLine()) != null ){
          cont = true;
          get_user = true;
          stok = new StringTokenizer(line);
          while (cont){
            if (stok.hasMoreTokens()){
              token = stok.nextToken();
              if (! token.equals("#")){
                if (get_user){
                  user_name = token;
                  get_user = false;
                } else {
                  user_data.put( user_name, token );
                }
              } else {
                cont = false;
              }
            } else {
              cont = false;
            }
          }
        }
        is.close();
      } catch (Exception e){
        System.out.println("Error: can't read password file: " + e.toString() );
        login_success = false;
      }

      pm.write(STDOUT, "login: ");
      while (waiting_for_response){
        input_line = (String)pm.read(STDIN);
        if (input_line != null){
          user_name = input_line;
          waiting_for_response = false;
        }
      }

      pm.write(STDOUT, "password: ");
      waiting_for_response = true;
      while (waiting_for_response){
        input_line = (String)pm.read(STDIN);
        if (input_line != null){
          password = input_line;
          waiting_for_response = false;
        }
      }

      // now see if user/password combo is in hashmap
      if ( user_data.containsKey( user_name ) ){
        if ( password.equals( (String)user_data.get(user_name)) ){
          login_success = true;
        } else {
          System.out.println("failed");
          login_success = false;
        }
      } else {
        System.out.println("failed");
        login_success = false;
      }

      super.setExitStatus( login_success );
      super.setRunningState(false);
      super.setExecuted(true);

    }



}