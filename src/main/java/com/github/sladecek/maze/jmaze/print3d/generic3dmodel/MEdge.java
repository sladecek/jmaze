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

    /**
     * Return 'p1' if 'f' is left face, otherwise p1.
     *
     * @param faceThatShouldBeOnLeftSide
     * @return
     */
    public MPoint getBackwardPoint(MFace faceThatShouldBeOnLeftSide) {
        MPoint result = (faceThatShouldBeOnLeftSide == leftFace) ? p1 : p2;
        return result;
    }

    public MPoint getForwardPoint(MFace faceThatShouldBeOnLeftSide) {
        MPoint result = (faceThatShouldBeOnLeftSide == leftFace) ? p2 : p1;
        return result;
    }

    public MFace getRightFace() {
        return rightFace;
    }

    /**
     * Right face setter. Can be set only once.
     */
    public void setRightFace(MFace rightFace) {
        assert this.rightFace == null;
        this.rightFace = rightFace;
    }

    public MFace getLeftFace() {
        return leftFace;
    }

    public void setLeftFace(MFace leftFace) {
        assert this.leftFace == null;
        this.leftFace = leftFace;
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
    private MFace rightFace;
    private MFace leftFace;
}
