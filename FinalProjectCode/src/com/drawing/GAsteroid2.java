package com.drawing;

import com.jogamp.opengl.GL2;

import java.awt.*;

public class GAsteroid2 extends GObstacle {
        private GQuad asteroid;
        private float[] vertex2f;
        private float drawing_width;
        private float disp;
        private boolean left;
        //public GCRect boundary;
        public GCRect boundary;

        GAsteroid2(float[] vertex2f, final GL2 gl, float drawing_width, float disp) {
            this.vertex2f = vertex2f;

            this.asteroid = new GQuad(vertex2f);
            if (vertex2f[0] < 0) {
                this.asteroid.loadTexture(gl, "/World/asteroid.png");
                left = true;
            }
            else {
                this.asteroid.loadTexture(gl, "/World/asteroid.png");
                left = false;
            }
            this.asteroid.setAlpha(true);

            this.drawing_width = drawing_width;
            this.disp = disp;

            float[] boundaryVertex2f = new float[] { .3f, .3f, .3f, this.vertex2f[0] - (3.4f *this.vertex2f[2]), this.vertex2f[1], this.vertex2f[2], this.vertex2f[3] };
            this.boundary = new GCRect(gl, boundaryVertex2f, this.disp, 0f);

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
