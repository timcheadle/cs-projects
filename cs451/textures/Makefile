# Makefile for room.c
#
# tim cheadle
# tcheadle@gmu.edu
#
# $Id: Makefile,v 1.1 2003-02-06 05:54:14 session Exp $
#
GCC = gcc-3.0
CFLAGS = -lGL -lglut -std=gnu99

all: texture

texture: texture.c
	${GCC} ${CFLAGS} texture.c -o texture

clean:
	rm -fr texture core
