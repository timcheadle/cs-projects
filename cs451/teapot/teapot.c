/*
 * teapot.c
 *
 * Demo of opengl routines on a 3D teapot.
 *
 * NOTE: To compile, use the attached Makefile, or following command:
 *       `gcc -lGL -lglut teapot.c -o teapot
 *
 * tim cheadle
 * tcheadle@gmu.edu
 *
 * $Id: teapot.c,v 1.1 2003-02-06 05:53:51 session Exp $
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
static GLfloat change = 1.0;
static GLint solid = 1;

/*
 * Initialize the window buffer
 */
void init(void) 
{
	GLfloat mat_specular[] = { 1.0, 1.0, 1.0, 1.0 };
	GLfloat mat_shininess[] = { 100.0 };
	GLfloat light0_position[] = { 10.0, 30.0, 100.0, 0.0 };
	GLfloat light0_ambient[] = { 0.4, 0.25, 0.1, 1.0 };
	glClearColor (0.0, 0.0, 0.0, 0.0);
	glShadeModel (GL_FLAT);

	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);
	glLightfv(GL_LIGHT0, GL_POSITION, light0_position);
	glLightfv(GL_LIGHT0, GL_AMBIENT, light0_ambient);
	
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
	glEnable(GL_DEPTH_TEST);
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
	
	glColor3f(0.3, 0.2, 0.9);
	glPushMatrix();
	glRotatef(spin, 0.2, 1.0, 1.0);
	if (solid) {
		glutSolidTeapot(30.0);
	} else {
		glutWireTeapot(30.0);
	}
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
	glOrtho(-60.0, 60.0, -60.0, 60.0, -100.0, 100.0);
/*	gluPerspective(80.0, 3.0, -40.0, 10.0);*/
	gluLookAt(0.0, 30.0, 60.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
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
			
		case 'w':
		case 'W':
			solid = !solid;
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
