Shape Browser

Tim Cheadle
May 4, 2003
CS 332

This program reads in a file containing information about shapes on each line,
and then translates it to a new format while displaying the shape on the
screen.  The program makes extensive use of design patterns, most notably the
Template pattern and Abstract Factory pattern.

The Template pattern is used for Shapes.  Each shape has to have the ability
to print out a string description of itself, but how this description is to
be implemented is unknown.  The template pattern allows us to create hooks
so that subclasses (specific shapes) can implement the descriptions on their
own, yet clients can still call toString() and know that the hooks will be
implemented.

The Abstract Factory pattern is used when actually creating the shapes from
the line of input from the file.  I didn't want to tie this application
solely to the file format provided, so I implemented an abtract factory to
create the interface through which to make shapes, then I created a factory
specific to the given file format in order to implement their creation.

I also used the Singleton pattern in the creation of the Config class.  This
allows clients to access the same configuration object no matter where the
scope, and this creates uniform configuration across all aspects of the code.

The Swing functionality is noticeably missing from the code; I simply did not
have enough time to design this and learn about Swing at the same time.
What's ironic is that I already had an interface designed for the shape
browser; I simply had no idea how to write it.
