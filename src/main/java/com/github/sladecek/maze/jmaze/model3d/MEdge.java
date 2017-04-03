package com.github.sladecek.maze.jmaze.model3d;

import com.github.sladecek.maze.jmaze.geometry.Point3D;

/**
 * Edge in a 3D model. An edge connects two points.
 */
public class MEdge {
    public MPoint getP1() {
        return p1;
    }

    public MEdge(MPoint p1, MPoint p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public MPoint getP2() {
        return p2;
    }

    public MEdge(Point3D p1, Point3D p2) {
        this.p1 = new MPoint(p1);
        this.p2 = new MPoint(p2);
    }

    private final MPoint p1;
    private final MPoint p2;
}
