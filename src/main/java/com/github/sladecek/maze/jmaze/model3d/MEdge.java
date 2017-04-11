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

    private final MPoint p1;
    private final MPoint p2;

    private MFace leftFace;
    private MFace rightFace;

    public MFace getLeftFace() {
        return leftFace;
    }

    public void setLeftFace(MFace leftFace) {
        assert this.leftFace == null;
        this.leftFace = leftFace;
    }

    public MFace getRightFace() {
        return rightFace;
    }

    public void setRightFace(MFace rightFace) {
        assert this.rightFace == null;
        this.rightFace = rightFace;
    }
}
