	/* lex project
	 * cs330
	 * tim cheadle
	 *
	 * $Id: project.l,v 1.1 2004-06-08 04:25:20 session Exp $
	 */

	/* string.h for toupper() call below */
	#include <string.h>  
	/* initialize whitespace counters */
	int blankc=0, tabc=0, newlinec=0;

D [0-9]
P [1-9]
S [+-]
L [a-zA-Z]
INTEGER 0|({P}{D}*)
REAL {S}?{D}+((\.{D}+(E{S}?{D}+)?)|(E{S}?{D}+))
TAG \<\/?{L}{1,6}\>
COMMENT \<!--(.?[^-])*--\>
BLANK [ ]
TAB \t
NEWL \n
OTHER .

%%
{INTEGER} {
	printf("INTEGER:(%s)", yytext);
}

{REAL} {
	printf("REAL:(%s)", yytext);
}

{TAG} {
	int i=0;
	/* if the second char is a '/', it's an end tag */
	if (yytext[1] == '/') {
		printf("ENDTAG:");
		/* only print out the word inside the tag, not the brackets or slash */
		for (i=2; i < yyleng-1; i++) {
			printf("%c", toupper(yytext[i]));
		}
	/* otherwise it's a start tag... */
	} else {
		printf("TAG:");
		/* only print the word inside the tag, omit the brackets */
		for (i=1; i < yyleng-1; i++) {
			printf("%c", toupper(yytext[i]));
		}
	}
}

{COMMENT} {
	int IS_MULTI=0;
	int i;
	/* look for a \n anywhere; if so it's a multiline */
	for (i=4; i < yyleng-3 && IS_MULTI==0; i++) {
		if (yytext[i] == '\n') {
			IS_MULTI=1;
			printf("MULTI-LINE: %s", yytext);
		}
	}
	/* if no newlines were found, its a one-line */
	if (IS_MULTI==0) {
		printf("ONE-LINE: %s", yytext);
	}
}

{BLANK} { blankc++; printf(" "); }
{TAB} { tabc++; printf("\t"); }
{NEWL} { newlinec++; printf("\n"); }

{OTHER} { printf("%s", yytext); }

%%
main() {
	yylex();
	printf("BLANKS  : %d\n", blankc);
	printf("TABS    : %d\n", tabc);
	printf("NEWLINES: %d\n", newlinec);
}
