// last changed 11-6-99
#include "nw.h"
//
// Copyright 1997-1999 J.M.Pullen/George Mason University
// main executive for Network Workbench project WAN 3
//
//---------------------------------------------------------------------------
//
// insert #include for student-written modules here
//
// with no #include these will be provided from workbench library
//
#include "rteupdt.cpp"
// to see Workbench solution, comment out previous line
#include "pwan3.cpp"
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

// any changes to simulation run profile in pwan3.cpp should be inserted here
//
// some good examples are:
//
//   internet = TRUE;                        // use internet notation for hosts
//   interactive = TRUE;                     // makes output interactive
//   max_DES_ticks = 500000;                 // changes simulation limit  
//   diskout = NULL;                         // turns off disk output   
//   print_at[frame_send_interface] = FALSE; // turns off/on layer trace 
//   number_of_hosts_sending_email = 1;      // simplifies traffic for debug 
//   link_bit_error_rate = 0;                // stops bit errors for debug
//   print_at[packet_drop] = FALSE;          // stops packet drop messages
//   print_at[routing_receive_interface] = FALSE; // stops routing messages
//   print_at[routing_send_interface] = FALSE;    //  "
//   routing_update_interval = 250000;       // changes routing update interval


// print out authors

   print_authors();

// initialize network

   if(read_net())
   {
     if(wan)create_topology();
     print_topology();
     if(wan)startup_routing();
     if(multicast)
     {
       create_mc_topology();
       print_mc_topology();
     }
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
