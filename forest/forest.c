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
 * $Id: forest.c,v 1.3 2002-12-08 02:27:44 session Exp $
 */

#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>

#define NUM_TREES 3000
#define TEX_SCALE 50

static struct Tree {
	float x;
	float y;
	float z;
	float r;
} trees[NUM_TREES];


/* Textures */
static GLuint texture, texture2; 


/*
 * Light properties
 */
static GLfloat sun_x = 30.0;
static GLfloat sun_y = 25.0;
static GLfloat sun_z = 10.0;
 
static GLfloat light0_specular[]  = { 1.0, 1.0, 1.0, 1.0 };
static GLfloat light0_shininess[] = { 100.0 };
static GLfloat light0_position[]  = { 30.0, 25.0, 10.0, 1.0 };
static GLfloat light0_diffuse[] = { 0.8, 0.6, 0.6, 1.0 };
static GLfloat light0_ambient[]   = { 0.4, 0.4, 0.4, 1.0 };
static GLfloat light0_direction[] = { 0.0, -1.0, 0.0 };


/*
 * Materials properties
 */
static GLfloat no_mat[] = { 0.0, 0.0, 0.0, 1.0 };
 
/* Ground properties */
static GLfloat floor_diffuse[] = { 0.2, 0.5, 0.8, 1.0 };
static GLfloat floor_ambient[] = { 1, 1, 1, 1.0 };
static GLfloat floor_specular[] = { 0.2, 0.5, 0.8, 1.0 };
static GLfloat high_shininess[] = { 100.0 };

/* Trunk & branch properties */
static GLfloat wood_diffuse[] = { 0.4, 0.8, 0.2, 1.0 };
static GLfloat wood_ambient[] = { 0.4, 0.8, 0.2, 1.0 };
static GLfloat wood_specular[] = { 0.5, 0.7, 0.1, 1.0 };
	
/* Sun sphere properties */
static GLfloat sun_diffuse[] = { 1.0, 1.0, 1.0, 1.0 };
static GLfloat sun_ambient[] = { 0.0, 0.0, 0.0, 1.0 };
static GLfloat sun_specular[] = { 1.0, 1.0, 1.0, 1.0 };
static GLfloat sun_emission[] = { 0.3, 0.3, 0.3, 1.0 };



void generateTrees() {
	/* Generate tree locations */
	for(int i=0; i < NUM_TREES; i++) {
		trees[i].x = (rand() % 1500)/10.0 - 100.0;
		trees[i].y = (rand() % 100)/10.0 + 2.0;
		trees[i].z = (rand() % 1500)/10.0 - 100.0;
		trees[i].r = (rand() % 60)/100.0 + 0.1;
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



void loadTextures() {
	texture = LoadTextureRAW("grass-tile.bmp", 1);
	texture2 = LoadTextureRAW("gravel-tile.bmp", 1);
}



void drawGround() {
	glBindTexture( GL_TEXTURE_2D, texture );

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
		glVertex3i(50, 0, -100);
		
		glTexCoord2f(0, 0);
		glNormal3i(0, 1, 0);
		glVertex3i(50, 0, 50);
		
		glTexCoord2f(0, TEX_SCALE);
		glNormal3i(0, 1, 0);
		glVertex3i(-100, 0, 50);
	glEnd();
	glPopMatrix();
}


void drawTrees() {
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
		float r = trees[i].r;
		
		#ifdef DEBUG
		printf("[%2.0d] x, y, z = %0.2f, %2.0f, %0.2f\n", i, x, y, z);
		#endif
	
		glPushMatrix();
		glTranslatef(x, 0.0, z);
		glRotatef(-90.0, 1.0, 0.0, 0.0);
		glutSolidCone(r, y, 10, 1);
		glPopMatrix();
	}
}



void drawSun() {
	GLUquadricObj *q = gluNewQuadric();
	gluQuadricNormals(q, GL_SMOOTH);					// Generate Smooth Normals For The Quad
	gluQuadricTexture(q, GL_TRUE);						// Enable Texture Coords For The Quad
	glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);			// Set Up Sphere Mapping
	glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);			// Set Up Sphere Mapping
	glBindTexture( GL_TEXTURE_2D, texture2 );
	
	glPushMatrix();
	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, sun_ambient);
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, sun_diffuse);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, sun_specular);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SHININESS, high_shininess);
	glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION, sun_emission);
	glTranslatef(sun_x, sun_y + 5.0, sun_z);
	gluSphere(q, 5.0, 60, 60); 
	glPopMatrix();
	
	/* Reset light position */
	light0_position[0] = sun_x;
	light0_position[1] = sun_y;
	light0_position[2] = sun_z;
	
	glDisable(GL_TEXTURE_GEN_S);						// Disable Sphere Mapping
	glDisable(GL_TEXTURE_GEN_T);						// Disable Sphere Mapping
}





/*
 * Initialize the window buffer
 */
void init(void) 
{
	/* Add an ambient light with a grey color */


	glClearColor (0.0, 0.03, 0.05, 0.0);
	glShadeModel (GL_SMOOTH);

	glLightfv(GL_LIGHT0, GL_POSITION, light0_position);
	glLightfv(GL_LIGHT0, GL_AMBIENT, light0_ambient);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, light0_diffuse);
	glLightfv(GL_LIGHT0, GL_SPECULAR, light0_specular);
	glLightfv(GL_LIGHT0, GL_SHININESS, light0_shininess);
	glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, light0_direction);
	
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
	glEnable(GL_DEPTH_TEST);
	glEnable( GL_TEXTURE_2D );

	/* Seed the random table */
	srand(time(NULL));
	
	generateTrees();
}


/*
 * Draw the room
 */
void display(void)
{
	/* Clear the output color buffer) */
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	
	loadTextures();
	
	drawGround();
	drawTrees();
	drawSun();
	
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
	gluPerspective(60.0, 1.5, 1.0, 400.0);
	gluLookAt(35.0, 35.0, 35.0, 0.0, 20.0, 0.0, 0.0, 1.0, 0.0);	
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
}


/*
 * Handles keyboard input; turns spotlight on/off, changes cutoff angle, or exits
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
}

/* 
 *  Request double buffer display mode and.
 *  register input and timer callback functions
 */
int main(int argc, char** argv)
{
	glutInit(&argc, argv);
	glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB);
	glutInitWindowSize (600, 400); 
	glutInitWindowPosition (100, 100);
	glutCreateWindow (argv[0]);
	init ();
	glutDisplayFunc(display); 
	glutReshapeFunc(reshape); 
	glutKeyboardFunc(keyboard);
	glutMainLoop();
	return 0;
}
