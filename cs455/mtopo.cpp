// last changed 11-27-99
#ifndef HEADERFILE
#include "nw.h"
#endif
//  insert your name in the function below
char* network::mc_topo_author() {return " ";}
// Copyright 1997-1999 J.M.Pullen/George Mason University
// stub for Network Workbench function to create multicast topology matrices
//****************************************************************************
// given:
//   functional routing table routes[host1][host2]
//   multicast hosts matrix mchosts[host]
//   multicast group size mcgsize
//   multicast tree root mctree_root
//
// produces the following matrices:
//
//   mcgroup[netnum] holds subnet numbers that have a multicast host,
//     in its first mcgsize elements
//   net_state_data[netnum]->mciface[interface] holds TRUE for every 
//     {router,interface} that participates in the multicast group, 
//     FALSE otherwise
//
// NOTE: Care must be taken that the multicast tree is really a tree,
//   that is it does not include any closed paths, or multicast packets
//   will loop.
//
// There is only one multicast netnum, its address is MC_NET_ADDR.
// The multicast groups can have different values of hostnum.
// This version uses only one group, with hostnum = DEFAULT_MC_GROUP.
//
 void network::create_mc_topology()
{
//
// NW Multicast Topology Algorithm
// -------------------------------
//
// set m to 1;
//
// fill up the mc_group matrix:
// do for all n from 1 to nnets,
// {
//   if net_state_data[n]->hosts_in_mc_group is greater than zero,
//   {
//     set mc_group[m] = n;
//     increment m;
//   }
// }
//
// use the unicast routing table to trace each mcnet and turn on 
// mciface bits in the path:
// do for all m from 1 to mc_grp_size,
// {
//   search down a new path from mctree_root to this m,
//   flagging interfaces in the tree:
//   set k to mc_tree_root;            
//   while k is not equal to mc_group[m] 
//   {
//     set next_k to net_state_data[k]->routes.router[mc_group[m]];
//     set mc_iface to TRUE at both ends of the link from k to next_k;       
//     set k to next_k;
//   }
// }.
//
//  student code goes here
//



}
