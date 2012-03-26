# Makefile for room.c
#
# tim cheadle
# tcheadle@gmu.edu
#
# $Id: Makefile,v 1.2 2003-02-06 06:01:51 session Exp $
#
GCC = gcc-3.0
CFLAGS = -lGL -lglut -std=gnu99

all: room plane

room: room.c
	${GCC} ${CFLAGS} room.c -o room

plane: plane.c
	${GCC} ${CFLAGS} plane.c -o plane

clean:
	rm -fr room core
