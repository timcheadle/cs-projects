/*
 * pinocchio.c
 *
 * Demo of opengl camera movement routines around a static
 * object (a model of pinocchio).
 *
 * NOTE: To compile, use the attached Makefile, or following command:
 *       `gcc -lGL -lglut pinocchio.c -o pinocchio
 *
 * tim cheadle
 * tcheadle@gmu.edu
 *
 * $Id: pinocchio.c,v 1.5 2002-10-15 06:18:49 session Exp $
 */

#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define PI 3.141592654

static GLint phase = 1; /* Phase of rotation demo
                         *  = 1 -> rotating on X-Z plane
                         *  = 2 -> rotating on Y-Z plane
                         */
static GLint rotating = 0; /* Rotation on/off flag */
static GLfloat angle = 0.0; /* angle of rotation to spin the camera */
static GLfloat up_angle = 0.0; /* angle of rotation to move the camera's up vector */
static GLfloat change = (1.0/360.0) * 2.0 * PI;
static GLfloat radius = 30.0; /* Radius of circles of rotation */
static GLfloat delay = 10.0; /* Delay (in ms) between frame updates */

/*
 * Initialize the window buffer
 */
void init(void) 
{
	glClearColor (0.0, 0.0, 0.0, 0.0);
	glShadeModel (GL_SMOOTH);
}

/*
 * Draw the pinocchio, making the necessary
 * translations and rotations
 */
void display(void)
{
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
	
	/*printf("angle: %f, radius: %f, change: %f, cos: %f, sin: %f\n", angle, radius, change, radius*cos(angle), radius*sin(angle));*/
}

/*
 * Update the angle of rotation for the pinocchio
 * every second if spinning is on
 */
void spinCamera(int temp)
{
	if (rotating) {
		angle = (angle + change);
		if (angle > 2*PI) {
			angle = angle - 2*PI;
			if (phase == 1) {
				phase = 2;
			} else if (phase == 2) {
				phase = 1;
			}
		}
		
		/* now change the up vector */
		up_angle = (angle + change) + (PI/2);
		
		glutPostRedisplay();
	}
	
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(-200.0, 200.0, -200.0, 200.0, -300.0, 300.0);
	if (phase == 1) {
		gluLookAt(radius*sin(angle), 0.0, radius*cos(angle), 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
	} else if (phase == 2) {
		gluLookAt(0.0, radius*sin(angle), radius*cos(angle), 0.0, 0.0, 0.0, 0.0, sin(up_angle), cos(up_angle));
	}
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	
	glutTimerFunc(delay, spinCamera, 1);
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
	if (phase == 1) {
		gluLookAt(radius*sin(angle), 0.0, radius*cos(angle), 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
	} else if (phase == 2) {
		gluLookAt(0.0, radius*sin(angle), radius*cos(angle), 0.0, 0.0, 0.0, 0.0, sin(up_angle), cos(up_angle));
	}
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
}


/*
 * Handles keyboard input; turns rotation on/off or exits program
 */
void keyboard(unsigned char key, int x, int y) 
{
	switch (key) {
		case 's':
		case 'S':
			/* toggle rotating flag on/off */
			rotating = !rotating;
			break;

		case 27: /* ESC key */
			exit(0);
			break;
			
		case 43:
			change *= 1.5;
			break;
		case 45:
			change /= 1.5;
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
	glutInitWindowSize (450, 450); 
	glutInitWindowPosition (100, 100);
	glutCreateWindow (argv[0]);
	init ();
	glutDisplayFunc(display); 
	glutReshapeFunc(reshape); 
	glutKeyboardFunc(keyboard);
/*	glutIdleFunc(spinCamera);*/
	glutTimerFunc(delay, spinCamera, 1);
	glutMainLoop();
	return 0;
}
