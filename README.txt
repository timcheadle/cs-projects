Tim Cheadle
Brian Skapura
Dave Steinbrunn

CS 471 Java Operating System Simulator

requires:  java version 1.4.0  ( uses regular expressions )

installation instructions:

1.  cp the bin directory to be a subdirectory of your home directory, also do this with the etc directory

2.  Edit the file etc/passwd.txt to include a user account for yourself

3.  make sure that the cs471 directory is part of you classpath

4.  type 'java Console' to run the simulator


Note:   The enhancment for this simulator is an application set that allows users who are logged in to the os to send each other messages.  The main component of this is a J2EE servlet that acts as a message mailbox.  In order to use this feature you must be able to connect to a servlet container that is running this servlet.  Currently this servlet is being run at < tim's tomcat server addr here >


----
Installing tomcat

1.  make sure you have latest java installed

http://java.sun.com/j2se/1.4/download.html

2.   get the tomcat binary from apache:

http://jakarta.apache.org/builds/jakarta-tomcat-4.0/release/v4.0.3/

3.  install tomcat

4.  put the file 'MessageServlet.war'  in the {TOMCAT_HOME}/webapps directory

5.  start tomcat ( {TOMCAT_HOME}/bin/startup.sh )

6.  the servlet will be automatically deployed.   You should be able to connect to it by going to the following url in a browser:

http://server:8080/MessageServlet/message

This will bring up a window showing the current status, who's logged in and what messages are stored.





