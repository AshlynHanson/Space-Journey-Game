package com.drawing;

import com.jogamp.opengl.GL2;

public class GObstacle implements GShape {
    GCRect boundary;

    GObstacle() {

    }

    public GCRect getBoundary() {
        return this.boundary;
    }

    public void render(GL2 gl) {

    }
}
