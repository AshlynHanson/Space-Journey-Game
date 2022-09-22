package com.drawing;

import com.jogamp.opengl.GL2;

public class GFireball extends GObstacle {
    private GQuad fireball;
    private float[] vertex2f;
    private float drawing_width;
    private float disp;
    public GCRect boundary;

    GFireball(float[] vertex2f, final GL2 gl, float drawing_width, float disp) {
        this.vertex2f = vertex2f;
        this.drawing_width = drawing_width;
        this.disp = disp;

        this.fireball = new GQuad(vertex2f);
        this.fireball.loadTexture(gl, "/World/fireball2.png");
        this.fireball.setAlpha(true);

        float[] boundaryVertex2f = new float[] { .3f, .3f, .3f, this.vertex2f[0], this.vertex2f[1] - (2.2f * this.vertex2f[3]), this.vertex2f[2], this.vertex2f[3] };
        this.boundary = new GCRect(gl, boundaryVertex2f, 0f, this.disp);
    }

    public GCRect getBoundary() {
        return this.boundary;
    }

    /**
     * Renders the Acorn object to the screen
     * @param gl the GL object
     */
    @Override
    public void render(GL2 gl) {
        gl.glPushMatrix();
        gl.glLoadIdentity(); // reset projection matrix

        this.updatePoint(this.disp, this.drawing_width, gl);

        this.fireball.render(gl);

        gl.glPopMatrix();
    }

    /**
     * Moves the fireball's position across the screen. If the fireball's position
     * goes above the set max, the fireball starts at the bottom side of the screen
     * and continues to move up
     * @param disp the rate that the fireball moves at
     * @param max the screen limit
     * @param gl the GL object
     */
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
