// last changed 8-1-99
#ifndef HEADERFILE
#include "nw.h"
#endif
// insert your name in the function below
char* network::topo_author(){return "Tim Cheadle";}


// Copyright 1997-1999 J.M.Pullen/George Mason University//
// stub for function to create topology matrices for Network Workbench
//*****************************************************************************
void network::create_topology ()
{
// Algorithm to produce exit_interfaces and nports from links
// ----------------------------------------------------------
// do for each row in links,
// {
//   do for each column in the row of links, generating a row of 
//   exit_interfaces from this rule,
//   {
//     if an element of links is zero, 
//       the corresponding element of exit_interfaces is -1;
//     do for non-zero elements in links,
//    { 
//       set the corresponding element of exit_interfaces to an integer  
//       that starts at 1 for the first instance and increases by 1 with 
//       each instance;
//     }
//   }
//   set the value of net_state_data[ ]->nports, in the position corresponding 
//   to the row, to the number of interfaces on which the node has connections;
// }. 
//
	int i, j, k;
	int iface_counter = 1;  // counter to assign interface numbers local to each router
	int total_interfaces = 0;  // counter for the total number of ports in the network

	for (i=1; i <= nnets; i++) {
		iface_counter = 1;
		for (j=1; j <= nnets; j++) {
			if (!links[i][j]) {
				exit_interfaces[i][j] = -1; // if interface doesn't exist, assign -1
			} else {
				exit_interfaces[i][j] = iface_counter++;
				net_state_data[i]->nports++;
				total_interfaces++;
			}
		}
	}

// Algorithm to produce interface from exit_interfaces
// ---------------------------------------------------
// do for each row in exit_interfaces, starting with row 1, column 1,
// {
//   do for each column in the row,
//     whenever you find an element greater than zero start a new position in 
//     interfaces,
//     { 
//       set interfaces[position].net to the row number (this is the 
//       router);
//       set interfaces[position].host to 1;
//       set interfaces[position].ifacenum to the value of the 
//       element of exit_interfaces that is greater than zero
//       (do not set the value of interfaces[position].other_end yet);
//     }
//   do for each column in interfaces to set the value of other_end,
//   {
//     get the net (router) number and the ifacenum;
//     look in exit_interfaces row number for net to find the column 
//     that holds ifacenum;
//     this [row][column] in exit_interfaces represents one end of a 
//     link;
//     the other end of this link is the element with indexes 
//     reversed: [column][row]; 
//     the value of net at he other end will be the [column] value and 
//     ts ifacenum will be in position[column][row];
//     now that you know the net and ifacenum for other_end, do for each 
//     column in interfaces,
//     {
//       test to find the position with these values,
//         put the number of that position in the other_end for the 
//         position of interfaces you are filling in;
//     }
//   }
// }.
//
// NOTES:
// 1. with internetting, netnum=0 is used for broadcast therefore matrix
// positions are in range 1:nnets, not 0:(nnets-1) unlike normal C programs
//
// 2. interface 0 is always LAN, therefore router serial links are start at 1
//
	int index = 0; // assign global interface numbers to each interface
	
	for (i=1; i <= nnets; i++) {
		for (j=1; j <= nnets; j++) {
			if (exit_interfaces[i][j] > 0) {
				interfaces[index].net = i;
				interfaces[index].host = 1;
				interfaces[index].ifacenum = exit_interfaces[i][j];
				index++;
			}
		}
	}
	
	for (i=0; i < index; i++) { // for each column in interfaces
		int router = interfaces[i].net;
		int iface = interfaces[i].ifacenum;
		for (j = 1; j <= nnets; j++) { // for each column in exit_interfaces
			if (exit_interfaces[router][j] == iface) {
				int end_router = j;
				int end_iface = exit_interfaces[j][router];
				for (k = 0; k < index; k++) {
					if (interfaces[k].net == end_router && interfaces[k].ifacenum == end_iface) {
						interfaces[i].other_end = k;
					}
				}
			}
		}
	}
			
}
