// last changed 5-5-01
#ifndef HEADERFILE
#include "nw.h"
#endif
// Copyright 1997-1999 J.M.Pullen/George Mason University
// Interface module for use with Network Workbench.
// Design by J.M.Pullen combines tracing interfaces between any desired layers
// (including possible user-written output functions) with efficient
// discrete event simulation.
//
//*************************************************************************
//
//  main interlayer (interface) function family
//
// These functions collect output statistics, with option of printing
// data as it is received for debug purposes.  Then they pass the
// interface data to the discrete event simulation routine for
// time-deferred delivery to the called layer. To start debug print
// at any layer, execute a statement:
//   print_at[interlayer_type] = TRUE; // see nw.h for interlayer_type
// to stop debug print change print_at[ ] to FALSE.
//
// All arguments are passed through intact to schedule_event()
//
// In order to use the generic DES, all argument structures must be
// converted to type data_units (union of all types) before being
// passed to schedule_event, and the state pointer for the stack
// layer must be one of the three types supported by the DES functions:
// host_state*, link_interface_state*, or mac_interface_state*.
//

// delayed delivery to application send - no further delay added or statistics kept
void network::delay_to_al_send(Sim_Timer ticks,host_state* this_host,filein* args)
{
  data_units du_args;
  du_args.file_args = *args;
  schedule_event(ticks,file_send_interface,du_args,this_host,0);
}

// best-effort application layer send
void network::al_to_utl(message* args,host_state* this_host)
{
  data_units du_args;
  du_args.message_args = *args;
// collect al statistics
  if(args->message_type == email)
    ++netstats[this_host->netnum][this_host->hostnum].email_messages_sent;
  if(args->message_type == stream)
    ++netstats[this_host->netnum][this_host->hostnum].stream_messages_sent;
  if(print_at[umessage_send_interface]==1)
  {
    show_host(this_host,
      "application layer sends to best-effort transport layer message:");
    print_email(args);
    interact();
    if(print_at[umessage_send_interface]>1 || print_at[umessage_send_interface]<0)
      message_send_user_out(args,this_host);
  }
//  schedule the result of this action to happen
  schedule_event(AL_PROCESSING_TICKS,umessage_send_interface,du_args,this_host,0);
}

// reliable application layer send
void network::al_to_rtl(message* args,host_state* this_host)
{
  data_units du_args;
  du_args.message_args = *args;
// collect al statistics
  if(args->message_type == email)
    ++netstats[this_host->netnum][this_host->hostnum].email_messages_sent;
  if(args->message_type == stream)
    ++netstats[this_host->netnum][this_host->hostnum].stream_messages_sent;
  if(print_at[rmessage_send_interface]==1)
  {
    show_host(this_host,
      "application layer sends to reliable transport layer message:");
    print_email(args);
    interact();
    if(print_at[rmessage_send_interface]>1 || print_at[rmessage_send_interface]<0)
      message_send_user_out(args,this_host);
  }
//  schedule the result of this action to happen
    schedule_event(AL_PROCESSING_TICKS,rmessage_send_interface,du_args,this_host,0);
}


// delayed delivery to transport send - no layer delay added or statistics kept
void network::delay_to_tl_send(Sim_Timer ticks,message* args,host_state* this_host)
{
  data_units du_args;
  du_args.message_args = *args;
  schedule_event(ticks,rmessage_send_interface,du_args,this_host,0);
}

// invoke routing send - no layer delay added or statistics kept
void network::invoke_routing_send(Sim_Timer ticks,message* args,host_state* this_host)
{
  data_units du_args;
  du_args.message_args = *args;
  schedule_event(ticks,routing_send_interface,du_args,this_host,0);
}

// reliable transport layer send
void network::rtl_to_nl(segment* args,host_state* this_host,byte protocol)
{
  data_units du_args;
  du_args.segment_args = *args;
// collect tl statistics
  ++netstats[this_host->netnum][this_host->hostnum].r_segments_sent;
  if(print_at[segment_send_interface]==1)
  {
    show_host(this_host,
      "reliable transport layer sends to network layer segment:");
    print_chars((char_data*)args);
    interact();
    if(print_at[segment_send_interface]>1 || print_at[segment_send_interface]<0)
      segment_send_user_out(args,this_host);
  }
//  schedule the result of this action to happen
  schedule_event(TL_PROCESSING_TICKS,segment_send_interface,du_args,this_host,
                 protocol);
}

