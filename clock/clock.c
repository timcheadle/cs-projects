/*
 * clock.c
 *
 * A simple 2D simulation of a clock's second hand.
 *
 * tim cheadle
 * tcheadle@gmu.edu
 *
 * $Id: clock.c,v 1.2 2002-09-15 05:12:58 session Exp $
 */

#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define PI 3.141592654

static GLfloat spin = 0.0;
static GLint spinning = 0;
static GLint raise_color = 1;
static GLfloat radius = 35.0;
static GLfloat red = 0.5, green = 0.5, blue = 0.5;
static GLint divisions = 60;

void init(void) 
{
	glClearColor (0.0, 0.0, 0.0, 0.0);
	glShadeModel (GL_FLAT);
}

void display(void)
{
	GLfloat angle = 0;
	int i;
	
	glClear(GL_COLOR_BUFFER_BIT);
	
	glBegin(GL_LINE_STRIP);
	glColor3f(red, green, blue);
    glVertex2f(radius, 0);
	for (i = 0; i <= divisions; i++) {
		angle = (((double) i) / ((double) divisions)) * 2.0 * PI;
		glVertex2f((radius * cos(angle)), (radius * sin(angle)));
	}
	glEnd();
	
	/* Draw the rectangle part of the clock hand */
	glPushMatrix();
	glRotatef(spin, 0.0, 0.0, 1.0);
	glColor3f(red, green, blue);
	glRectf(0.0, 1.0, 30.0, -1.0);
	glPopMatrix();
	
	/* Draw the triangle end of the clock hand */
	glPushMatrix();
	glRotatef(spin, 0.0, 0.0, 1.0);
	glColor3f(red, green, blue);
	glBegin(GL_TRIANGLES);
		glVertex2f(30.0, 2.0);
		glVertex2f(33.0, 0.0);
		glVertex2f(30.0, -2.0);
	glEnd();
	glPopMatrix();
	glutSwapBuffers();
}

void spinDisplay(void)
{
	seed(time());
	time_t t = time(0);
	GLfloat change = 0.0005;
	spin = spin - 0.03; /*(360/60); */
	if (spin > 360.0)
		spin = spin - 360.0;
	/* while(t == time(0)) { } */
	if (red < 1.0 && raise_color) {
		raise_color = 1;
	} else {
		raise_color = 0;
	}
	if (red > 0.3 && !raise_color) {
		raise_color = 0;
	} else {
		raise_color = 1;
	}
	if (raise_color) {
		red += change*(rand()%10)/10.0;
		green += change*(rand()%10)/10.0;
		blue += change*(rand()%10)/10.0;
	} else {
		red -= change*(rand()%10)/10.0;
		green -= change*(rand()%10)/10.0;
		blue -= change*(rand()%10)/10.0;
	}
	glutPostRedisplay();
	
}

void reshape(int w, int h)
{
	glViewport (0, 0, (GLsizei) w, (GLsizei) h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(-50.0, 50.0, -50.0, 50.0, -1.0, 1.0);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
}

void mouse(int button, int state, int x, int y) 
{
	switch (button) {
		case GLUT_LEFT_BUTTON:
			if (state == GLUT_DOWN) {
				if (!spinning) {
					glutIdleFunc(spinDisplay);
					spinning = 1;
				} else {
					glutIdleFunc(NULL);
					spinning = 0;
				}
			}
			break;
		default:
			break;
	}
}

/* 
 *  Request double buffer display mode.
 *  and register mouse input callback functions
 */
int main(int argc, char** argv)
{
	glutInit(&argc, argv);
	glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB);
	glutInitWindowSize (250, 250); 
	glutInitWindowPosition (100, 100);
	glutCreateWindow (argv[0]);
	init ();
	glutDisplayFunc(display); 
	glutReshapeFunc(reshape); 
	glutMouseFunc(mouse);
	glutMainLoop();
	return 0;
}
