package com.drawing;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_LINES;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

// import com.jogamp.opengl.util.gl2.GLUT;

/*
* JOGL 2.0 Program Template (GLCanvas) This is a "Component" which can be added
* into a top-level "Container". It also handles the OpenGL events to render
* graphics.
*/
@SuppressWarnings("serial")
class GLUTCanvas extends GLCanvas implements GLEventListener {

	public static int CANVAS_WIDTH = 1100; // width of the drawable
	public static int CANVAS_HEIGHT = 700; // height of the drawable

	public static final float DRAWING_WIDTH = 550f, DRAWING_HEIGHT = 350f;
	public static float GL_Width, GL_Height;
	// Setup OpenGL Graphics Renderer
	GDrawOrigin myOrigin;
	GKeyBoard keyBoard;
	GMouse mouse;


	boolean keyPressed = false;

	GFireball fireball;
	GFireball fireball2;
	GFireball fireball3;
	GRocket1 rocket1;
	GRocket2 rocket2;
	GRocket3 rocket3;
	GAsteroid asteroid;
	GAsteroid2 asteroid2;
	GAsteroid asteroid3;
	GFlyingSquirrel squirrel;

	GPatch myPatch;
	GSpriteKey spriteCharacter;

	ArrayList<GShape> drawingArtObjects;
	ArrayList<GObstacle> collisionRects;
	ArrayList<GObstacle> winObjects;
	ArrayList<GAcorn> acorns;
	ArrayList<GFlyingSquirrel> squirrels;
	ArrayList<GFireball> fireballs;

	/** Constructor to setup the GUI for this Component */
	public GLUTCanvas(GLCapabilities capabilities, GKeyBoard kb, GMouse mouse) {

		super(capabilities);


		// creating a canvas for drawing
		// GLCanvas canvas = new GLCanvas(capabilities);

		this.addGLEventListener(this);
		this.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		this.keyBoard = kb;
		this.mouse = mouse;
	}

	// ------ Implement methods declared in GLEventListener ------

	/**
	 * 
	 * Called back immediately after the OpenGL context is initialized. Can be used
	 * to perform one-time initialization. Run only once.
	 */
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context

		// ----- Your OpenGL initialization code here -----
		GLU glu = new GLU();

