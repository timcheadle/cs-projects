// last changed 8-14-01
#ifndef HEADERFILE
#include "nw.h"
#endif
// insert your name in the function below
char* stack::tl_author(){return " ";}
//   Copyright 1997-2001 J.M.Pullen/George Mason University
//   stub for reliable Transport Layer send logic shell for Network Workbench.
//
//   this function contains the primary send logic, while connection and
//   buffering logic are in the parent module tl.cpp
//
segment stack::send_rtl_segments(host_tl_buffer* saved_segments,segment* reply_seg,
                    host_state* this_host,byte destnet,byte desthost,byte protocol)
// parameters:
//   saved_segments:  segments that have been transmitted previously
//   reply_seg:       last segment reply from other end
//   this_host:       complete state of this host (used to get tl_state)
//   destnet:         netnum at other end of connection
//   desthost:        hostnum at other end of connection
//   protocol:        code showing this is reliable transport protocol
{
  transport_state* tl_state = this_host->host_tl_state;
  segment send_segment;
  message sending_message = tl_state->outgoing_message;
  message null_message = {0,this_host->netnum,this_host->hostnum,destnet,desthost};
  segment null_segment = {0};
//
// algorithm for sending TL segments (function send_rtl_segments)
// if reply_seg contains an ackno (ACK==1, SYN==0, FIN==0) indicating that 
// a previously sent segment has not been received (ackno<last_char_sent), 
//   do for all segments in the buffer (the number is TL_MAX_BUFFER_SEGS),
//     if that segment has had time to reach its destination 
//     (sim_time minus segment sent_time is greater than net_rtt_timeout),
//       send that segment again from the tl_buffer;
// calculate window_end as the smaller of (send_window_start+send_window_size-1) 
// and (sending_message.size-1);
// while last_char_sent is less than window_end,
// {
//   make another segment from the message;
//   send the segment;
// } 
// for each segment in tl_buffer,
//   if segment has timed out waiting for ACK (sim_time minus segment 
//   sent_time is greater than net_rtt_timeout),
//     retransmit the segment in tl_buffer;
//
// students should modify the code immediately following this to 
// implement the above algorithm
//
// this example shows how to send a segment; please look for details of send_seg 
// usage in file tl.cpp.:
   send_segment = make_segment(&sending_message,reply_seg->ackno);
   tl_state->last_char_sent = // this example shows how to send a segment
      send_seg(send_segment,this_host,tl_state->last_char_sent,-1);

//
// if send_window_start is greater than or equal to size of message being sent 
// (all data has been sent and ACKed), 
// {
//   send FIN;
//   set tl_send_state to close_wait;
// }.
//
// students should modify the code immediately following this to 
// implement the above algorithm
//
// this sample shows how to send control segments such as FIN:
  rtl_to_nl(&send_segment,this_host,protocol);   

  return send_segment;
}

