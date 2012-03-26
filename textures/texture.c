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
 * $Id: texture.c,v 1.1 2003-02-06 05:54:14 session Exp $
 */

#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>

#define NUM_TREES 2000
#define TEX_SCALE 50

/* static const GLint NUM_TREES = 20; */

static GLint spotlight_on = 1;
static GLfloat spotlight_cutoff = 35.0;
struct Tree {
	float x;
	float y;
	float z;
} trees[NUM_TREES];
	

/*
 * Initialize the window buffer
 */
void init(void) 
{
	/* Add an ambient light with a grey color */
	GLfloat light0_specular[]  = { 1.0, 1.0, 1.0, 1.0 };
	GLfloat light0_shininess[] = { 100.0 };
	GLfloat light0_position[]  = { 30.0, 60.0, 30.0, 1.0 };
	GLfloat light0_diffuse[] = { 1.0, 0.6, 0.7, 1.0 };
	GLfloat light0_ambient[]   = { 0.8, 0.8, 0.8, 1.0 };

	glClearColor (0.0, 0.03, 0.05, 0.0);
	glShadeModel (GL_SMOOTH);

	glLightfv(GL_LIGHT0, GL_POSITION, light0_position);
	glLightfv(GL_LIGHT0, GL_AMBIENT, light0_ambient);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, light0_diffuse);
	glLightfv(GL_LIGHT0, GL_SPECULAR, light0_specular);
	glLightfv(GL_LIGHT0, GL_SHININESS, light0_shininess);
	
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
	glEnable(GL_DEPTH_TEST);
	glEnable( GL_TEXTURE_2D );

	/* Seed the random table */
	srand(time(NULL));
	
	/* Generate tree locations */
	for(int i=0; i < NUM_TREES; i++) {
		trees[i].x = (rand() % 1600)/10.0 - 100.0;
		trees[i].y = (rand() % 100)/10.0 + 2.0;
		trees[i].z = (rand() % 1600)/10.0 - 100.0;
	}
}


/* load a 256x256 RGB .RAW file as a texture */
GLuint LoadTextureRAW( const char * filename, int wrap )
{
    GLuint texture;
    GLint width, height;
    char *data;
    FILE *file;

    /* open texture data */
    file = fopen( filename, "rb" );
    if ( file == NULL ) return 0;

    /* allocate buffer */
    width = 256;
    height = 256;
    data = malloc( width * height * 3 );

    /* read texture data */
    fread( data, width * height * 3, 1, file );
    fclose( file );

    /* allocate a texture name */
    glGenTextures( 1, &texture );

    /* select our current texture */
    glBindTexture( GL_TEXTURE_2D, texture );

    /* select modulate to mix texture with color for shading */
    glTexEnvf( GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE );

    /* when texture area is small, bilinear filter the closest mipmap */
    glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
                     GL_LINEAR_MIPMAP_NEAREST );
					 
    /* when texture area is large, bilinear filter the first mipmap */
    glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR );

    /* if wrap is true, the texture wraps over at the edges (repeat)
     *     ... false, the texture ends at the edges (clamp)
	 */
    glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap ? GL_REPEAT : GL_CLAMP );
    glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap ? GL_REPEAT : GL_CLAMP );

    /* build our texture mipmaps */
    gluBuild2DMipmaps( GL_TEXTURE_2D, 3, width, height,
                       GL_RGB, GL_UNSIGNED_BYTE, data );

    /* free buffer */
    free( data );

    return texture;
}






/*
 * Draw the room
 */
void display(void)
{
	GLfloat no_mat[] = { 0.0, 0.0, 0.0, 1.0 };
	GLuint texture; 
	
	/* Floor properties */
	GLfloat floor_diffuse[] = { 0.2, 0.5, 0.8, 1.0 };
	GLfloat floor_ambient[] = { 1, 1, 1, 1.0 };
	GLfloat floor_specular[] = { 0.2, 0.5, 0.8, 1.0 };
	GLfloat high_shininess[] = { 100.0 };

	/* Trunk & branch properties */
	GLfloat wood_diffuse[] = { 0.5, 0.7, 0.1, 1.0 };
	GLfloat wood_ambient[] = { 1, 1, 1, 1 };
	GLfloat wood_specular[] = { 0.5, 0.7, 0.1, 1.0 };
	

	/* Clear the output color buffer) */
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	
	/* Get texture */
	texture = LoadTextureRAW("grass-tile.bmp", 1);
	glBindTexture( GL_TEXTURE_2D, texture );

	/* Floor */
	glPushMatrix();
	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, floor_ambient);
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, floor_diffuse);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, floor_specular);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SHININESS, high_shininess);
	glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION, no_mat);
	glBegin(GL_POLYGON);
		glTexCoord2f(TEX_SCALE, TEX_SCALE);
		glNormal3i(0, 1, 0);
		glVertex3i(-100, 0, -100);
		
		glTexCoord2f(TEX_SCALE, 0);
		glNormal3i(0, 1, 0);
		glVertex3i(25, 0, -100);
		
		glTexCoord2f(0, 0);
		glNormal3i(0, 1, 0);
		glVertex3i(25, 0, 25);
		
		glTexCoord2f(0, TEX_SCALE);
		glNormal3i(0, 1, 0);
		glVertex3i(-100, 0, 25);
	glEnd();
	glPopMatrix();

	glPushMatrix();
	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, wood_ambient);
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, wood_diffuse);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, wood_specular);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SHININESS, high_shininess);
	glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION, no_mat);
	glPopMatrix();
	
	for(int i=0; i < NUM_TREES; i++) {
		float x = trees[i].x;
		float y = trees[i].y;
		float z = trees[i].z;
		
		#ifdef DEBUG
		printf("[%2.0d] x, y, z = %0.2f, %2.0f, %0.2f\n", i, x, y, z);
		#endif
	
		glPushMatrix();
		glTranslatef(x, 0.0, z);
		glRotatef(-90.0, 1.0, 0.0, 0.0);
		glutSolidCone(0.3, y, 60, 40);
		glPopMatrix();
	}
	
	glPushMatrix();
	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, wood_ambient);
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, wood_diffuse);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, wood_specular);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SHININESS, high_shininess);
	glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION, no_mat);
	glPopMatrix();
	
	glutSwapBuffers();
	glDeleteTextures( 1, &texture );

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
	gluLookAt(25.0, 10.0, 15.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);	
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
