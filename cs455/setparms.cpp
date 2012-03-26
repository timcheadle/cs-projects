// last changed 4-27-01
#ifndef HEADERFILE
#include "nw.h"
#endif
// Copyright 1997-2001 J.M.Pullen/George Mason University
// Network Workbench Parameters Module
//******************************************************************************
//  the values in this module set below determine are
//  initial values for the Workbench run
//
//  they may be changed by inserting a statement at
//  any point in the executable code
//
char* network::parms_author(){return "Workbench generic";}
void network::set_runparms()
{
// preset default value of parameters
   set_defaults();

// set simulation to continue after setup
   keep_running = TRUE;

// set general characteristics of network
   lan = TRUE;
   wan = TRUE;
   multicast = TRUE;
   internet = TRUE;
   dynamic_routing = FALSE;
   run_list_server = FALSE;

// set initial printout conditions
   print_at[umessage_send_interface] = TRUE;
   print_at[rmessage_send_interface] = TRUE;
   print_at[segment_send_interface] = TRUE;
   print_at[packet_queue_interface] = FALSE;
   print_at[dlc_init_interface] = FALSE;
   print_at[frame_send_interface] = FALSE;
   print_at[mac_send_interface] = FALSE;
   print_at[physical_send_interface] = FALSE;
   print_at[token_send_interface] = FALSE;
   print_at[csma_send_interface] = FALSE;
   print_at[frame_receive_interface] = FALSE;
   print_at[packet_receive_interface] = FALSE;
   print_at[usegment_receive_interface] = TRUE;
   print_at[rsegment_receive_interface] = TRUE;
   print_at[message_receive_interface] = TRUE;
   print_at[bit_error_generate] = TRUE;
   print_at[bit_error_detect] = TRUE;
   print_at[packet_drop] = TRUE;
   print_at[module_authors] = TRUE;
   print_at[run_parameters] = TRUE;
   print_at[routing_table] = TRUE;
   print_at[multicast_tables] = TRUE;
   print_at[message_stats] = TRUE;
   print_at[segment_stats] = TRUE;
   print_at[packet_stats] = TRUE;
   print_at[frame_stats] = FALSE;
   print_at[csma_collision] = FALSE;
   print_at[csma_backoff] = FALSE;

// set initial value of DES run length
   max_DES_ticks = 100000;

// determine whether run will be interactive
   interactive = TRUE;

// set number of email files to be transmitted
   number_of_hosts_sending_email = 3;

// set message generation intervals al_msg_intervals[netnum][hostnum]
// configuration below is for reliable email interval 1 ms at router 1
// and a one-time multicast packet at router 1 at beginning of simulation
   host_state_data[1][1]->al_msg_intervals->ral_msg_delay = float(.001);
   host_state_data[1][1]->al_msg_intervals->mal_msg_delay = float(-1.);

// set bit error rates for links and LANs
   link_bit_error_rate = float(1E-4);
   csma_bit_error_rate = float(1E-7);
   token_bit_error_rate = float(1E-8);

// set simulation time step
   time_step = float(1E-7);

}
