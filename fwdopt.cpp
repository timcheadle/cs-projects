// last changed 9-13-99
#ifndef HEADERFILE
#include "nw.h"
#endif
// insert your name in the function below
char* stack::fwdopt_author(){return " ";}
//  Copyright 1997-1999 J.M.Pullen/George Mason University
//  stubs for student-written network layer modules for Network Workbench
//*****************************************************************************
//  stub for function to compute forwarding interface given a correct routing table
//
byte network::forward_iface(subnet_state* this_net,byte destnet)
{
// this_net-> subnet data; destnet is number of destination subnet
//
// Packet Forwarding Algorithm for NW
// ----------------------------------
//
// Look in the this_net->routing table for the next_hop network number;
// Look in the exit_interfaces matrix to find the interface that goes 
// from this network router to that network;
// Return that value.
//

  return 0; // student must replace 0 with code
}

//
//  stub for function to compute one row of the optimal routing table for routes[]
//
//
next_hops stack::optimize_routes(byte source)
{
// source is the subnet number of router (at packet source) that seeks
// to find an optimal sent of routes for its packets
//
  next_hops forward_routers; // create next_hop vector here to return
//
// Dijkstra's route optimization algorithm for NW
// ----------------------------------------------
//
// required storage:
//   array R that will represent Dijkstra’s set R;
//   array C of costs associated with route from source to router n;
//
// initialization:
// initialize R[0] to source;
// do for all n from 1 to nnets,
// {
//   initialize C[n] to cost, as seen from source of the link, 
//   from source to n;
//   initialize forward_routers.router[n] to n;
// }
// do for all h from 1 to nnets,
// {
//   add to R the router with the least-cost path to source:
//   set cost_of_h to a very large number;
//   do for all n from 1 to nnets,
//   {   
//     do for all j from 0 to h-1,
//       if any R[j] contains n, skip this n;
//     if C[n] is less than cost_of_h,
//     {
//       set cost_of_h to C[n]
//       set R[h] to n;
//     }
//   }
// 
//   update the least-cost paths:
//   do for all n from 1 to nnets, 
//   { 
//     find cost_of_h_to_n, as seen from source, of link from R[h] to n;
//     if C[R[h]] plus cost_of_h_to_n is less than C[n],
//     {
//       set C[n] to C[R[h]] plus cost_of_h_to_n;
//       set forward_routers.router[n] to forward_routers.router[R[h]];
//     }
//   }
//
//   stopping rule- quit when all routers are in R:
//   do for all n from 1 to nnets,
//     do for all j for 0 to h, 
//       look for an n that is not in some R[j];
//   if no such n is found, break out of this (do for h) loop;
// }.
//


  return forward_routers;
}

