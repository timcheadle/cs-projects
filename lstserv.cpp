// last changed 9-13-99
#ifndef HEADERFILE
#include "nw.h"
#endif
// insert your name in the function below
char* stack::ls_author(){return "Tim Cheadle";}
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

	int i, j;
	int subscribing = 0;
	char compare[9];

	if (incoming_message->size >= 9) {
		for(i=0; i < incoming_message->size; i++) {
			if (
					incoming_message->text[i] == subscribe_message[0] &&
					incoming_message->text[i+1] == subscribe_message[1] &&
					incoming_message->text[i+2] == subscribe_message[2] &&
					incoming_message->text[i+3] == subscribe_message[3] &&
					incoming_message->text[i+4] == subscribe_message[4] &&
					incoming_message->text[i+5] == subscribe_message[5] &&
					incoming_message->text[i+6] == subscribe_message[6] &&
					incoming_message->text[i+7] == subscribe_message[7] &&
					incoming_message->text[i+8] == subscribe_message[8]
			   )
			{
				subscribing = 1;
			}
		}
	}

	// If it was a subscription request and we have more room on the list, add
	// the new subscriber and increment the subscriber count
	if (subscribing) {
		if (server_list_count < LIST_SERVER_CAPACITY) {
			list_server_addresses[server_list_count].net = incoming_message->source_net;
			list_server_addresses[server_list_count].host = incoming_message->source_host;
			/*
			cout << "subscribing: " 
				<< (int)list_server_addresses[server_list_count].net
				<< "."
				<< (int)list_server_addresses[server_list_count].host
				<< endl;
			*/
			server_list_count++;
		}
		return;

	// If they aren't trying to subscribe, forward the message to everyone on the list
	} else {
		for(j=0; j < server_list_count; j++) {
			message outgoing_message = *incoming_message;
			outgoing_message.dest_net = list_server_addresses[j].net;
			outgoing_message.dest_host = list_server_addresses[j].host;
			send_email(&outgoing_message,this_host,FALSE);  
		}
	}
}
