/*
 * forest.c
 *
 * Demo of opengl lighting, fog and texture mapping.  Simulates a
 * forest with sun rotation.
 *
 * NOTE: To compile, use the attached Makefile, or following command:
 *       `gcc -lGL -lglut forest.c -o forest`
 *
 * USAGE: 'o' - turn the spotlight on/off
 *        '+' - increase spotlight cutoff angle by 5.0
 *        '-' - decrease spotlight cutoff angle by 5.0
 *        ESC - exit the demo
 *
 * tim cheadle
 * tcheadle@gmu.edu
 *
 * $Id: forest.c,v 1.8 2002-12-09 04:40:49 session Exp $
 */

#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>

#define NUM_TREES 3000
#define TEX_SCALE 1
#define PI 3.141592654
#define WIDTH 60

static struct Tree {
	float x;
	float y;
	float z;
	float r;
} trees[NUM_TREES];

static char *filename;
static GLint spinning = 1;
static GLfloat spin = 0.0; /* Angle of sun rotation */
static GLfloat delay = 1.0; /* Delay (in ms) between frame updates */


/* Textures */
static GLuint texture, texture2; 

/* Fog density */
static GLfloat density = 0.01;

/*
 * Light properties
 */
static GLfloat sun_x = 0.0;
static GLfloat sun_y = WIDTH;
static GLfloat sun_z = 0.0;
 
static GLfloat light0_specular[]  = { 1.0, 1.0, 1.0, 1.0 };
static GLfloat light0_shininess[] = { 100.0 };
static GLfloat light0_position[]  = { 0.0, WIDTH, 0.0, 1.0 };
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
static GLfloat wood_diffuse[] = { 0.45, 0.85, 0.25, 1.0 };
static GLfloat wood_ambient[] = { 0.45, 0.85, 0.25, 1.0 };
static GLfloat wood_specular[] = { 0.5, 0.7, 0.1, 1.0 };
	
/* Sun sphere properties */
static GLfloat sun_diffuse[] = { 1.0, 1.0, 1.0, 1.0 };
static GLfloat sun_ambient[] = { 0.0, 0.0, 0.0, 1.0 };
static GLfloat sun_specular[] = { 1.0, 1.0, 1.0, 1.0 };
static GLfloat sun_emission[] = { 1.0, 1.0, 0.6, 1.0 };


/*
 * Loads the tree data structure from a file
 */
void loadTrees() {
	FILE *file;
	
	#ifdef DEBUG
	printf("opening %s\n", filename);
	#endif
	
	if ((file = fopen(filename, "r")) == NULL)
           fprintf(stderr, "Cannot open %s\n", "output_file");
	
	int i=0;
	while(!feof(file)) {
		fscanf(file, "%f,%f,%f,%f\n", &trees[i].x, &trees[i].y, &trees[i].z, &trees[i].r);
		#ifdef DEBUG
		printf("%f,%f,%f,%f\n", trees[i].x, trees[i].y, trees[i].z, trees[i].r);
		#endif
		i++;
	}
	
	fclose(file);
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
 * Loads the required textures
 */
void loadTextures() {
	texture = LoadTextureRAW("grass-tile.bmp", 1);
	/*texture2 = LoadTextureRAW("gravel-tile.bmp", 1);*/
}


/*
 * Draws the ground of the forest with texturing
 */
void drawGround() {
	glBindTexture( GL_TEXTURE_2D, texture );

	glPushMatrix();
	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, floor_ambient);
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, floor_diffuse);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, floor_specular);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SHININESS, high_shininess);
	glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION, no_mat);
	glPopMatrix();
	
	for(int x = -WIDTH; x < WIDTH; x+=5) {
		for(int z = -WIDTH; z < WIDTH; z+=5) {
			glPushMatrix();
			glBegin(GL_POLYGON);
				glTexCoord2f(TEX_SCALE, TEX_SCALE);
				glNormal3i(0, 1, 0);
				glVertex3i(x, 0, z);
			
				glTexCoord2f(TEX_SCALE, 0);
				glNormal3i(0, 1, 0);
				glVertex3i(x+5, 0, z);
			
				glTexCoord2f(0, 0);
				glNormal3i(0, 1, 0);
				glVertex3i(x+5, 0, z+5);
		
				glTexCoord2f(0, TEX_SCALE);
				glNormal3i(0, 1, 0);
				glVertex3i(x, 0, z+5);
			glEnd();
			glPopMatrix();
		}
	}
}

/*
 * Draws the trees of the forest
 */
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

/*
 * Draws and rotates the sun
 */
