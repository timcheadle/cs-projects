# Makefile for forest.c
#
# tim cheadle
# tcheadle@gmu.edu
#
# $Id: Makefile,v 1.1 2002-12-09 03:41:15 session Exp $
#
GCC = gcc-3.0
CFLAGS = -lGL -lglut -std=gnu99

all: forest

forest: forest.c
	${GCC} ${CFLAGS} forest.c -o forest

clean:
	rm -fr forest core
