/*
 * room.c
 *
 * Demo of opengl lighting.
 *
 * NOTE: To compile, use the attached Makefile, or following command:
 *       `gcc -lGL -lglut pinocchio.c -o pinocchio
 *
 * USAGE: 'o' - turn the spotlight on/off
 *        '+' - increase spotlight cutoff angle by 5.0
 *        '-' - decrease spotlight cutoff angle by 5.0
 *        ESC - exit the demo
 *
 * tim cheadle
 * tcheadle@gmu.edu
 *
 * $Id: room.c,v 1.4 2003-02-06 06:01:28 session Exp $
 */

#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <stdlib.h>

#define PI 3.141592654


static GLint spotlight_on = 1;
static GLfloat spotlight_cutoff = 60.0;

/*
 * Initialize the window buffer
 */
void init(void) 
{
	/* Add an ambient light with a grey color */
	GLfloat light0_specular[]  = { 1.0, 1.0, 1.0, 1.0 };
	GLfloat light0_shininess[] = { 100.0 };
	GLfloat light0_position[]  = { 75.0, 60.0, 50.0, 0.0 };
	GLfloat light0_ambient[]   = { 0.2, 0.2, 0.2, 1.0 };

	/* Add the spotlight */
	GLfloat light1_specular[]  = { 1.0, 1.0, 1.0, 1.0 };
	GLfloat light1_diffuse[]   = { 1.0, 1.0, 1.0, 1.0 };
	GLfloat light1_position[]  = { 40.0, 30.0, 20.0, 1.0 };
	GLfloat light1_ambient[]   = { 0.2, 0.2, 0.2, 1.0 };
	GLfloat spot_direction[]   = { 0.0, 30.0, 50.0 };

	glClearColor (0.0, 0.03, 0.05, 0.0);
	glShadeModel (GL_SMOOTH);

	glLightfv(GL_LIGHT0, GL_POSITION, light0_position);
	glLightfv(GL_LIGHT0, GL_AMBIENT, light0_ambient);
	glLightfv(GL_LIGHT0, GL_SPECULAR, light0_specular);
	glLightfv(GL_LIGHT0, GL_SHININESS, light0_shininess);
	
	glLightfv(GL_LIGHT1, GL_AMBIENT, light1_ambient);
	glLightfv(GL_LIGHT1, GL_DIFFUSE, light1_diffuse);
	glLightfv(GL_LIGHT1, GL_SPECULAR, light1_specular);
	glLightfv(GL_LIGHT1, GL_POSITION, light1_position);


	glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, spotlight_cutoff);
	glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, spot_direction);
	glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 0.0);
	
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
	glEnable(GL_LIGHT1);
	glEnable(GL_DEPTH_TEST);
}

/*
 * Draw the room
 */
void display(void)
{
	GLfloat no_mat[] = { 0.0, 0.0, 0.0, 1.0 };
	
	/* Floor properties */
	GLfloat floor_diffuse[]    = { 0.5, 0.3, 0.3, 1.0 };
	
	/* Wall properties */
	GLfloat wall_diffuse[]     = { 0.8, 0.8, 0.8, 1.0 };
	
	/* Frame properties */
	GLfloat frame_diffuse[]    = { 0.2, 0.2, 0.2, 1.0 };
	
	/* Painting properties */
	GLfloat painting_diffuse[] = { 0.4, 0.5, 0.8, 1.0 };
	
	GLfloat mat_diffuse[] = { 0.4, 0.5, 0.8, 1.0 };
	GLfloat mat_specular[] = { 1.0, 0.5, 1.0, 1.0 };
	GLfloat no_shininess[] = { 0.0 };
	GLfloat low_shininess[] = { 5.0 };
	GLfloat high_shininess[] = { 100.0 };

	/* Clear the output color buffer) */
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	
	/* Floor */
	glPushMatrix();
	/*glMaterialfv(GL_FRONT, GL_AMBIENT, floor_ambient);*/
	glMaterialfv(GL_FRONT, GL_DIFFUSE, floor_diffuse);
	glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
	glMaterialfv(GL_FRONT, GL_SHININESS, high_shininess);
	glMaterialfv(GL_FRONT, GL_EMISSION, no_mat);
	glNormal3f(0.0, 0.0, -1.0);
	glRotatef(90.0, 1.0, 0.0, 0.0);
	glRecti(0, 0, 150, 100);
	glPopMatrix();
	
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_DIFFUSE, painting_diffuse);
	glMaterialfv(GL_FRONT, GL_SHININESS, high_shininess);
	glTranslatef(100.0, 30.0, 50.0);
	glutSolidCube(15.0);
	glPopMatrix();
	
	/* Back wall */
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_DIFFUSE, wall_diffuse);
	glMaterialfv(GL_FRONT, GL_EMISSION, no_mat);
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
	glMaterialfv(GL_FRONT, GL_DIFFUSE, frame_diffuse);
	glRotatef(-90.0, 0.0, 1.0, 0.0);
	glTranslatef(0.0, 0.0, -0.9);
	glNormal3f(0.0, 0.0, -1.0);
	glRecti(25.0, 10.0, 75.0, 50.0);
	glPopMatrix();
	
	/* Painting on left wall */
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_DIFFUSE, painting_diffuse);
	glMaterialfv(GL_FRONT, GL_SHININESS, high_shininess);
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
 * Handles keyboard input; turns spotlight on/off, changes cutoff angle, or exits
 */
void keyboard(unsigned char key, int x, int y) 
{
	switch (key) {
		case '-':
			spotlight_cutoff -= 5.0;
			glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, spotlight_cutoff);
			glutPostRedisplay();
			break;
			
		case '+':
			spotlight_cutoff += 5.0;
			glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, spotlight_cutoff);
			glutPostRedisplay();
			break;
	
		case 'o':
		case 'O': {
			if (spotlight_on) {
				glDisable(GL_LIGHT1);
				spotlight_on = 0;
			} else {
				glEnable(GL_LIGHT1);
				spotlight_on = 1;
			}
			glutPostRedisplay();
			break;
		}
	
		case 27: /* ESC key */
			exit(0);
			break;
			
		default:
			break;
	}
	printf("spotlight cutoff: %2.0f\n", spotlight_cutoff);
}

/* 
 *  Request double buffer display mode and.
 *  register input and timer callback functions
 */
int main(int argc, char** argv)
{
	glutInit(&argc, argv);
	glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB);
	glutInitWindowSize (600, 600); 
	glutInitWindowPosition (100, 100);
	glutCreateWindow (argv[0]);
	init ();
	glutDisplayFunc(display); 
	glutReshapeFunc(reshape); 
	glutKeyboardFunc(keyboard);
	glutMainLoop();
	return 0;
}
