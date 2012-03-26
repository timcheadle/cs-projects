// last changed 4-27-01
#ifndef HEADERFILE
#include "nw.h"
#endif
//   Copyright 1997-2001 J.M.Pullen/George Mason University
//**********************************************************************************
//   function to generate inter-error interval used to introduce errors into data
//
long int simulation_control::ticks_to_next_lan_error(mac_interface_state* mac_iface)
{
  float rate=mac_iface->errrate;
// set mean error rate for this port
  float avg_errs_per_sec = float(CSMA_BIT_RATE)*rate;
// generate pseudo-random number
  float randnum=fwrand(&mac_iface->randseed);
// reshape distribution from uniform to exponential
  return (float(log(randnum))/(-avg_errs_per_sec))/time_step;
}
