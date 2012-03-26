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
	zero_bits(stuffed_frame);
	int i_bitpos;
	int o_bitpos;
	int i;
	int one_count = 0;

	// find length by converting the byte starting at bit 8
	int unstuffed_length = 8*bittobyte(8,unstuffed_frame->frame_bits);
 
	// Copy the first 8 bits into the output frame
	// since we know it's the beginning of the input frame
	for (i_bitpos=0; i_bitpos < 8; i_bitpos++) {
		stuffed_frame->frame_bits[i_bitpos] = unstuffed_frame->frame_bits[i_bitpos];
	}

	// Now look for a string of five 1's in between the
	// start and end bitstrings of the frame
	for (i_bitpos=8, o_bitpos=8; i_bitpos < (unstuffed_length-8); i_bitpos++, o_bitpos++) {
		bit current_bit = unstuffed_frame->frame_bits[i_bitpos];
		if (current_bit == 0) {
			stuffed_frame->frame_bits[o_bitpos] = current_bit;
			one_count = 0;
		} else  {
			// If we found a 1, increment the counter and check to
			// see if that's the fifth 1 we've see...
			stuffed_frame->frame_bits[o_bitpos] = current_bit;
			one_count++;
			if (one_count == 5) {
				// If it is, then insert a 0 and reset the counter
				o_bitpos++;
				stuffed_frame->frame_bits[o_bitpos] = 0;
				one_count = 0;
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
	zero_bits(unstuffed_frame);
	int one_count = 0;
	int i_bitpos = 0;
	int o_bitpos = 0;
	int end_frame = 0;
	
	// Copy the first 8 bits into the output frame
	// since we know it's the beginning of the input frame
	for (i_bitpos=0, o_bitpos=0; i_bitpos < 8; i_bitpos++, o_bitpos++) {
		unstuffed_frame->frame_bits[o_bitpos] = stuffed_frame->frame_bits[i_bitpos];
	}

	// Now cycle through the input frame, looking for the flag 01111110
	while (!end_frame && i_bitpos < MAX_BIT_FRAME_SIZE) {
		bit current_bit = stuffed_frame->frame_bits[i_bitpos];

		// If the current bit is a 0, copy it to the
		// unstuffed frame and reset the 1 counter
		if (current_bit == 0) {
			unstuffed_frame->frame_bits[o_bitpos] = current_bit;
			one_count = 0;
		} else {
			// If we found a 1, copy it over and increment the counter.
			unstuffed_frame->frame_bits[o_bitpos] = current_bit;
			one_count++;
			
			// If the counter is five, check the next bit (if it doesn't
			// exceed the maximum frame length) to see what we should do next
			if (one_count == 5 && (i_bitpos+1) < MAX_BIT_FRAME_SIZE) {
				bit next_bit = stuffed_frame->frame_bits[i_bitpos+1];
				
				// If the next bit is a zero, ignore it and
				// increment the input bit index
				if (next_bit == 0) {
					i_bitpos++;
					one_count = 0;
				} else {
					// Otherwise, we've found six 1's and that means we're
					// at the end of the frame.  Copy over a 1 and then a tailing 0
					unstuffed_frame->frame_bits[++o_bitpos] = next_bit;
					unstuffed_frame->frame_bits[++o_bitpos] = 0;
					end_frame = 1;
				}
			}
		}
		i_bitpos++;
		o_bitpos++;
	}
}