// best-effort transport layer send
void network::utl_to_nl(segment* args,host_state* this_host,byte protocol)
{
  data_units du_args;
  du_args.segment_args = *args;
// collect tl statistics
  ++netstats[this_host->netnum][this_host->hostnum].u_segments_sent;
  if(print_at[segment_send_interface]==1)
  {
    show_host(this_host,
      "best-effort transport layer sends to network layer segment:");
    print_chars((char_data*)args);
    interact();
    if(print_at[segment_send_interface]>1 || print_at[segment_send_interface]<0)
      segment_send_user_out(args,this_host);
  }
//  schedule the result of this action to happen
  schedule_event(TL_PROCESSING_TICKS,segment_send_interface,du_args,this_host,
    protocol);
}

// routing send
void network::routing_to_nl(SPF_segment* args,host_state* this_host,byte protocol)
{
  data_units du_args;
  du_args.SPF_segment_args = *args;
// collect routing statistics
  ++netstats[this_host->netnum][1].routing_packets_sent;
  if(print_at[routing_send_interface]==1)
  {
    show_host(this_host,
      "routing protocol sends to network layer segment:");
    print_chars((char_data*)args);
    interact();
    if(print_at[routing_send_interface]>1 || print_at[routing_send_interface]<0)
      routing_send_user_out(args,this_host);
  }
//  schedule the result of this action to happen
  schedule_event(ROUTING_PROCESSING_TICKS,segment_send_interface,du_args,
	this_host,protocol);
}

// network layer send
void network::nl_to_dl(packet* args,host_state* this_host,byte ifacenum)
{
  data_units du_args;
  du_args.packet_args = *args;
  link_interface_state* this_iface = this_host->linkstate[ifacenum];
// collect nl statistics
  if((du_args.packet_args.source_net == this_host->netnum)
     &&(du_args.packet_args.source_host == this_host->hostnum))
  {
    if(du_args.packet_args.dest_net < MC_NET_ADDR)
      ++netstats[this_host->netnum][this_host->hostnum].uc_packets_sent;
    if(du_args.packet_args.dest_net == MC_NET_ADDR)
      ++netstats[this_host->netnum][this_host->hostnum].mc_packets_sent;
    if(du_args.packet_args.dest_net == BC_NET_ADDR)
      ++netstats[this_host->netnum][this_host->hostnum].bc_packets_sent;
  }
  if(((du_args.packet_args.source_net != this_host->netnum)
       ||(du_args.packet_args.source_host != this_host->hostnum))
     &&(du_args.packet_args.dest_net < MC_NET_ADDR))
     ++netstats[this_host->netnum][this_host->hostnum].uc_packets_forwarded;
  if(print_at[packet_send_interface]==1 || print_at[packet_queue_interface]==1)
  {
    if(du_args.packet_args.dest_net < MC_NET_ADDR)
    show_host_iface(this_iface,"network layer sends to interface",
      "packet queue for DLC");   
    else if(du_args.packet_args.dest_net == MC_NET_ADDR)
      show_host_iface(this_iface,"network layer sends to interface",
        "packet queue for DLC multicast packet");  
    else
      show_host_iface(this_iface,"network layer sends to interface",
        "packet queue for DLC broadcast packet");  
    print_chars((char_data*)args);
    interact();
    if(print_at[packet_send_interface]>1 || print_at[packet_send_interface]<0
       || print_at[packet_queue_interface]>1 || print_at[packet_queue_interface]<0)
    packet_send_user_out(args,this_iface);
  }
//  schedule the result of this action to happen
  schedule_event(NL_PROCESSING_TICKS,packet_queue_interface,du_args,this_iface,0);
}

//
// this function is an artifact of simulation created to take the place of
// the hardware interrupt signal that would normally signal the sending link
// interface when transmission of a frame is complete - it results in
// delayed delivery to datalink send - no layer delay added or statistics kept
//
// overloaded in two forms- link can be identified by 
// link_interface_state* (use by link DLC)
// or portnum (used by mac DLC)
//
void network::wakeup_dl_send(Sim_Timer ticks,byte fromportnum,byte protocol)
{
  link_interface_state* this_iface = 
    host_state_data[interfaces[fromportnum].net][interfaces[fromportnum].host]->
      linkstate[interfaces[fromportnum].ifacenum];
  wakeup_dl_send(ticks,this_iface,protocol);
}
void network::wakeup_dl_send(Sim_Timer ticks,link_interface_state* this_iface,
                             byte protocol)
{
  packet dummy = sim.dummy_packet(null_pkt,this_iface->netnum,
    this_iface->hostnum,this_iface->ifacenum);
  dummy.pkt_protocol = protocol;
  data_units du_args;
  du_args.packet_args = dummy;
  this_iface->link_active = TRUE;
  schedule_event(ticks,packet_send_interface,du_args,this_iface,0);
}

