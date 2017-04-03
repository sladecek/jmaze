package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.model3d.MEdge;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * A {@code WallEnd} is the end of a touching the pilar..
 */
public class WallEnd extends Beam {


    public WallEnd(MWall wall, WallShape wallShape, boolean reversed) {
        this.wall = wall;
        this.wallShape = wallShape;
        this.reversed = reversed;
    }

    public Point2D getCenterPoint() {
        return getPoint(!reversed);

    }


    private Point2D getPoint(boolean whichPoint) {
        if (whichPoint) {
            return wallShape.getP1();
        } else {
            return wallShape.getP2();
        }

    }

    public Point2D getOtherPoint() {
        return getPoint(reversed);
    }


    public int getFaceId(boolean whichFace) {
        if (whichFace) {
            return wallShape.getLeftFaceId();
        } else {
            return wallShape.getRightFaceId();
        }
    }

    public int getLeftFaceId() {
        return getFaceId(!reversed);
    }

    public int getRightFaceId() {
        return getFaceId(reversed);
    }

    public void addEdge(MEdge edge) {
        wall.addEndEdge(edge, reversed);
    }

    private final MWall wall;

    public MWall getWall() {
        return wall;
    }

    public WallShape getWallShape() {
        return wallShape;
    }

    public boolean isReversed() {
        return reversed;
    }

    private WallShape wallShape;
    private boolean reversed;
}
