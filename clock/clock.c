/*
 * clock.c
 *
 * A simple 2D simulation of a clock's second hand.
 *
 * tim cheadle
 * tcheadle@gmu.edu
 *
 * $Id: clock.c,v 1.3 2002-09-24 05:56:36 session Exp $
 */

#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define PI 3.141592654

const GLint divisions = 60; /* number of sides on circle approx. polygon*/
const GLfloat radius = 25.0; /* radius of the circle */
const GLfloat center_x = 15.0, center_y = 15.0; /* center point of rotation */

static GLint spinning = 0; /* clock hand spinning on/off flag */
static GLfloat spin = 0.0; /* angle of rotation to spin the clock hand */
static GLfloat red   = 0.3, green = 0.9, blue  = 0.3; /* color of polygons */

/*
 * Initialize the window buffer
 */
void init(void) 
{
	glClearColor (0.0, 0.0, 0.0, 0.0);
	glShadeModel (GL_FLAT);
}

/*
 * Draw all the circle and clock hand, making the necessary
 * translations and rotations
 */
void display(void)
{
	GLfloat angle = 0;
	int i;
	
	/* Clear the output color buffer) */
	glClear(GL_COLOR_BUFFER_BIT);
	
	/* Draw the circle approximation polygon */
	glBegin(GL_LINE_STRIP);
	glColor3f(red, green, blue);
	glVertex2f(center_x + radius, center_y);
	for (i = 0; i <= divisions; i++) {
		/* 
		 * (Go around the circle making right triangles every time
		 * and draw the opposite side of the triangle; angle = 2*pi / divisions
		 */
		angle = (((double) i) / ((double) divisions)) * 2.0 * PI;
		glVertex2f(center_x + (radius * cos(angle)), center_y + (radius * sin(angle)));
	}
	glEnd();
	
	/* Draw the rectangle part of the clock hand and rotate */
	glPushMatrix();
	glTranslatef(center_x, center_y, 0.0);
	glRotatef(spin, 0.0, 0.0, 1.0);
	glTranslatef(0.0, 0.0, 0.0);
	glColor3f(red, green, blue);
	glRectf(0.0, 1.0, 20.0, -1.0);
	glPopMatrix();
	
	/* Draw the triangle end of the clock hand and rotate */
	glPushMatrix();
	glTranslatef(center_x, center_y, 0.0);
	glRotatef(spin, 0.0, 0.0, 1.0);
	glTranslatef(0.0, 0.0, 0.0);
	glColor3f(red, green, blue);
	glBegin(GL_TRIANGLES);
		glVertex2f(20.0, 2.0);
		glVertex2f(23.0, 0.0);
		glVertex2f(20.0, -2.0);
	glEnd();
	glPopMatrix();
	glutSwapBuffers();
}

/*
 * Update the angle of rotation for the clock hand 
 * every second if spinning is on
 */
void spinDisplay(int value)
{
	/* If the spinning flag is on, rotate clockwise by an angle (2 * PI)/60 radians */
	if (spinning) {
		GLfloat change = 0.005;
		spin = spin - (360/60);
		if (spin > 360.0)
			spin = spin - 360.0;
		glutPostRedisplay();
	}
	/* Set another timer callback to rotate every second */
	glutTimerFunc(1000.0, spinDisplay, 1);
}

/*
 * Handles window resizing input; redraws when resized
 */
void reshape(int w, int h)
{
	glViewport (0, 0, (GLsizei) w, (GLsizei) h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(-50.0, 50.0, -50.0, 50.0, -1.0, 1.0);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
}


/*
 * Handles keyboard input; turns rotation on/off or exits program
 */
void keyboard(unsigned char key, int x, int y) 
{
	switch (key) {
		case 'a':
		case 'A':
			/* toggle spinning flag on */
			spinning = 1;
			break;
			
		case 's':
		case 'S':
			/* toggle spinning flag off */
			spinning = 0;
			break;
			
		case 27: /* ESC key */
			exit(0);
			break;
			
		default:
			break;
	}
	printf("pressed key: %d\n", key);
}

/* 
 *  Request double buffer display mode and.
 *  register input and timer callback functions
 */
int main(int argc, char** argv)
{
	glutInit(&argc, argv);
	glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB);
	glutInitWindowSize (350, 350); 
	glutInitWindowPosition (100, 100);
	glutCreateWindow (argv[0]);
	init ();
	glutDisplayFunc(display); 
	glutReshapeFunc(reshape); 
	glutKeyboardFunc(keyboard);
	/*glutIdleFunc(glutPostRedisplay);*/
	glutTimerFunc(1000.0, spinDisplay, 1);
	glutMainLoop();
	return 0;
}
