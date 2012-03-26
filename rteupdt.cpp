// last changed 8-28-99
#ifndef HEADERFILE
#include "nw.h"
#endif
//  insert your name in the function below
char* stack::routing_author() {return " ";}
//
// Copyright 1997-1999 J.M.Pullen/George Mason University
// routing database update LSA generation for Network Workbench
SPF_LSA_entry stack::update_LSA(link_interface_state* this_iface,byte ifacenum)
{
  SPF_LSA_entry updated_LSA;
//
// NW LSA Update Algorithm
// -----------------------
// copy ifacenum into the updated LSA entry;
// increment the routing_SN in the router host port corresponding to ifacenum
// and copy it into the updated LSA entry;
// copy the queue_depth in the router port.
//
// student code goes here
// build an updated_LSA from input parameters and router node data
//


  return updated_LSA;
}
