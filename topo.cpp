// last changed 8-1-99
#ifndef HEADERFILE
#include "nw.h"
#endif
// insert your name in the function below
char* network::topo_author(){return " ";}
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


}
