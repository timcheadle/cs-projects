/*
 * clock.c
 *
 * A simple 2D simulation of a clock's second hand.
 *
 * tim cheadle
 * tcheadle@gmu.edu
 *
 * $Id: clock.c,v 1.1 2002-09-15 04:14:36 session Exp $
 */

#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <stdlib.h>
#include <time.h>

static GLfloat spin = 0.0;
static GLint spinning = 0;

void init(void) 
{
	glClearColor (0.0, 0.0, 0.0, 0.0);
	glShadeModel (GL_FLAT);
}

void display(void)
{
	glClear(GL_COLOR_BUFFER_BIT);
	glPushMatrix();
	glRotatef(spin, 0.0, 0.0, 1.0);
	glColor3f(0.8, 0.5, 0.5);
	glRectf(0.0, 1.0, 30.0, -1.0);
	glPopMatrix();
	glutSwapBuffers();
}

void spinDisplay(void)
{
	time_t t = time(0);
	spin = spin - (360/60); 
	if (spin > 360.0)
		spin = spin - 360.0;
	while(t == time(0)) { }
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
