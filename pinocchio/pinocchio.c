/*
 * pinnochio.c
 *
 * Demo of opengl camera movement routines around a static
 * object (a model of pinnochio).
 *
 * NOTE: To compile, use the attached Makefile, or following command:
 *       `gcc -lGL -lglut teapot.c -o teapot
 *
 * tim cheadle
 * tcheadle@gmu.edu
 *
 * $Id: pinocchio.c,v 1.2 2002-10-13 22:52:51 session Exp $
 */

#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define PI 3.141592654

static GLint spinning = 0; /* clock hand spinning on/off flag */
static GLfloat spin = 0.0; /* angle of rotation to spin the teapot */
static GLfloat red   = 0.3, green = 0.9, blue  = 0.3; /* color of polygons */
static GLfloat change = 0.1;

/*
 * Initialize the window buffer
 */
void init(void) 
{
	glClearColor (0.0, 0.0, 0.0, 0.0);
	glShadeModel (GL_SMOOTH);
}

/*
 * Draw the teapot, making the necessary
 * translations and rotations
 */
void display(void)
{
	GLfloat angle = 0;
	int i;
	
	/* Clear the output color buffer) */
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	
	/* Hat */
	glColor3f(0.3, 0.2, 0.9);
	glPushMatrix();
	glTranslatef(0.0, 75.0, 0.0);
	glRotatef(-90.0, 1.0, 0.0, 0.0);
	glutWireCone(20.0, 55.0, 30.0, 30.0);
	glPopMatrix();
	
	/* Head */
	glColor3f(0.3, 0.2, 0.9);
	glPushMatrix();
	glTranslatef(0.0, 60.0, 0.0);
	glutWireSphere(20.0, 30.0, 30.0);
	glPopMatrix();
	
	/* Body */
	glColor3f(0.3, 0.2, 0.9);
	glPushMatrix();
	glRotatef(spin, 0.2, 1.0, 1.0);
	glScalef(40.0, 80.0, 20.0);
	glutWireCube(1.0);
	glPopMatrix();

	/* Left Leg */
	glColor3f(0.3, 0.2, 0.9);
	glPushMatrix();
	glTranslatef(-15.0, -55.0, 15.0);
	glRotatef(-45.0, 1.0, 0.0, 0.0);
	glScalef(10.0, 60.0, 10.0);
	glutWireCube(1.0);
	glPopMatrix();
	
	/* Right Leg */
	glColor3f(0.3, 0.2, 0.9);
	glPushMatrix();
	glTranslatef(15.0, -55.0, 15.0);
	glRotatef(-45.0, 1.0, 0.0, 0.0);
	glScalef(10.0, 60.0, 10.0);
	glutWireCube(1.0);
	glPopMatrix();
	
	/* Left Arm */
	glColor3f(0.3, 0.2, 0.9);
	glPushMatrix();
	glTranslatef(-35.0, 0.0, 10.0);
	glRotatef(30.0, 0.0, 1.0, 0.0);
	glScalef(40.0, 10.0, 10.0);
	glutWireCube(1.0);
	glPopMatrix();
	
	/* Right Arm */
	glColor3f(0.3, 0.2, 0.9);
	glPushMatrix();
	glTranslatef(35.0, 0.0, 10.0);
	glRotatef(-30.0, 0.0, 1.0, 0.0);
	glScalef(40.0, 10.0, 10.0);
	glutWireCube(1.0);
	glPopMatrix();
	
	
	
	glutSwapBuffers();
}

/*
 * Update the angle of rotation for the teapot
 * every second if spinning is on
 */
void spinDisplay(void)
{
	/* If the spinning flag is on, rotate clockwise by an angle (2 * PI)/60 radians */
	if (spinning) {
		spin = spin - change; /*(360/60);*/
		if (spin > 360.0)
			spin = spin - 360.0;
		glutPostRedisplay();
	}
}

/*
 * Handles window resizing input; redraws when resized
 */
void reshape(int w, int h)
{
	glViewport (0, 0, (GLsizei) w, (GLsizei) h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(-200.0, 200.0, -200.0, 200.0, -300.0, 300.0);
	gluLookAt(90.0, 30.0, 60.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
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
			spinning = !spinning;
			break;
			
		case 27: /* ESC key */
			exit(0);
			break;
			
		case 'f':
		case 'F':
			glShadeModel(GL_FLAT);
			break;
			
		case 's':
		case 'S':
			glShadeModel(GL_SMOOTH);
			break;
			
		case 43:
			change += 1.0;
			break;
		case 45:
			change -= 1.0;
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
	glutIdleFunc(spinDisplay);
	glutMainLoop();
	return 0;
}