		GL_Width = DRAWING_WIDTH / 2.0f;
		GL_Height = DRAWING_HEIGHT / 2.0f;
		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		// gl.glOrtho(-GL_Width, GL_Width, -GL_Height, GL_Height, -2.0f, 2.0f); // 2D
		glu.gluOrtho2D(-GL_Width, GL_Width, -GL_Height, GL_Height); // canvas

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW); // specify coordinates
		gl.glLoadIdentity(); // reset

		gl.glClearColor(.90f, .90f, 1.0f, 1.0f); // color used to clean the canvas
		gl.glColor3f(1.0f, 1.0f, 1.0f); // drawing color

		// origin
		myOrigin = new GDrawOrigin(GLUTCanvas.GL_Width, GLUTCanvas.GL_Height);

		// learn quad initialization
		float vertex2f[] = new float[]{105f, 0f, 100f, 100f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};


		// patch with texture
		myPatch = new GPatch(-275, -175, 550, 350, 5, "/world/spaceBackground.png");

			// character controlled sprite animation
		spriteCharacter = new GSpriteKey(gl, -20, 50, 100, 60, 30);
		spriteCharacter.selectAnimation(0);

		loadObjectDataFromFile(gl);

		vertex2f = new float[] {
				150f, -120f, 30f, 55f, // position
				1f, 1f, 1f, // color
				0f, 0f, 1f,
		};
		fireball = new GFireball(vertex2f, gl, DRAWING_HEIGHT, 0.5f);

		vertex2f = new float[] {
				30f, 50f, 30f, 55f, // position
				1f, 1f, 1f, // color
				0f, 0f, 1f,
		};
		fireball2 = new GFireball(vertex2f, gl, DRAWING_HEIGHT, 0.8f);

		vertex2f = new float[] {
				-210f, -30f, 30f, 55f, // position
				1f, 1f, 1f, // color
				0f, 0f, 1f,
		};
		fireball3 = new GFireball(vertex2f, gl, DRAWING_HEIGHT, 0.7f);

		vertex2f = new float[] {
				190f, -90f, 30f, 55f, // position
				1f, 1f, 1f, // color
				0f, 0f, 1f,
		};
		rocket1 = new GRocket1(vertex2f, gl, DRAWING_HEIGHT, 1.2f);

		vertex2f = new float[] {
				20f, -90f, 55f, 30f, // position
				1f, 1f, 1f, // color
				0f, 0f, 1f,
		};
		rocket2 = new GRocket2(vertex2f, gl, DRAWING_HEIGHT, 1f);

		vertex2f = new float[] {
				-50f, -20f, 55f, 30f, // position
				1f, 1f, 1f, // color
				0f, 0f, 1f,
		};
		rocket3 = new GRocket3(vertex2f, gl, DRAWING_HEIGHT, 0.9f);

		vertex2f = new float[] {
				-20f, -160f, 55f, 55f, // position
				1f, 1f, 1f, // color
				0f, 0f, 1f,
		};
		asteroid = new GAsteroid(vertex2f, gl, DRAWING_HEIGHT, 0.8f);

		vertex2f = new float[] {
				-120f, 80f, 35f, 35f, // position
				1f, 1f, 1f, // color
				0f, 0f, 1f,
		};
		asteroid2 = new GAsteroid2(vertex2f, gl, DRAWING_HEIGHT, 0.9f);

		vertex2f = new float[] {
				-70f, 120f, 25f, 35f, // position
				1f, 1f, 1f, // color
				0f, 0f, 1f,
		};
		asteroid3 = new GAsteroid(vertex2f, gl, DRAWING_HEIGHT, 0.5f);

		vertex2f = new float[] {
				-100f, 100f, 100f, 100f, // position
				1f, 1f, 1f, // color
				0f, 0f, 1f,
		};
		//screen = new GLoseScreen(vertex2f, gl);

		vertex2f = new float[] {
				-190f, 120f, 35f, 25f, // position
				1f, 1f, 1f, // color
				0f, 0f, 1f,
		};
		squirrel = new GFlyingSquirrel(vertex2f, gl, DRAWING_WIDTH, 0.5f);


		collisionRects = new ArrayList<>();
		collisionRects.add(rocket1);
		collisionRects.add(rocket2);
		collisionRects.add(rocket3);
		collisionRects.add(fireball);

		collisionRects.add(asteroid);
		collisionRects.add(asteroid2);

		winObjects = new ArrayList<>();
		winObjects.add(squirrel);


		// adding them all in the arrayList
		drawingArtObjects = new ArrayList<GShape>();
		drawingArtObjects.add(myPatch);

		drawingArtObjects.add(rocket1);
		drawingArtObjects.add(rocket2);
		drawingArtObjects.add(rocket3);
		drawingArtObjects.add(fireball);

		drawingArtObjects.add(asteroid);

		drawingArtObjects.add(asteroid2);
		drawingArtObjects.add(squirrel);

		drawingArtObjects.add(spriteCharacter);

		spriteCharacter.setCollisionRectangles(collisionRects);
		spriteCharacter.setWinCollisions(winObjects);

	}

	// reads a file and creates GCRect objects based on the data
	public void loadObjectDataFromFile(final GL2 gl) {

		collisionRects = new ArrayList<>();
		squirrels = new ArrayList<>();
		acorns = new ArrayList<>();
		fireballs = new ArrayList<>();

		float[] speeds = {0.5f, 0.5f, 0.5f, 0.5f, 0.5f};
		float[] cloudSpeeds = {0.2f, 0.1f, 0.3f, 0.4f, 0.5f};

		String textPath = "/world/Floaters.txt";
		String texPath = System.getProperty("user.dir") + textPath;
		File myFile = new File(texPath);
		int count = 0;
		try (Scanner myStream = new Scanner(myFile)) {

			float[] vertex2f = new float[]{0f, 1f, 2f, 3f, 4f, 5f, 6f};
			float[] vertex2fSquirrel = new float[]{0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f};
			float[] vertex2fAcorn = new float[]{0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f};
			float[] vertex2fFireball = new float[]{0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f};

			while (myStream.hasNextLine()) {
				String values = myStream.nextLine();
				if (values.equals("[GCRect]")) {
					// read three lines of data
					// first line: 3 color values
					String data = myStream.nextLine();
					int indexSlash = data.indexOf("//");
					String dataVal = data.substring(0, indexSlash);
					String dataElements[] = dataVal.split(",");
					if (dataElements.length > 2) {
						vertex2f[0] = Float.parseFloat(dataElements[0]);
						vertex2f[1] = Float.parseFloat(dataElements[1]);
						vertex2f[2] = Float.parseFloat(dataElements[2]);

					}

					// second line: 2 position values

					data = myStream.nextLine();
					indexSlash = data.indexOf("//");
					dataVal = data.substring(0, indexSlash);
					dataElements = dataVal.split(",");
					if (dataElements.length > 1) {
						vertex2f[3] = Float.parseFloat(dataElements[0]);
						vertex2f[4] = Float.parseFloat(dataElements[1]);
					}

					// third line: 2 dimension values

					data = myStream.nextLine();
					indexSlash = data.indexOf("//");
					dataVal = data.substring(0, indexSlash);
					dataElements = dataVal.split(",");
					if (dataElements.length > 1) {
						vertex2f[5] = Float.parseFloat(dataElements[0]);
						vertex2f[6] = Float.parseFloat(dataElements[1]);
					}

					// reading an empty line
					if (myStream.hasNextLine())
						myStream.nextLine();

					count++;


				}

				if (values.equals("[GFlyingSquirrel]")) {
					// read three lines of data
					// first line: position and scale values
					String data = myStream.nextLine();
					int indexSlash = data.indexOf("//");
					String dataVal = data.substring(0, indexSlash);
					String dataElements[] = dataVal.split(",");
					if (dataElements.length > 2) {
						vertex2fSquirrel[0] = Float.parseFloat(dataElements[0]);
						vertex2fSquirrel[1] = Float.parseFloat(dataElements[1]);
						vertex2fSquirrel[2] = Float.parseFloat(dataElements[2]);
						vertex2fSquirrel[3] = Float.parseFloat(dataElements[3]);

					}

					// second line:

					data = myStream.nextLine();
					indexSlash = data.indexOf("//");
					dataVal = data.substring(0, indexSlash);
					dataElements = dataVal.split(",");
					if (dataElements.length > 1) {
						vertex2fSquirrel[4] = Float.parseFloat(dataElements[0]);
						vertex2fSquirrel[5] = Float.parseFloat(dataElements[1]);
						vertex2fSquirrel[6] = Float.parseFloat(dataElements[2]);
					}

					// third line: 2 dimension values

					data = myStream.nextLine();
					indexSlash = data.indexOf("//");
					dataVal = data.substring(0, indexSlash);
					dataElements = dataVal.split(",");
					if (dataElements.length > 1) {
						vertex2fSquirrel[7] = Float.parseFloat(dataElements[0]);
						vertex2fSquirrel[8] = Float.parseFloat(dataElements[1]);
						vertex2fSquirrel[9] = Float.parseFloat(dataElements[2]);
					}
					// reading an empty line
					if (myStream.hasNextLine())
						myStream.nextLine();
					count++;

					int randomIndex = new Random().nextInt(4 - 0 + 1) + 0;
					GFlyingSquirrel mySquirrel = new GFlyingSquirrel(vertex2fSquirrel, gl, DRAWING_WIDTH, speeds[randomIndex]);
					squirrels.add(mySquirrel);
				}
				if (values.equals("[GAcorn]")) {
					// read three lines of data
					// first line: position and scale values
					String data = myStream.nextLine();
					int indexSlash = data.indexOf("//");
					String dataVal = data.substring(0, indexSlash);
					String dataElements[] = dataVal.split(",");
					if (dataElements.length > 2) {
						vertex2fAcorn[0] = Float.parseFloat(dataElements[0]);
						vertex2fAcorn[1] = Float.parseFloat(dataElements[1]);
						vertex2fAcorn[2] = Float.parseFloat(dataElements[2]);
						vertex2fAcorn[3] = Float.parseFloat(dataElements[3]);

					}

					// second line:

					data = myStream.nextLine();
					indexSlash = data.indexOf("//");
					dataVal = data.substring(0, indexSlash);
					dataElements = dataVal.split(",");
					if (dataElements.length > 1) {
						vertex2fAcorn[4] = Float.parseFloat(dataElements[0]);
						vertex2fAcorn[5] = Float.parseFloat(dataElements[1]);
						vertex2fAcorn[6] = Float.parseFloat(dataElements[2]);
					}

					// third line: 2 dimension values

					data = myStream.nextLine();
					indexSlash = data.indexOf("//");
					dataVal = data.substring(0, indexSlash);
					dataElements = dataVal.split(",");
					if (dataElements.length > 1) {
						vertex2fAcorn[7] = Float.parseFloat(dataElements[0]);
						vertex2fAcorn[8] = Float.parseFloat(dataElements[1]);
						vertex2fAcorn[9] = Float.parseFloat(dataElements[2]);
					}
					// reading an empty line
					if (myStream.hasNextLine())
						myStream.nextLine();
					count++;

					int randomIndex = new Random().nextInt(4 - 0 + 1) + 0;
					GAcorn myAcorn = new GAcorn(vertex2fAcorn, gl, DRAWING_HEIGHT, speeds[randomIndex]);
					acorns.add(myAcorn);
				}
				if (values.equals("[GFireball]")) {
					// read three lines of data
					// first line: position and scale values
					String data = myStream.nextLine();
					int indexSlash = data.indexOf("//");
					String dataVal = data.substring(0, indexSlash);
					String dataElements[] = dataVal.split(",");
					if (dataElements.length > 2) {
						vertex2fFireball[0] = Float.parseFloat(dataElements[0]);
						vertex2fFireball[1] = Float.parseFloat(dataElements[1]);
						vertex2fFireball[2] = Float.parseFloat(dataElements[2]);
						vertex2fFireball[3] = Float.parseFloat(dataElements[3]);

					}

					// second line:

					data = myStream.nextLine();
					indexSlash = data.indexOf("//");
					dataVal = data.substring(0, indexSlash);
					dataElements = dataVal.split(",");
					if (dataElements.length > 1) {
						vertex2fFireball[4] = Float.parseFloat(dataElements[0]);
						vertex2fFireball[5] = Float.parseFloat(dataElements[1]);
						vertex2fFireball[6] = Float.parseFloat(dataElements[2]);
					}

					// third line: 2 dimension values

					data = myStream.nextLine();
					indexSlash = data.indexOf("//");
					dataVal = data.substring(0, indexSlash);
					dataElements = dataVal.split(",");
					if (dataElements.length > 1) {
						vertex2fFireball[7] = Float.parseFloat(dataElements[0]);
						vertex2fFireball[8] = Float.parseFloat(dataElements[1]);
						vertex2fFireball[9] = Float.parseFloat(dataElements[2]);
					}
					// reading an empty line
					if (myStream.hasNextLine())
						myStream.nextLine();
					count++;

					int randomIndex = new Random().nextInt(4 - 0 + 1) + 0;
					GFireball myFireball = new GFireball(vertex2fFireball, gl, DRAWING_HEIGHT, 0.5f);
					fireballs.add(myFireball);
				}
			}

		} catch (Exception e) {
			System.out.println("File reading error");
		}

		if (DrawWindow.DEBUG_OUTPUT)
			System.out.println(count + " objects loaded");

	}


	/**
	 * Call-back handler for window re-size event. Also called when the drawable is
	 * first set to visible.
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL graphics context

		// Have to add this in order for the canvas to properly scale in the window
		// Found at https://forum.jogamp.org/canvas-not-filling-frame-td4040092.html
		double dpiScalingFactorX = ((Graphics2D) getGraphics()).getTransform().getScaleX();
		double dpiScalingFactorY = ((Graphics2D) getGraphics()).getTransform().getScaleY();
		width = (int) (width / dpiScalingFactorX);
		height = (int) (height / dpiScalingFactorY);

		if (DrawWindow.DEBUG_OUTPUT)
		System.out.println(width + ":" + height);

		GLUTCanvas.CANVAS_HEIGHT = height;
		GLUTCanvas.CANVAS_WIDTH = width;

		// we want this aspect ratio in our drawing
		float target_aspect = DRAWING_WIDTH / DRAWING_HEIGHT;

		if (height < 1)
			height = 1;
		// this is the new aspect ratio based on the resize
		float calc_aspect = (float) width / (float) height;

		float aspect = calc_aspect / target_aspect;

		if (calc_aspect >= target_aspect) {
			GL_Width = DRAWING_WIDTH / 2.0f * aspect;
			GL_Height = DRAWING_HEIGHT / 2.0f;
		} else {
			GL_Width = DRAWING_WIDTH / 2.0f;
			GL_Height = DRAWING_HEIGHT / 2.0f / aspect;
		}

		myOrigin.updateOriginVertex(GLUTCanvas.GL_Width, GLUTCanvas.GL_Height);

		GLU glu = new GLU();
		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		// gl.glOrtho(-GL_Width, GL_Width, -GL_Height, GL_Height, -2.0f, 2.0f); // 2D
		glu.gluOrtho2D(-GL_Width, GL_Width, -GL_Height, GL_Height); // canvas

		// gl.glViewport(0, 0, (int) GL_Width * 2, -(int) GL_Height * 2);
		gl.glViewport(-(int) GL_Width, (int) GL_Width, -(int) GL_Height, (int) GL_Height);

		// gl.glEnable(GL2.GL_DEPTH_TEST);
		// gl.glDepthFunc(GL2.GL_LESS);
		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW); // specify coordinates
		gl.glLoadIdentity(); // reset

	}

	/**
	 * Called back by the animator to perform rendering.
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // get the OpenGL 2 graphics context

		gl.glClearColor(.90f, .90f, 1.0f, 1.0f); // color used to clean the canvas
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the canvas with color

		gl.glLoadIdentity(); // reset the model-view matrix

		// myOrigin.render(gl);
		for (GShape artObject : drawingArtObjects) {
			artObject.render(gl);
		}


		for (GObstacle artObject : collisionRects) {
			GCRect boundary = artObject.getBoundary();
			boundary.render(gl);
		}





		//updateCollisions();



		gl.glFlush();
	}

	/**
	 * Called back before the OpenGL context is destroyed. Release resource such as
	 * buffers.
	 */
	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	/**
	 * This function updates drawing based on keyboard events
	 */
	public void processKeyBoardEvents(int key) {
/*
		if (keyBoard.getCharPressed() == 't' && keyBoard.isPressReleaseStatus() == true) {
			this.mousePoints.setDrawingType(GL_TRIANGLES);
		}

		else if (keyBoard.getCharPressed() == 'l' && keyBoard.isPressReleaseStatus() == true) {
			this.mousePoints.setDrawingType(GL_LINES);

		}

		else if (keyBoard.getCharPressed() == 'c' && keyBoard.isPressReleaseStatus() == true) {
			this.mousePoints.setDrawingType(GDrawingPoints.XGL_CIRCLE);
		}

 */

		if (keyBoard.getCharPressed() == 'e' && keyBoard.isPressReleaseStatus() == true) {
			this.keyPressed = true;
		}


		this.spriteCharacter.processKeyBoardEvent(key);

	}

	public void processKeyBoardEventsStop() {
		keyBoard.setPressReleaseStatus(false);
		this.spriteCharacter.resetKeyBoardEvent();
	}

	/**
	 * 
	 */
	public void processMouseEvents()
	{
		if (mouse.isPressReleaseStatus() == true) {
			//mousePoints.addPoint(mouse.getPointClicked());
		}
	}

}