// last changed 11-16-00
#include "nw.h"
//
// Copyright 1997-2000 J.M.Pullen/George Mason University
// main executive for Network Workbench project TRN1
//
//---------------------------------------------------------------------------
//
// insert #include for student-written modules here
//
// with no #include these will be provided from workbench library
//
#include "tlsend.cpp"
// to see Workbench solution, comment out previous line
#include "ptrn1.cpp"
#ifdef BORLAND
#include <vcl\condefs.h>
USELIB("..\bcb\libbcbud.lib");
//---------------------------------------------------------------------------
#endif
//---------------------------------------------------------------------------
stack sim;

int stack::simulation()
{
// set printout conditions

   set_runparms();

// any changes to simulation run profile in ptran1.cpp should be inserted here
//
// some good examples are:
//
//   INTERNET = TRUE;                        // use internet notation for hosts
//   interactive = TRUE;                     // makes output interactive
//   max_DES_ticks = 500000;                 // changes simulation limit
//   diskout = NULL;                         // turns off disk output
//   print_at[frame_send_interface] = FALSE; // turns off/on layer trace
//   number_of_hosts_sending_email = 1;      // simplifies traffic for debug
//   link_bit_error_rate = 0;                // stops bit errors for debug
//


// print out authors

   print_authors();

// initialize network

   if(read_net())
   {
     if(wan)create_topology();
     print_topology();
     if(wan)startup_routing();
     startup_simulation();
   }    

// run simulation

   while(next_event());
   statistics();
   return SUCCESS;
}

int main()
{
return sim.simulation();
}
