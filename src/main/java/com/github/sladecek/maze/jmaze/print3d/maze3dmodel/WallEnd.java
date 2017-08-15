package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * A {@code WallEnd} is the end of a mWall touching the pillar.
 */
public class WallEnd implements Comparable<WallEnd> {
    /**
     * WallEnd constructor.
     *
     * @param p1IsPillar true if wallShape.p1 touches the pillar. Otherwise p2 touches the pillar.
     */
    public WallEnd(MWall mWall, WallShape wallShape, boolean p1IsPillar) {
        this.mWall = mWall;
        this.wallShape = wallShape;
        this.p1IsPillar = p1IsPillar;
    }

    public Point2DInt getNonPillarPoint() {
        return getPoint(!p1IsPillar);
    }

    public Point2DInt getPillarPoint() {
        return getPoint(p1IsPillar);
    }

    /**
     * Returns on of the points of the mWall.
     *
     * @param whichPoint if 'true' returns the rear point (p1). If 'false' returns the front point (p2).

     */
    private Point2DInt getPoint(boolean whichPoint) {
        if (whichPoint) {
            return wallShape.getP1();
        } else {
            return wallShape.getP2();
        }
    }

    private int getFaceId(boolean whichFace) {
        if (whichFace) {
            return wallShape.getRightFaceId();
        } else {
            return wallShape.getLeftFaceId();
        }
    }

    public int getLeftFaceId() {
        return getFaceId(p1IsPillar);
    }

    public int getRightFaceId() {
        return getFaceId(!p1IsPillar);
    }

    public void addEdge(MEdge edge) {
        mWall.addEdgeToHead(edge, p1IsPillar);
    }

    public MWall getMWall() {
        return mWall;
    }

    public WallShape getWallShape() {
        return wallShape;
    }

    public boolean isP1Pillar() {
        return p1IsPillar;
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
                ", p1IsPillar=" + p1IsPillar +
                '}';
    }

    private final MWall mWall;
    private final WallShape wallShape;
    private final boolean p1IsPillar;
}
