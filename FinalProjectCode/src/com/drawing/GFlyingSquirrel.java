package com.drawing;

import com.jogamp.opengl.GL2;

public class GFlyingSquirrel extends GObstacle {
    private GQuad squirrel;
    private float[] vertex2f;
    private float drawing_width;
    private float disp;
    private boolean left;
    public GCRect boundary;

    GFlyingSquirrel(float[] vertex2f, final GL2 gl, float drawing_width, float disp) {
        this.vertex2f = vertex2f;

        this.squirrel = new GQuad(vertex2f);
        if (vertex2f[0] < 0) {
            this.squirrel.loadTexture(gl, "/World/flyingSquirrel.png");
            left = true;
        }
        else {
            this.squirrel.loadTexture(gl, "/World/flyingSquirrelReverse.png");
            left = false;
        }
        this.squirrel.setAlpha(true);

        this.drawing_width = drawing_width;
        this.disp = disp;

        float[] boundaryVertex2f = new float[] { .3f, .3f, .3f, this.vertex2f[0], this.vertex2f[1], this.vertex2f[2], this.vertex2f[3] };
        this.boundary = new GCRect(gl, boundaryVertex2f, 0f, 0f);
    }

    /**
     * Renders the Squirrel object to the screen
     * @param gl the GL object
     */
    @Override
    public void render(GL2 gl) {
        gl.glPushMatrix();
        gl.glLoadIdentity(); // reset projection matrix

        this.squirrel.render(gl);

        gl.glPopMatrix();
    }

    public GCRect getBoundary() {
        return this.boundary;
    }
}
