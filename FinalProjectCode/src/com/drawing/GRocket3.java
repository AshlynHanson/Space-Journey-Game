package com.drawing;

import com.jogamp.opengl.GL2;

public class GRocket3 extends GObstacle {
    GQuad rocket;
    float[] vertex2f;
    private float drawing_width;
    private float disp;
    public GCRect boundary;

    GRocket3(float[] vertex2f, final GL2 gl, float drawing_width, float disp) {
        this.vertex2f = vertex2f;

        this.rocket = new GQuad(vertex2f);
        this.rocket.loadTexture(gl, "/World/rocket3.png");
        this.rocket.setAlpha(true);

        this.drawing_width = drawing_width;
        this.disp = disp;

        float[] boundaryVertex2f = new float[] { .3f, .3f, .3f, this.vertex2f[0] - (this.vertex2f[2] /1.5f), this.vertex2f[1], this.vertex2f[2], this.vertex2f[3] };
        this.boundary = new GCRect(gl, boundaryVertex2f, this.disp, 0f);
    }

    /**
     * Renders the Cloud object to the screen
     * @param gl the GL object
     */
    @Override
    public void render(GL2 gl) {
        gl.glPushMatrix();
        gl.glLoadIdentity(); // reset projection matrix

        this.updatePoint(this.disp, this.drawing_width, gl);

        this.rocket.render(gl);

        gl.glPopMatrix();
    }

    public GCRect getBoundary() {
        return this.boundary;
    }

    /**
     * Moves the rocket's position across the screen. If the rocket's position
     * goes above the set max, the rocket starts at the left side of the screen
     * and continues to move forward
     * @param disp the rate that the rocket moves at
     * @param max the screen limit
     * @param gl the GL object
     */
    public void updatePoint(float disp, float max, final GL2 gl) {
        if (this.vertex2f[0] < max) {
            this.vertex2f[0] += disp;
            gl.glTranslatef(this.vertex2f[0], 0.0f, 0.0f);
        }
        else {
            this.vertex2f[0] = -(max);
            gl.glTranslatef(this.vertex2f[0], 0.0f, 0.0f);
        }
    }

}
