// last changed 5-5-01
#ifndef HEADERFILE
#include "nw.h"
#endif
// insert your name in the function below
char* stack::dl_author(){return " ";}
//   Copyright 1997-2001 J.M.Pullen/George Mason University
//   Data Link Control Layer logic for Network Workbench.
//
//   This version defines stub functions providing logic for an HDLC-like DLC
//   that uses go-back-n (n=DL_WINDOW_FRAMES).
//
//   These functions are all very short, one to four lines of code.
//   They provide the logic for various steps in the DLC ARQ.
//*****************************************************************************
//
//  function to test whether range between two numbers is smaller than
//  the window, given DLC counter range (DL_WINDOW_MAX)
//
//  Nmin is logically lower end of range, the lowest number not ACKed
//  Nmax is logically higher end of range, the next number to be used
//  window_size is the number of frames in the window
//
//  NOTE: This function differs from LTwindow in the book "Understanding
//  Internet Protocols" (Pullen, Wiley, 2000) by addition of the third
//  argument to the function. The third argument is needed to allow for
//  the possibility that the window size may change.
//
//
bit stack::LTwindow(byte Nmin,byte Nmax,byte window_size)
{
  int testmax=Nmax;

  if(Nmin > Nmax) testmax = testmax+DL_WINDOW_MAX;

  return(testmax-Nmin < window_size);
}

//
//  function to update current_window_size in the link_interface_state
//  based on its current values of SNMax and SNmin
//
//
byte stack::current_window_size(link_interface_state* link_iface)
{
  int window_size = link_iface->SNmax - link_iface->SNmin;
  if(window_size < 0) window_size += DL_WINDOW_MAX;
  return window_size;
}

//
//  function to test whether range between two numbers is within
//  the window, given DLC counter range (DL_WINDOW_MAX)
//
//  Nmin is logically lower end of range, the lowest number not ACKed
//  Nmax is logically higher end of range, the next number to be used
//  window_size is the number of frames in the window
//
//  NOTE: This function differs from INwindow in the book "Understanding
//  Internet Protocols" (Pullen, Wiley, 2000) by addition of the third
//  argument to the function. The third argument is needed to allow for
//  the possibility that the window size may change.
//
//
bit stack::INwindow(byte Nmin, byte Nmax,byte window_size)
{
  return FALSE; // student must replace FALSE with appropriate code
}


//
//  function to determine whether the assigned window size has become 
//  full, that is, the number of frames has reached DL_WINDOW_FRAMES 
//
//
bit stack::window_full(link_interface_state* link_iface)
{
  return TRUE; // student must replace TRUE with appropriate code
}

//
//  function to confirm that frames have been sent for which ACKs
//  have not been received
//
//
bit stack::frames_remain_unacked(link_interface_state* link_iface)
{
  return TRUE;  // student must replace TRUE with appropriate code
}

//
//  function to increment SNmax for an interface within the modular range
//  which is of size DL_WINDOW_MAX
//
void stack::increment_SNmax(link_interface_state* link_iface)
{
// student must provide code
   return;
}

//
//  function to update the SNmin of an interface, based on received RN
//
//
void stack::update_SNmin(link_interface_state* link_iface,byte received_RN)
{
// student must provide code
  return;
}

//
//  function to update the RN of an interface, based on received SN
//
//
void stack::update_RN(link_interface_state* link_iface,byte received_SN)
{
// student must provide code
  return;
}


//
//  function to decide whether to accept frame, based on its SN
//  and the RN associated with receiving interface
//
//
bit stack::accept_frame(byte received_SN,byte interface_RN)
{
// student must replace FALSE with appropriate code
  return FALSE;
}