// datalink layer initialization
void network::nl_to_dl_init(packet* args,link_interface_state* this_iface)
{
  data_units du_args;
  du_args.packet_args = *args;
  if(print_at[dlc_init_interface]==1)
  {
    show_host_iface(this_iface,
      "network layer requests link initialization"," ");
    interact();
    if(print_at[dlc_init_interface]>1 || print_at[dlc_init_interface]<0)
      dlc_init_user_out(args,this_iface);
    }
//  schedule the result of this action to happen
    schedule_event(0,dlc_init_interface,du_args,this_iface,0);
}

// mac sublayer send
void network::dl_to_mac(bit_frame* args,link_interface_state* this_iface,byte toportnum)
{
  data_units du_args;
  du_args.bit_args = *args;
  mac_interface_state* mac_iface = this_iface->mac;
  ++netstats[this_iface->netnum][this_iface->hostnum].frames_sent[0];
  if(print_at[mac_send_interface]==1)
  {
    show_host(host_state_data[this_iface->netnum][this_iface->hostnum],
      "LAN interface DLC layer sends to MAC sublayer frame:");
    print_bits((bit_frame*)args);
    interact();
    if(print_at[mac_send_interface]>1 || print_at[mac_send_interface]<0)
      mac_send_user_out(args,mac_iface,toportnum);
  }
//  schedule the result of this action to happen
  schedule_event(DL_PROCESSING_TICKS,mac_send_interface,du_args,mac_iface,toportnum);
}

// delayed delivery to mac send - no layer delay added or statistics kept
void network::delay_to_mac_send(Sim_Timer ticks,bit_frame* args,
                              mac_interface_state* mac_iface,byte toportnum)
{
  data_units du_args;
  du_args.bit_args = *args;
output("delay_mac sim time:",sixdigs(sim_time)," delay:",fourdigs(ticks),"\n");
  schedule_event(ticks,mac_send_interface,du_args,mac_iface,toportnum);
output("delay_mac sim time:",sixdigs(sim_time),"\n");
}

// event call to clear_csma_send - needed for synchronization
void network::clear_sender(byte fromportnum)
{
  data_units du_args;
  du_args.bit_args.frame_bits[0] = 0;
  schedule_event(0,csma_clear_interface,du_args,mac_state_data[fromportnum],0);
}

// datalink layer send
void network::dl_to_pl(bit_frame* args,link_interface_state* this_iface)
{
  data_units du_args;
  du_args.bit_args = *args;
  ++netstats[this_iface->netnum][this_iface->hostnum].frames_sent[this_iface->ifacenum];
   if(print_at[frame_send_interface]==1)
   {
     show_host_iface(this_iface,
       "interface","DLC layer sends to physical layer frame:");
     print_bits((bit_frame*)args);
     interact();
     if(print_at[frame_send_interface]>1 || print_at[frame_send_interface]<0)
       dlc_send_user_out(args,this_iface);
  }
//  schedule the result of this action to happen
  schedule_event(DL_PROCESSING_TICKS,frame_send_interface,du_args,this_iface,0);
}

// link physical layer send
void network::send_across_link(Sim_Timer link_delay,physical_frame* args,
  link_interface_state* this_iface)
{
  byte to_portnum = interfaces[this_iface->portnum].other_end;
  byte to_netnum = interfaces[to_portnum].net;
  byte to_hostnum = interfaces[to_portnum].host;
  byte to_ifacenum = interfaces[to_portnum].ifacenum;
  data_units du_args;
  du_args.phys_args = *args;
  if(print_at[physical_send_interface]==1)
  {
    show_host_iface(this_iface,"interface","physical layer transmits frame:");
    print_bits((bit_frame*)args);
    interact();
    if(print_at[physical_send_interface]>1 || print_at[physical_send_interface]<0)
      physical_send_user_out(args,this_iface);
  }
//  schedule the result of this action to happen
  schedule_event(link_delay,physical_send_interface,du_args,
    host_state_data[to_netnum][to_hostnum]->linkstate[to_ifacenum],0);
}

