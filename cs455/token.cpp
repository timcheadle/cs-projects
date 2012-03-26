// last changed 9-13-99
#ifndef HEADERFILE
#include "nw.h"
#endif
//  insert your name in the function below
char* stack::token_author() {return " ";}
// Copyright 1997-1999 J.M.Pullen/George Mason University
// stub token LAN module for Network Workbench
//*****************************************************************************
bit stack::token_receive(bit_frame* received_token_frame,
                         mac_interface_state* mac_iface)
{
//
// Algorithm for NW Token-Passing Protocol
// ---------------------------------------
// The timeout necessary to implement the token LAN is implemented as a special
// code that is sent to token_receive when a timeout occurs.
//
// The token interface hardware automatically forwards all frames except those
// that contain a token or are addressed from this host.
//
// When a frame is received, it is a timeout, token, or data;
// if the frame contains a timeout code,
// {
//   if the frame's timeout_SN matches mac_iface->timeout_SN,
//   {
//     send the frame in mac_iface again;
//     start timeout again;
//   }
//   return;
// }
// if the frame contains a token,
//   look at mac_iface->current_frame, if there is a frame waiting in 
//   current_frame,
//   {
//     send it;
//     start a timer to call token_receive back in case the frame is lost;
//     return;
//   } 
//   else (i.e. no frame waiting),
//   {
//     forward the token to next host;
//     return;
//   }
// unstuff the received frame so its contents can be inspected;
// at this point the frame must contain data,
// if data is from this host (indicates a completed transmission),
// {
//   increment mac_iface->timeout_SN;
//   pass the token forward;
//   return;
// }
// else (i.e. data is not from this host),
// {
//   the data must be addressed to this host (including multicast and 
//   broadcast) or the interface would not have delivered it,
//   pass the data to dlc_receive;
//   return;
// }.
//
//
// The following functions are intended to be useful in implementing the above 
// algorithm.  All are in mac.cpp and class stack except the first which is
// in bitwork.cpp:
//
// bit forward_token(mac_interface_state*);              inserts token in token LAN
// bit start_token_timeout(mac_interface_state*);        starts timeout event for token LAN
// bit token_present(bit_frame*);                        test for token frame
// bit mac_buffer_contains_frame(mac_interface_state*);  test for MAC frame availability
// bit timeout_code_present(bit_frame*);                 test for timeout code in frame
// byte extract_timeout_SN(bit_frame*,byte);             value of timeout_SN in token LAN
// byte fromport(bit_frame);                             extracts from_port from bit_frame
// bit token_send(bit_frame*,mac_interface_state*,byte); sends a data frame over token LAN
//
// in addition, the following function provided in interlyr.cpp is needed:
//
// void stack::mac_to_dl(bit_frame*,mac_interface_state*)invokes dl_receive with a received
//                                                       bit_frame
//
// student code (about 20 lines) goes here

// here is an example of sending a frame
    token_send(received_token_frame,mac_iface);

// here is how to unstuff the received frame
    bit_frame unstuffed_token_frame;
    unstuff(&unstuffed_token_frame,received_token_frame);

    return SUCCESS;
}