void drawSun() {
	GLUquadricObj *q = gluNewQuadric();
	gluQuadricNormals(q, GL_SMOOTH);					// Generate Smooth Normals For The Quad
	gluQuadricTexture(q, GL_TRUE);						// Enable Texture Coords For The Quad
	glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);			// Set Up Sphere Mapping
	glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);			// Set Up Sphere Mapping
	glBindTexture( GL_TEXTURE_2D, texture2 );
	
	sun_x = light0_position[0];
	sun_y = light0_position[1];
	sun_z = light0_position[2];
	
	/*
	 * Make sun lighter/darker for sunset/sunrise
	 */
	if (spin >= 0.0 && spin <= 90.0) {
		GLfloat percent = ((90.0 - spin) / 90.0) + 0.4;
		if (percent > 1.0) percent = 1.0;
		
		sun_emission[0] = percent;
		sun_emission[1] = percent * 0.9;
		sun_emission[2] = percent - 0.6;
	}
	
	if (spin >= 270.0 && spin <= 360.0) {
		GLfloat percent = ((spin - 270.0) / 90.0) + 0.4;
		if (percent > 1.0) percent = 1.0;
		
		sun_emission[0] = percent;
		sun_emission[1] = percent * 0.9;
		sun_emission[2] = percent - 0.6;
	}
	
	glPushMatrix();
	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, sun_ambient);
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, sun_diffuse);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, sun_specular);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SHININESS, high_shininess);
	glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION, sun_emission);
	glRotatef(spin, -1.0, 0.0, 0.0);
	glTranslatef(sun_x, sun_y + 5.0, sun_z);
	gluSphere(q, 5.0, 60, 60); 
	glPopMatrix();
	
	
	/* Move light position */
	glPushMatrix();
	glRotatef(spin, -1.0, 0.0, 0.0);
	glLightfv(GL_LIGHT0, GL_POSITION, light0_position);
	glPopMatrix();
		
	
	glDisable(GL_TEXTURE_GEN_S);						// Disable Sphere Mapping
	glDisable(GL_TEXTURE_GEN_T);						// Disable Sphere Mapping
}


/*
 * Draws the fog
 */
void drawFog() {
	GLfloat fogColor[4] = {0.5, 0.5, 0.5, 1.0};

	glFogi (GL_FOG_MODE, GL_EXP);
	glFogfv (GL_FOG_COLOR, fogColor);
	glFogf (GL_FOG_DENSITY, density);
	glHint (GL_FOG_HINT, GL_DONT_CARE);
	glFogf (GL_FOG_START, 1.0);
	glFogf (GL_FOG_END, 5.0);
}


/* 
 * Update the angle of spin
 */
void updateSpin(int tmp) {
	/* If the spinning flag is on, rotate clockwise by an angle (2 * PI)/60 radians */
	if (spinning) {
		GLfloat change = 2.5;
		spin = spin + change;
		if (spin > 360.0)
			spin = spin - 360.0;
		glutPostRedisplay();
	}
	/* Set another timer callback to rotate every second */
	glutTimerFunc(delay, updateSpin, 1);
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
	glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 80.0);
	glLightf(GL_LIGHT0, GL_SPOT_EXPONENT, 2.5);
	
	glEnable(GL_FOG);
	
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
	glEnable(GL_DEPTH_TEST);
	glEnable( GL_TEXTURE_2D );

	/* Seed the random table */
	srand(time(NULL));
	
	//generateTrees();
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
	drawFog();
	
	#ifdef DEBUG
	printf("spin: %f\n", spin);
	#endif
	
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
	gluLookAt(WIDTH, 25.0, WIDTH, 0.0, 20.0, 0.0, 0.0, 1.0, 0.0);	
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
}


/*
 * Handles keyboard input
 */
void keyboard(unsigned char key, int x, int y) 
{
	switch (key) {
		case 13: /* Enter key to reload file */
			printf("Reloading file...\n");
			loadTrees();
			printf("Redrawing scene...\n");
			display();
			break;
	
		case 27: /* ESC key */
			exit(0);
			break;
			
		case '-':	
			density -= 0.005;
			display();
			break;
		
		case '+':	
			density += 0.005;
			display();
			break;
		
		case 's':
		case 'S': /* Turn spinning on/off */
			if (spinning)
				spinning = 0;
			else
				spinning = 1;
			break;
			
		default:
			break;
	}
}

/* 
 *  Request double buffer display mode and.
 *  register input and timer callback functions
 */
int main(int argc, char **argv)
{
	glutInit(&argc, argv);
	glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB);
	glutInitWindowSize (600, 400); 
	glutInitWindowPosition (100, 100);
	glutCreateWindow (argv[0]);
	
	if (argc != 2) {
		printf("Usage: %s <filename>\n", argv[0]);
		exit(1);
	}
	
	filename = argv[1];
	loadTrees();
	
	init ();
	glutDisplayFunc(display); 
	glutReshapeFunc(reshape); 
	glutKeyboardFunc(keyboard);
	glutTimerFunc(delay, updateSpin, 1);
	glutMainLoop();
	return 0;
}
