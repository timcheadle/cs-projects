// last changed 9-13-99
#ifndef HEADERFILE
#include "nw.h"
#endif
// insert your name in the function below
char* stack::ls_author(){return " ";}
// Copyright 1997-1999 J.M.Pullen/George Mason University
// list server module for Network Workbench
//*****************************************************************************
void stack::list_server(message* incoming_message,host_state* this_host)
{
  char subscribe_message[9] = {'s','u','b','s','c','r','i','b','e'};
//
// NW Listserver Algorithm
//
// If incoming_message.text contains "subscribe",
// {
//   if the list contains less than LIST_SERVER_CAPACITY entries,
//   {
//     add the source_net and source_host from the message at the end 
//     of the array of list_server_addresses;
//     increment server_list_count;
//   }  
//   return;
// } 
// otherwise replicate email copies to the list:
// do for server_list_count,
// {
//   copy incoming_message to outgoing_message;
//   insert net number and host number from list_server_addresses 
//   in outgoing_message;
//   invoke send_email using outgoing_message coded for best-effort 
//   transport;  
// }.
  message outgoing_message = *incoming_message;
// student code goes here: 

  send_email(&outgoing_message,this_host,FALSE);  
}
