# Makefile for CS330 lex project
# tim cheadle
#
# $Id: Makefile,v 1.1 2004-06-08 04:25:20 session Exp $
#
all: project.l
	flex project.l
	cc lex.yy.c -ll

clean:
	rm -fr a.out lex.yy.c

gmu:
	scp project.l DF Makefile readme tcheadle@osf1.gmu.edu:/home/u5/tcheadle/lex