// mac token physical layer send
void network::send_across_token_lan(Sim_Timer token_delay,physical_frame* args,
                                  mac_interface_state* mac_iface,byte toportnum)
{
  data_units du_args;
  du_args.phys_args = *args;
  bit timeout_event_bits[8] = {1,0,0,0,0,0,0,0}; // code showing this is
                                                 // timeout event not frame
// test for timeout event (not a real data send) - do not print
  bit timeout_true = TRUE;
  for (int codetest=0; codetest<8; ++codetest)
    timeout_true = timeout_true &&
      (args->frame_bits[codetest] == timeout_event_bits[codetest]);
  if(print_at[token_send_interface]==1 && !timeout_true)
  {
    show_host(host_state_data[mac_iface->netnum][mac_iface->hostnum],
      "token LAN interface begins to forward frame:");
     print_bits((bit_frame*)args);
     interact();
     if(print_at[token_send_interface]>1 || print_at[token_send_interface]<0)
       token_send_user_out(args,mac_iface,toportnum);
   }
//  schedule the result of this action to happen
  schedule_event(token_delay,token_send_interface,du_args,mac_state_data[toportnum],0);
}

// mac csma physical layer send
void network::send_across_csma(Sim_Timer csma_delay,physical_frame* args,
                             mac_interface_state* mac_iface,byte toportnum)
{
  data_units du_args;
  du_args.phys_args = *args;
  if(print_at[csma_send_interface]==1)
  {
    show_host(host_state_data[mac_iface->netnum][mac_iface->hostnum],
      "CSMA/CD LAN transmits frame:");
    print_bits((bit_frame*)args);
    interact();
    if(print_at[csma_send_interface]>1 || print_at[csma_send_interface]<0)
      csma_send_user_out(args,mac_iface,toportnum);
  }
//  schedule the result of this action to happen
  mac_iface->frame_event_SN[1] =
    schedule_event(csma_delay,csma_send_interface,du_args,mac_state_data[toportnum],
      mac_iface->portnum);
}

// mac physical layer broadcast (sends to netnum subnet)
void network::broadcast_csma(Sim_Timer csma_delay,physical_frame* args,
                           mac_interface_state* mac_iface)
{
  data_units du_args;
  du_args.phys_args = *args;
  if(print_at[csma_send_interface]==1)
  {
    show_host(host_state_data[mac_iface->netnum][mac_iface->hostnum],
      "CSMA/CD LAN broadcasts frame:");
    print_bits((bit_frame*)args);
    interact();
  }
//  schedule the result of this action to happen
   byte netnum = mac_iface->netnum;
   byte fromportnum = mac_iface->portnum;
   for(int testhost=1; testhost<net_state_data[netnum]->lan_hosts; ++testhost)
   {
     int testportnum = lookup(netnum,testhost,0);
     if((testportnum < MAX_BYTE) && (testportnum != fromportnum))
     {
       mac_state_data[fromportnum]->frame_event_SN[testhost] =
         schedule_event(csma_delay,csma_send_interface,du_args,
           mac_state_data[testportnum],fromportnum);
       if(print_at[csma_send_interface]>1 || print_at[csma_send_interface]<0)
         csma_send_user_out(args,mac_state_data[fromportnum],BC_MAC_ADDR);
     }
   }
}

// mac physical layer jamming broadcast (sends to netnum subnet)
void network::jam_csma(Sim_Timer csma_delay,physical_frame* args,
                     mac_interface_state* mac_iface)
{
  data_units du_args;
  du_args.phys_args = *args;
  if(print_at[csma_send_interface]==1)
  {
    show_host(host_state_data[mac_iface->netnum][mac_iface->hostnum],
      "CSMA/CD LAN broadcasts frame:");
    print_bits((bit_frame*)args);
    interact();
  }
//  schedule the result of this action to happen
  byte netnum = mac_iface->netnum;
  byte hostnum = mac_iface->hostnum;
  byte fromportnum = mac_iface->portnum;
  for(int testhost=1; testhost<=net_state_data[netnum]->lan_hosts; ++testhost)
  {
    if(testhost != hostnum) // sender of broadcast does not receive it
    {
      int testportnum = lookup(netnum,testhost,0);
      if(testportnum < MAX_BYTE)
      {
        schedule_event(csma_delay,csma_send_interface,du_args,
          mac_state_data[testportnum],fromportnum);
        if(print_at[csma_send_interface]>1 || print_at[csma_send_interface]<0)
          csma_send_user_out(args,mac_state_data[fromportnum],BC_MAC_ADDR);
      }
    }
  }
}

