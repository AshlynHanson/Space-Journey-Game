package com.drawing;

import com.jogamp.opengl.GL2;

public class GAcorn implements GShape {
    private GQuad acorn;
    private float[] vertex2f;
    private float drawing_width;
    private float disp;

    GAcorn(float[] vertex2f, final GL2 gl, float drawing_width, float disp) {
        this.vertex2f = vertex2f;
        this.drawing_width = drawing_width;
        this.disp = disp;

        this.acorn = new GQuad(vertex2f);
        this.acorn.loadTexture(gl, "/World/acorn.png");
        this.acorn.setAlpha(true);
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

        this.acorn.render(gl);

        gl.glPopMatrix();
    }

    /**
     * Moves the acorn's position across the screen. If the acorns's position
     * goes below the set max, the acorn starts at the top side of the screen
     * and continues to move down
     * @param disp the rate that the acorn moves at
     * @param max the screen limit
     * @param gl the GL object
     */
    public void updatePoint(float disp, float max, final GL2 gl) {
        if (this.vertex2f[1] > -max) {
            this.vertex2f[1] -= disp;
            gl.glTranslatef(0, this.vertex2f[1], 0.0f);
        }
        else {
            this.vertex2f[1] = max;
            gl.glTranslatef(0f, this.vertex2f[1], 0.0f);
        }
    }
}
