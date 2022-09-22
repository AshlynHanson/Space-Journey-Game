package com.drawing;

import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_LINE_LOOP;
import static com.jogamp.opengl.GL.GL_TRIANGLE_STRIP;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static com.jogamp.opengl.GL2GL3.GL_LINE;

import java.awt.Point;
import java.util.Arrays;
import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class GCRect implements GShape {

	float vertex2f[];
	float speed = 0.5f;

	final int TEXHEIGHTCORRECTION = 150;
	int texture_height;
	private Texture texID;
	float speedX;
	float speedY;
	boolean alpha;

	GCRect(final GL2 gl, float vertex2f[], float speedX, float speedY) {
		// only color information is used
		this.vertex2f = Arrays.copyOf(vertex2f, vertex2f.length);
		this.speedX = speedX;
		this.speedY = speedY;
		this.vertex2f[3] -= speedX;
		this.vertex2f[4] -= speedY;
		// first 3 elements are color
		// 4th, 5th two elements provides the lower left corner point
		// 6th element is the width
		// 7th element is the height
		reloadThings(gl, false);
	}

	public void setAlpha(boolean alpha) {
		this.alpha = alpha;
	}

	public void loadTexture(final GL2 gl, String path) {
		texID = GTextureUtil.loadTextureProjectDir(gl, path, "PNG");
		texID.setTexParameterf(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		texID.setTexParameterf(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		texID.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_MIRRORED_REPEAT);
		texID.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_MIRRORED_REPEAT);

		// float borderColor[] = { 1.0f, 1.0f, 0.0f, 1.0f };
		// texID.setTexParameterfv(gl, GL2.GL_TEXTURE_BORDER_COLOR, borderColor, 0);

	}

	public void reloadThings(final GL2 gl, boolean resetPosFlag) {
		Random rand = new Random();

		// calculating texture height
		//texture_height = (int) (this.getWidth() / this.getHeight() * TEXHEIGHTCORRECTION);
		final int MIN_DELAY_LENGTH = (int) GLUTCanvas.DRAWING_WIDTH/8 ,
				MAX_DELAY_LENGTH = (int) GLUTCanvas.DRAWING_WIDTH/4;


		// it must be a function of the previous GRect objects y value
		final int MIN_HEIGHT = (int) GLUTCanvas.DRAWING_HEIGHT/16 ,
				MAX_HEIGHT = (int) GLUTCanvas.DRAWING_WIDTH/8;

		if (resetPosFlag == true) {
			// position in the world
			//vertex2f[3] = (int) GLUTCanvas.DRAWING_WIDTH / 2 + MIN_DELAY_LENGTH + rand.nextInt(MAX_DELAY_LENGTH);
			//vertex2f[4] = -GLUTCanvas.DRAWING_HEIGHT / 2 + MIN_HEIGHT + rand.nextInt(MAX_HEIGHT);

		}
		// calculating texture height
		//texture_height = (int) (this.getWidth() / this.getHeight() * TEXHEIGHTCORRECTION);


		// speed of movement
		//final float MIN_SPEED = 0.25f, MAX_SPEED = 1.0f;
		//speed = MIN_SPEED + (float) rand.nextDouble() * MAX_SPEED;

	}

	public float getWidth() {
		return vertex2f[5];
	}

	public float getHeight() {
		return vertex2f[6];
	}

	public float getBaseHeight() {
		return vertex2f[4];
	}

	public float getMiddleHorizontalPoint() {
		return vertex2f[3] + this.getWidth() / 2;
	}

	public Point getLowerLeftPoint()
	{
		Point p = new Point();
		p.x = (int) vertex2f[3]; // 3
		p.y = (int) vertex2f[4]; // 4
		return p;
	}

	public Point getUpperRightPoint() {
		Point p = new Point();
		p.x = (int) (vertex2f[3] + vertex2f[5]); // 3 5
		p.y = (int) (vertex2f[4] + vertex2f[6]); // 4 6
		return p;

	}


	public void render(final GL2 gl) {

		gl.glPushMatrix();

		//vertex2f[3] += speedX;
		//vertex2f[4] += speedY;

		gl.glTranslatef(vertex2f[3], vertex2f[4], 0f);
		gl.glScalef(vertex2f[5], vertex2f[6], 1.0f);

		gl.glColor3fv(vertex2f, 0); // drawing color
		//drawOutline(gl);
		if (vertex2f[3] < 350f) {
			vertex2f[3] += speedX;
		}
		else {
			if (speedX > 0) {
				vertex2f[3] = -350f;
			}
			else {
				vertex2f[3] = 350f;
			}
		}

		/*
		else if (vertex2f[3] > -380f) {
			vertex2f[3] += speedX;
		}
		else if (vertex2f[3] > 380f){
			vertex2f[3] = -380f;
		}
		else if (vertex2f[3] < -380f){
			vertex2f[3] = 380;
		}

		 */


		/*

		if (vertex2f[3] > -GLUTCanvas.DRAWING_WIDTH) {
			vertex2f[3] += speedX;
		}
		else {
			vertex2f[3] = GLUTCanvas.DRAWING_WIDTH;
		}

		 */

		if (vertex2f[4] < GLUTCanvas.DRAWING_HEIGHT) {
			vertex2f[4] += speedY;
		}
		else {
			vertex2f[4] = -(GLUTCanvas.DRAWING_HEIGHT);
		}

		gl.glPopMatrix();
	}

	public void drawOutline(final GL2 gl) {

		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

		gl.glLineWidth(4f);

		gl.glBegin(GL_LINE_LOOP);

		gl.glVertex2f(1f, 0f); // v0 bottom right
		gl.glVertex2f(1f, 1f); // v1 top right
		gl.glVertex2f(0f, 1f); // v3 top left
		gl.glVertex2f(0f, 0f); // v2 bottom left

		gl.glEnd();

		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}

	public void drawFilled(final GL2 gl) {
		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);


		gl.glBegin(GL_TRIANGLE_STRIP);

		gl.glVertex2f(1f, 0f); // v0 bottom right
		gl.glVertex2f(1f, 1f); // v1 top right
		gl.glVertex2f(0f, 0f); // v2 bottom left
		gl.glVertex2f(0f, 1f); // v3 top left

		gl.glEnd();
	}

	// returns whether a given point is inside the rectangle
	boolean isInside(Point p) {
		Point pa = getLowerLeftPoint();
		Point pb = getUpperRightPoint();
		return GCollision.isInside( pa.getX(),  pa.getY(),  pb.getX(), pb.getY(), p.getX(), p.getY());
	}

}
