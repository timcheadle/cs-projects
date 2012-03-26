// last changed 11-6-99
#include "nw.h"
//
// Copyright 1997-1999 J.M.Pullen/George Mason University
// main Workbench executive
//
//---------------------------------------------------------------------------
//
// insert #include for student-written Workbench modules here
// any module not provided by student will be inserted from
// object library
//
//#include "backoff.cpp"   // MAC sublayer CSMA/CD backoff
//#include "crc.cpp"       // computes CRC-FCS
//#include "dllogic.cpp"   // data link layer (default is unreliable)
//#include "firewal.cpp"   // firewall packet filter
//#include "fwdopt.cpp"    // network layer forward and route optimize
//#include "lstserv.cpp"   // email list server application
//#include "mtopo.cpp"     // builds multicast topology matrix
//#include "rteupdt.cpp"   // WAN routing protocol
//#include "setparms.cpp"  // sets dynamic run parameters
//#include "stuff.cpp"     // stuffs/unstuffs bits
//#include "tlsend.cpp"    // transport layer (default is reliable)
//#include "token.cpp"     // MAC sublayer token LAN receive
//#include "topo.cpp"      // builds topology matrices
//---------------------------------------------------------------------------
#ifdef BORLAND
#include <vcl\condefs.h>
// to use Workbench reliable DLC-
// (Sun: use rdllogic.o as second parameter of cwkb)
// (Borland: change next line to libbcbrd.lib)
USELIB("..\bcb\libbcbud.lib");
//---------------------------------------------------------------------------
#endif
//---------------------------------------------------------------------------


stack sim;

int stack::simulation()
{
// set simulation conditions
   set_runparms();

// any changes to simulation run profile in setparms.cpp should be inserted here
//
// some good examples are:
//
//   interactive = TRUE;                    // makes output interactive
//   max_DES_ticks = 500000;                // changes simulation limit
//   diskout = NULL;                        // turns off disk output
//   print_at[frame_send_interface] = FALSE;// turns off/on layer trace
//   number_of_nodes_sending_email = 1;     // simplifies traffic for debug
//   bit_error_rate = 0;                    // stops bit errors for debug
//

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

//---------------------------------------------------------------------------
