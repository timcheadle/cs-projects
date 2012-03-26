// last changed 8-22-99
#ifndef HEADERFILE
#include "nw.h"
#endif
// insert your name in the function below
char* stack::backoff_author(){return " ";}
// Copyright 1997-1999 J.M.Pullen/George Mason University
//**************************************************************************
// Stub for student-written binary exponential backoff function, for CSMA/CD 
int stack::backoff(mac_interface_state* mac_iface)
{
// mac_iface is a pointer to the interface_state values associated with
// this interface, for example mac_iface->max_backoff_slots
// (see typedef for mac_interface_state in nw.h)
//
// Binary Exponential Backoff Algorithm
// ------------------------------------
// This algorithm assumes that the csma_send function which invokes backoff 
// will create the needed initial local interface state before it starts to 
// send a frame:
// { 
//   set max_backoff_slots to 1;
//   set backoff_count to 0;
// }
// It will then invoke backoff() after each successive collision, until it 
// is able to send the frame without a collision.
//
// Algorithm for function backoff()
// --------------------------------
// if max_backoff_slots is less than 1024,
//   double the value of max_backoff_slots;
// if backoff_count is greater than 16,
//   return value -1 indicating failure;
// generate a random_number between 0 and 1, using the random number seed 
// associated with this interface;
// calculate random_backoff_slots = random_number * max_backoff_slots;
// return backoff_ticks = random_backoff_slots * slot_ticks;
//
// student backoff code goes here
// student code must calculate new backoff value in ticks which is returned
// in place of the 0 below

  return 0;
}

