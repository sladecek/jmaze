package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * A {@code WallEnd} is the end of a mWall touching the pilar.
 */
public class WallEnd implements Comparable<WallEnd> {
    /**
     * WallEnd constructor.
     *
     * @param mWall
     * @param wallShape
     * @param p1IsPilar true if wallShape.p1 touches the pillar. Otherwise p2 touches the pillar.
     */
    public WallEnd(MWall mWall, WallShape wallShape, boolean p1IsPilar) {
        this.mWall = mWall;
        this.wallShape = wallShape;
        this.p1IsPilar = p1IsPilar;
    }

    public Point2D getNonPillarPoint() {
        return getPoint(!p1IsPilar);
    }

    public Point2D getPillarPoint() {
        return getPoint(p1IsPilar);
    }

    /**
     * Returns on of the points of the mWall.
     *
     * @param whichPoint if 'true' returns the rear point (p1). If 'false' returns the front point (p2).
     * @return
     */
    private Point2D getPoint(boolean whichPoint) {
        if (whichPoint) {
            return wallShape.getP1();
        } else {
            return wallShape.getP2();
        }
    }

    public int getFaceId(boolean whichFace) {
        if (whichFace) {
            return wallShape.getRigthFaceId();
        } else {
            return wallShape.getLeftFaceId();
        }
    }

    public int getLeftFaceId() {
        return getFaceId(p1IsPilar);
    }

    public int getRightFaceId() {
        return getFaceId(!p1IsPilar);
    }

    public void addEdge(MEdge edge) {
        mWall.addEdgeToHead(edge, p1IsPilar);
    }

    public MWall getMWall() {
        return mWall;
    }

    public WallShape getWallShape() {
        return wallShape;
    }

    public boolean isP1Pilar() {
        return p1IsPilar;
    }

    @Override
    public int compareTo(WallEnd wallEnd) {
        int pp = this.getPillarPoint().compareTo(wallEnd.getPillarPoint());
        if (pp == 0) {
            return this.getNonPillarPoint().compareTo(wallEnd.getNonPillarPoint());
        }
        return pp;
    }

    @Override
    public String toString() {
        return "WallEnd{" +
                "mWall=" + mWall +
                ", wallShape=" + wallShape +
                ", p1IsPilar=" + p1IsPilar +
                '}';
    }

    private final MWall mWall;
    private WallShape wallShape;
    private boolean p1IsPilar;
}
