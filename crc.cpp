// last changed 5-26-99
#ifndef HEADERFILE
#include "nw.h"
#endif
//  insert your name in the function below
char* stack::crc_author() {return "Tim Cheadle";}
// Copyright 1997-1999 J.M.Pullen/George Mason University
// Interface for CRC generator for Network Workbench
//*********************************************************************
FCS stack::generate_FCS(bit_frame* FCS_frame)
                                  // creates crc for bit frame
                                  // using CCITT 0-5-12-16 polynomial
{
	// NOTE:  throughout this program position 0 is treated as high-order
	// in character and bit strings for parity computation, etc.

	// Create an FCS array with 16 bits
	FCS result_FCS = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

	// Calculate the length of the incoming data frame
	int input_length = 8*bittobyte(8,FCS_frame->frame_bits);

	// Initialize a feedback bit
	int feedback = 0;

	// For every bit in the data frame, going left to right and
	// ignoring the framing flags
	for(int input_pos = 8; input_pos < input_length-8; input_pos++) {
		// Push the high-order FCS bit (bit 0) into the feedback bit
		feedback = result_FCS.FCS_bits[0];

		// Now shift every bit in the FCS array left one position
		for (int shift_pos = 0; shift_pos < 15; shift_pos++) {

			// If we're shifting something into bits 5 or 12 of
			// the CCITT standard (which are bits 10 and 3 for us),
			// XOR the shifted bit before storing it
			if (shift_pos == 10 || shift_pos == 3) {
				result_FCS.FCS_bits[shift_pos] = feedback ^ result_FCS.FCS_bits[shift_pos+1];
			
			// Otherwise, just shift left
			} else {
				result_FCS.FCS_bits[shift_pos] = result_FCS.FCS_bits[shift_pos+1];
			}
		}

		// Now XOR the incoming data bit with the feedback bit
		// and put it into the lowest-order bit of FCS
		result_FCS.FCS_bits[15] = feedback ^ FCS_frame->frame_bits[input_pos];
	}

	// Print the resulting FCS array
	cout << "result_FCS: ";
	showbits(result_FCS.FCS_bits, 16);
	cout << endl;

	return result_FCS;
}