// mac physical layer multicast (sends to netnum subnet)
void network::multicast_csma(Sim_Timer csma_delay,physical_frame* args,
                           mac_interface_state* mac_iface)
{
  data_units du_args;
  du_args.phys_args = *args;
  if(print_at[csma_send_interface]==1)
  {
    show_host(host_state_data[mac_iface->netnum][mac_iface->hostnum],
      "CSMA/CD LAN multicasts frame:");
    print_bits((bit_frame*)args);
    interact();
  }
//  schedule the result of this action to happen
   byte netnum = mac_iface->netnum;
   byte fromportnum = mac_iface->portnum;
   for(int testhost=1; testhost<=net_state_data[netnum]->hosts_in_mc_group; ++testhost)
   {
     int testport = lookup(netnum,testhost,0);
     if(testport != fromportnum)
     {
       if(print_at[csma_send_interface]>1 || print_at[csma_send_interface]<0)
         csma_send_user_out(args,mac_state_data[fromportnum],MC_MAC_ADDR);
       if(testport < MAX_BYTE)
         schedule_event(csma_delay,csma_send_interface,du_args,
           mac_state_data[testport],fromportnum);
     }
   }
}

// token frame receive - delay when whole frame is processed
void network::delay_to_token_receive(Sim_Timer ticks,bit_frame* args,
                                   mac_interface_state* mac_iface)
{
  data_units du_args;
  du_args.bit_args = *args;
  schedule_event(ticks,token_receive_interface,du_args,mac_iface,0);
}

// datalink layer receive
void network::pl_to_dl(bit_frame* args,link_interface_state* this_iface)
{
  data_units du_args;
  du_args.bit_args = *args;
// collect dl statistics
  ++netstats[this_iface->netnum][this_iface->hostnum].frames_received[this_iface->ifacenum];
  if(print_at[frame_receive_interface]==1)
  {
    show_host_iface(this_iface,"interface","physical layer receives and passes to DLC:");
    print_bits((bit_frame*)args);
    interact();
    if(print_at[frame_receive_interface]>1 || print_at[frame_receive_interface]<0)
      dlc_receive_user_out(args,this_iface);
  }
//  schedule the result of this action to happen
  schedule_event(1,frame_receive_interface,du_args,this_iface,0);
}


// lan datalink layer receive
void network::mac_to_dl(bit_frame* args,mac_interface_state* mac_iface)
{
  data_units du_args;
  du_args.bit_args = *args;
  byte netnum = interfaces[mac_iface->portnum].net;
  byte hostnum = interfaces[mac_iface->portnum].host;
  link_interface_state* this_iface = host_state_data[netnum][hostnum]->linkstate[0];
// collect dl statistics
  ++netstats[netnum][hostnum].frames_received[0];
  if(print_at[frame_receive_interface]==1)
  {
    if(net_state_data[netnum]->token_lan)
      show_host(host_state_data[netnum][hostnum],
        "token LAN interface receives and passes to DLC:");
    else
      show_host(host_state_data[netnum][hostnum],
        "CSMA/CD LAN interface receives and passes to DLC:");
    print_bits((bit_frame*)args);
    interact();
    if(print_at[frame_receive_interface]>1 || print_at[frame_receive_interface]<0)
      dlc_receive_user_out(args,this_iface);
  }
//  schedule the result of this action to happen
  schedule_event(TOKEN_PROCESSING_TICKS,frame_receive_interface,du_args,
	this_iface,0);
}

