// last changed 9-13-99
#ifndef HEADERFILE
#include "nw.h"
#endif
// insert your name in the function below
char* stack::fwdopt_author(){return "Tim Cheadle";}
//  Copyright 1997-1999 J.M.Pullen/George Mason University
//*****************************************************************************
//  function to compute forwarding interface given a correct routing table
//

byte network::forward_iface(subnet_state* this_net,byte destnet)
{
// this_net-> subnet data; destnet is number of destination subnet
//
// Packet Forwarding Algorithm for NW
// ----------------------------------
// Look in the this_net->routing table for the next_hop network number;
// Look in the exit_interfaces matrix to find the interface that goes 
// from this network router to that network;
// Return that value.
	byte curr_hop = this_net->netnum;
	byte next_hop = this_net->routes->router[destnet];
	return exit_interfaces[curr_hop][next_hop];
}

//
//  function to compute one row of the optimal routing table for routes[]
//
//
next_hops stack::optimize_routes(byte source)
{
	next_hops nhops;
	int i;
	float dist[nnets+1];
	bool marked[nnets+1];
	bool all_marked = false;

	for (i=1; i <= MAX_NETS; i++) {
		// Initialize all next hops to be -1
		nhops.router[i] = 0;
	}

	for (i=1; i <= nnets; i++) {
		// Initialize all distances to a huge number unless the hop is the source
		dist[i] = (i == source) ? 0.0 : HUGEFLOAT;
		// Initialize all hops as unmarked
		marked[i] = false;
	}

	// Loop through until every node is marked
	while (!all_marked) {
		// Find the unmarked node with the shortest distance
		int u = 1;
		for (int s = 2; s <= nnets; s++) {
			if (marked[u] || !marked[s] && (dist[s] < dist[u])) {
				u = s;
			}
		}

		// Mark u so we don't revisit it
		marked[u] = true;

		// Now go to all of it's neighbors
		for(int v = 1; v <= nnets; v++) {
			// links[v] will be > 0 if v is a neighbor to u
			if (links[u] > 0) {
				// If v's current distance is greater than the cost from u to v
				// then set the new distance for v equal to that cost
				if (dist[v] > cost(u, u, v)) {
					dist[v] = cost(u, u, v);
					
					// Now make the next hop to get to v equal to v if u is the source,
					// or equal to nhops[u] if not
					if (u == source) {
						nhops.router[v] = v;
					} else {
						nhops.router[v] = nhops.router[u];
					}
				}
			}
		}

		// Now see if every node has been marked
		all_marked = true;
		for (i=1; i <= nnets; i++) {
			if (marked[i] == false) {
				all_marked = false;
			}
		}
	}

	return nhops;
}

