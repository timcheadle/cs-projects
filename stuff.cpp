// last changed 10-5-99
#ifndef HEADERFILE
#include "nw.h"
#endif
//  insert your name in the function below
char* stack::stuff_author() {return "Tim Cheadle";}
// Copyright 1997-1999 J.M.Pullen/George Mason University
//************************************************************************
// this function fills a bit frame with zeros
//
void stack::zero_bits(bit_frame* zeros)
{
  for(int i=0; i<MAX_BIT_FRAME_SIZE; ++i)zeros->frame_bits[i]=0;
}
//************************************************************************
//
// this function generates a stuffed bit frame from an unstuffed bit frame
// inserting a zero bit after every five one bits except in the opening and
// closing flag
//
void stack::stuff(bit_frame* stuffed_frame,bit_frame* unstuffed_frame)
{
//
// bit stuffing/unstuffing function for DLC in Network Workbench
//
// Algorithm for DLC 1: stuffing a bit frame
// -----------------------------------------
//
// Copy the first eight bits to the output string without stuffing;
// Scan the data bits from left to right for length minus sixteen,
// {
//   copy from input to output,
//   {		
//     maintain an index, starting at 8, in the input bit array; 
//     another index, starting at 8, in the output array;
//     and a counter, starting at zero;
//     when copying a bit,
//     { 
//       move the input bit to output;
//       if it is a zero bit, set the counter to zero;
//       if it is a one bit, add one to the counter;
//       if the counter value is five,
//       {
//         insert a zero in the output;
//         zero the counter;
//         add one to the output index;
//       }
//       add one to input index;
//       add one to output index;
//     }
//   }
// }
// Copy the last eight bits without stuffing.
//
// NOTE: this version only copies the frame, student must change to 
// add stuffing
//

	zero_bits(stuffed_frame);
	int i_bitpos;
	int o_bitpos;
	int i;
	int onecount = 0;

	// find length by converting the byte starting at bit 8
	int unstuffed_length = 8*bittobyte(8,unstuffed_frame->frame_bits);
 
	// Copy the first 8 bits into the output string
	// since we know it's the beginning of a frame
	for (i_bitpos=0; i_bitpos < 8; i_bitpos++) {
		stuffed_frame->frame_bits[i_bitpos] = unstuffed_frame->frame_bits[i_bitpos];
	}

	showbits(unstuffed_frame->frame_bits, unstuffed_length);
	
	// Now look for a string of five 1's in between the
	// start and end bitstrings of the frame
	for (i_bitpos=8, o_bitpos=8; i_bitpos < (unstuffed_length-8); i_bitpos++, o_bitpos++) {
		bit current_bit = unstuffed_frame->frame_bits[i_bitpos];
		if (current_bit == 0) {
			stuffed_frame->frame_bits[o_bitpos] = current_bit;
			onecount = 0;
		} else  {
			// If we found a 1, increment the counter and check to
			// see if that's the fifth 1 we've see...
			stuffed_frame->frame_bits[o_bitpos] = current_bit;
			onecount++;
			if (onecount == 5) {
				// If it is, then insert a 0 and reset the counter
				stuffed_frame->frame_bits[o_bitpos] = 0;
				o_bitpos++;
				onecount = 0;
			}
		}
	}

	// Now copy the ending 8 bits of the input string
	// (which we know is the ending marker of the frame)
	for (i_bitpos=unstuffed_length-8; i_bitpos < unstuffed_length; i_bitpos++) {
		stuffed_frame->frame_bits[o_bitpos] = unstuffed_frame->frame_bits[i_bitpos];
		o_bitpos++;
	}
}

//************************************************************************
//
// this function generates an unstuffed bit frame from a stuffed bit frame
// removing a zero bit after every five one bits except in the opening and
// closing flag
//
void stack::unstuff(bit_frame* unstuffed_frame,bit_frame* stuffed_frame)
{
  int bitpos;
//
// Algorithm for Unstuffing
// ------------------------
// Left as an exercise for the student (it is very much like stuffing)
//
// NOTE: this version only copies the frame, student must change to
// add stuffing
//
  for(bitpos=0; bitpos<MAX_BIT_FRAME_SIZE; ++bitpos)
    unstuffed_frame->frame_bits[bitpos] = stuffed_frame->frame_bits[bitpos];
}