// network layer receive
void network::dl_to_nl(packet* args,link_interface_state* this_iface)
{
  data_units du_args;
  du_args.packet_args = *args;
  host_state* this_host = host_state_data[this_iface->netnum][this_iface->hostnum];
  byte ifacenum = this_iface->ifacenum;
// collect nl statistics
  if(du_args.packet_args.dest_net < MC_NET_ADDR)
    ++netstats[this_iface->netnum][this_iface->hostnum].uc_packets_received;
  else 
  { 
    if(du_args.packet_args.dest_net == MC_NET_ADDR)
      if(net_state_data[this_iface->netnum]->hosts_in_mc_group)
        ++netstats[this_iface->netnum][this_iface->hostnum].mc_packets_received;
    if(du_args.packet_args.dest_net == BC_NET_ADDR)
      ++netstats[this_iface->netnum][this_iface->hostnum].bc_packets_received;
  }
  if(print_at[packet_receive_interface]==1)
  {
    if(du_args.packet_args.dest_net == MC_NET_ADDR)
      show_host_iface(this_iface,"DLC sends to network layer","multicast packet:");
    else if(du_args.packet_args.dest_net == BC_NET_ADDR)
      show_host_iface(this_iface,"DLC sends to network layer","broadcast packet:");
    else show_host_iface(this_iface,"DLC sends to network layer","packet:");
    print_chars((char_data*)args);
    interact();
    if(print_at[packet_receive_interface]>1 || print_at[packet_receive_interface]<0)
      packet_receive_user_out(args,this_host,ifacenum);
  }
//  schedule the result of this action to happen
  schedule_event(DL_PROCESSING_TICKS,packet_receive_interface,du_args,
	this_host,ifacenum);
}

// delayed delivery to network receive - no layer delay added or statistics kept
void network::delay_to_nl_receive(Sim_Timer ticks,packet* args,host_state* this_host)
{
  data_units du_args;
  du_args.packet_args = *args;
  schedule_event(ticks,packet_receive_interface,du_args,this_host,0);
}

// routing protocol receive
void network::nl_to_routing(segment* args,host_state* this_host)
{
  data_units du_args;
  du_args.segment_args = *args;
// collect routing statistics
  ++netstats[this_host->netnum][this_host->hostnum].routing_packets_received;
  if(print_at[routing_receive_interface]==1)
  {
    show_host(this_host,"network layer sends to routing protocol segment:");
    print_chars((char_data*)args);
    interact();
    if(print_at[routing_receive_interface]>1 || print_at[routing_receive_interface]<0)
      routing_receive_user_out((SPF_segment*)args,this_host);
  }
//  schedule the result of this action to happen
  schedule_event(ROUTING_PROCESSING_TICKS,routing_receive_interface,du_args,
    this_host,0);
}

// best-effort transport layer receive
void network::nl_to_utl(segment* args,host_state* this_host)
{
  data_units du_args;
  du_args.segment_args = *args;
// collect tl statistics
  ++netstats[this_host->netnum][this_host->hostnum].u_segments_received;
  if(print_at[usegment_receive_interface]==1)
  {
    show_host(this_host,"network layer sends to transport layer segment:");
    print_chars((char_data*)args);
    interact();
    if(print_at[usegment_receive_interface]>1 || print_at[usegment_receive_interface]<0)
      segment_receive_user_out(args,this_host);
  }
//  schedule the result of this action to happen
  schedule_event(NL_PROCESSING_TICKS,usegment_receive_interface,du_args,
    this_host,0);
}

// reliable transport layer receive
void network::nl_to_rtl(segment* args,host_state* this_host)
{
  data_units du_args;
  du_args.segment_args = *args;
// collect tl statistics
  ++netstats[this_host->netnum][this_host->hostnum].r_segments_received;
  if(print_at[rsegment_receive_interface]==1)
  {
    show_host(this_host,"network layer sends to transport layer segment:");
    print_chars((char_data*)args);
    interact();
    if(print_at[rsegment_receive_interface]>1 || print_at[rsegment_receive_interface]<0)
      segment_receive_user_out(args,this_host);
  }
//  schedule the result of this action to happen
  schedule_event(NL_PROCESSING_TICKS,rsegment_receive_interface,du_args,
    this_host,0);
}

// application layer receive
void network::tl_to_al(message* args,host_state* this_host)
{
  data_units du_args;
  du_args.message_args = *args;
// collect al statistics
  if(args->size > 0)
  {
    if(args->message_type == email)
      ++netstats[this_host->netnum][this_host->hostnum].email_messages_received;
    if(args->message_type == stream)
      ++netstats[this_host->netnum][this_host->hostnum].stream_messages_received;
  }
  if(print_at[message_receive_interface]==1)
  {
    show_host(this_host,"transport layer sends to application layer message:");
    print_email(args);
    interact();
    if(print_at[message_receive_interface]>1 || print_at[message_receive_interface]<0)
      message_receive_user_out(args,this_host);
  }
//  schedule the result of this action to happen
  schedule_event(TL_PROCESSING_TICKS,message_receive_interface,du_args,
	this_host,0);
}
