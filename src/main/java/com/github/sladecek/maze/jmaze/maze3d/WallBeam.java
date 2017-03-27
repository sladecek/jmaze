package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * A {@code WallBeam} is the wall going out of a pillar towards another pillar.
 */
public class WallBeam extends Beam {
    public WallBeam(Point2D otherPoint, WallShape wall, int leftFaceId, int rightFaceId) {
        this.otherPoint = otherPoint;
        this.wall = wall;
        this.leftFaceId = leftFaceId;
        this.rightFaceId = rightFaceId;
    }

    public Point2D getOtherPoint() {
        return otherPoint;
    }

    public WallShape getWall() {
        return wall;
    }

    public int getLeftFaceId() {
        return leftFaceId;
    }

    public int getRightFaceId() {
        return rightFaceId;
    }
    private Point2D otherPoint;
    private WallShape wall;
    private int leftFaceId = -1;
    private int rightFaceId = -1;
}
