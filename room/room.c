/*
 * room.c
 *
 * Demo of opengl camera movement routines around a static
 * object (a model of pinocchio).
 *
 * NOTE: To compile, use the attached Makefile, or following command:
 *       `gcc -lGL -lglut pinocchio.c -o pinocchio
 *
 * USAGE: 's' - start/stop the demo (turns rotation on/off)
 *        '+' - increase rotation speed by 1.5x
 *        '-' - decrease the rotation speed by 1.5x
 *        ESC - exit the demo
 *
 * tim cheadle
 * tcheadle@gmu.edu
 *
 * $Id: room.c,v 1.3 2002-11-22 04:14:16 session Exp $
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
static GLfloat change = (1.0/360.0) * 2.0 * PI; /* amount to increment the rotation angle each frame */
static GLfloat radius = 30.0; /* Radius of circles of rotation */
static GLfloat delay = 10.0; /* Delay (in ms) between frame updates */

/*
 * Initialize the window buffer
 */
void init(void) 
{
	/* Add a positional light with a greenish-blue color */
	GLfloat mat_specular[] = { 1.0, 1.0, 1.0, 1.0 };
	GLfloat mat_shininess[] = { 100.0 };
	GLfloat light0_position[] = { 150.0, 120.0, 100.0, 1.0 };
	GLfloat light0_ambient[] = { 0.6, 0.6, 0.6, 1.0 };

	glClearColor (0.0, 0.03, 0.05, 0.0);
	glShadeModel (GL_SMOOTH);

	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);
	glLightfv(GL_LIGHT0, GL_POSITION, light0_position);
	glLightfv(GL_LIGHT0, GL_AMBIENT, light0_ambient);
	
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
	glEnable(GL_DEPTH_TEST);
}

/*
 * Draw the pinocchio, making the necessary
 * translations and rotations
 */
void display(void)
{
	GLfloat no_mat[] = { 0.0, 0.0, 0.0, 1.0 };
	
	/* Floor properties */
	GLfloat floor_ambient[] = { 0.9, 0.6, 0.6, 1.0 };
	
	/* Wall properties */
	GLfloat wall_diffuse[]  = { 0.8, 0.8, 0.8, 1.0 };
	
	/* Frame properties */
	GLfloat frame_diffuse[] = { 0.2, 0.2, 0.2 };
	
	GLfloat mat_ambient[] = { 0.7, 0.7, 0.7, 1.0 };
	GLfloat mat_ambient_color[] = { 0.8, 0.8, 0.2, 1.0 };
	GLfloat floor_diffuse[] = { 0.6, 0.2, 0.3, 1.0 };
	GLfloat mat_diffuse[] = { 0.4, 0.5, 0.8, 1.0 };
	GLfloat mat_specular[] = { 1.0, 1.0, 1.0, 1.0 };
	GLfloat no_shininess[] = { 0.0 };
	GLfloat low_shininess[] = { 5.0 };
	GLfloat high_shininess[] = { 100.0 };
	GLfloat mat_emission[] = {0.3, 0.2, 0.2, 0.0};

	/* Clear the output color buffer) */
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	
	/* Floor */
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT, floor_ambient);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, no_mat);
	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, low_shininess);
	glMaterialfv(GL_FRONT, GL_EMISSION, no_mat);
	glNormal3f(0.0, 0.0, -1.0);
	glRotatef(90.0, 1.0, 0.0, 0.0);
	glRecti(0, 0, 150, 100);
	glPopMatrix();
	
	/* Back wall */
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT, no_mat);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, wall_diffuse);
	glNormal3f(0.0, 0.0, 1.0);
	glRecti(0, 0, 150, 60);
	glPopMatrix();
	
	/* Left wall */
	glPushMatrix();
	glNormal3f(0.0, 0.0, -1.0);
	glRotatef(-90.0, 0.0, 1.0, 0.0);
	glRecti(0, 0, 100, 60);
	glPopMatrix();
	
	/* Painting frame */
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT, no_mat);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, frame_diffuse);
	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, low_shininess);
	glMaterialfv(GL_FRONT, GL_EMISSION, no_mat);
	glRotatef(-90.0, 0.0, 1.0, 0.0);
	glTranslatef(0.0, 0.0, -0.9);
	glNormal3f(0.0, 0.0, -1.0);
	glRecti(25.0, 10.0, 75.0, 50.0);
	glPopMatrix();
	
	/* Painting on left wall */
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT, no_mat);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_diffuse);
	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, high_shininess);
	glMaterialfv(GL_FRONT, GL_EMISSION, no_mat);
	glRotatef(-90.0, 0.0, 1.0, 0.0);
	glTranslatef(0.0, 0.0, -1.0);
	glNormal3f(0.0, 0.0, -1.0);
	glRecti(30.0, 15.0, 70.0, 45.0);
	glPopMatrix();
	
	glutSwapBuffers();
}

/*
 * Handles window resizing input; redraws when resized
 */
void reshape(int w, int h)
{
	glViewport (0, 0, (GLsizei) w, (GLsizei) h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(60.0, 1.0, 10.0, 400.0);
	gluLookAt(250.0, 100.0, 150.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);	
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
}


/*
 * Handles keyboard input; turns rotation on/off or exits program
 */
void keyboard(unsigned char key, int x, int y) 
{
	switch (key) {
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
	glutInitWindowSize (450, 450); 
	glutInitWindowPosition (100, 100);
	glutCreateWindow (argv[0]);
	init ();
	glutDisplayFunc(display); 
	glutReshapeFunc(reshape); 
	glutKeyboardFunc(keyboard);
	glutMainLoop();
	return 0;
}
