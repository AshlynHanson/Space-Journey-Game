package com.drawing;

import com.jogamp.opengl.GL2;

import java.awt.*;

public class GAsteroid extends GObstacle {
    private GQuad asteroid;
    private float[] vertex2f;
    private float drawing_width;
    private float disp;


    public GCRect boundary;

    GAsteroid(float[] vertex2f, final GL2 gl, float drawing_width, float disp) {
        this.vertex2f = vertex2f;

        this.asteroid = new GQuad(vertex2f);
        this.asteroid.loadTexture(gl, "/World/asteroid.png");
        this.asteroid.setAlpha(true);

        this.drawing_width = drawing_width;
        this.disp = disp;

        float[] boundaryVertex2f = new float[] { .3f, .3f, .3f, this.vertex2f[0], this.vertex2f[1] - (2.85f * this.vertex2f[3]), this.vertex2f[2], this.vertex2f[3] };
        this.boundary = new GCRect(gl, boundaryVertex2f, 0f, this.disp );

    }

    /**
     * Renders the Asteroid object to the screen
     * @param gl the GL object
     */
    @Override
    public void render(GL2 gl) {
        gl.glPushMatrix();
        gl.glLoadIdentity(); // reset projection matrix

        updatePoint(disp, this.drawing_width, gl);

        this.asteroid.render(gl);

        gl.glPopMatrix();
    }


    public GCRect getBoundary() {
        return this.boundary;
    }


    public void updatePoint(float disp, float max, final GL2 gl) {
        int index = 1;
        if (this.vertex2f[index] < max ) {
            this.vertex2f[index] += disp;
            gl.glTranslatef(0f, this.vertex2f[index], 0.0f);
        }
        else {
            this.vertex2f[index] = -(max);
            gl.glTranslatef(0f, this.vertex2f[index], 0.0f);
        }
    }


}
