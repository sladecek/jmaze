package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;

/**
 * Edge in a 3D model. An edge connects two points.
 */
public class MEdge {

    public MEdge(MPoint p1, MPoint p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public MPoint getP1() {
        return p1;
    }

    public MPoint getP2() {
        return p2;
    }

    public MFace getLeftFace() {
        return leftFace;
    }

    /**
     * Left face setter. Can be set only once.
     */
    public void setLeftFace(MFace leftFace) {
        assert this.leftFace == null;
        this.leftFace = leftFace;
    }

    public MFace getRightFace() {
        return rightFace;
    }

    public void setRightFace(MFace rightFace) {
  // TODO odkomentovat assert      assert this.rightFace == null;
        this.rightFace = rightFace;
    }

    @Override
    public String toString() {
        return "MEdge{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }

    private final MPoint p1;
    private final MPoint p2;
    private MFace leftFace;
    private MFace rightFace;
}
