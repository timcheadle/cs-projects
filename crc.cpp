// last changed 5-26-99
#ifndef HEADERFILE
#include "nw.h"
#endif
//  insert your name in the function below
char* stack::crc_author() {return " ";}
// Copyright 1997-1999 J.M.Pullen/George Mason University
// Interface for CRC generator for Network Workbench
//*********************************************************************
FCS stack::generate_FCS(bit_frame* FCS_frame)
                                  // creates crc for bit frame
{                                 // using CCITT 0-5-12-16 polynomial
   FCS result_FCS = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
// find length by converting the byte starting at bit 8
   int input_length = 8*bittobyte(8,FCS_frame->frame_bits);
//
// NW DLC CRC Algorithm
// --------------------
// establish an array FCS with 16 positions;
// do for each data bit (but not for the start and end flags), 
// scanning the frame from left to right,
// { 
//   make a copy of the high order FCS bit, call it "feedback";
//   shift each FCS bit to the next higher FCS order bit;
//   shift one data bit into the low-order bit of the FCS;
//   as bits 0, 5 and 12 are shifted, 
//     XOR them with the feedback bit;
// }.
//
// NOTE:  throughout this program position 0 is treated as high-order
// in character and bit strings for parity computation, etc.
//
//  student inserts CRC calculation here


   return result_FCS;
}
