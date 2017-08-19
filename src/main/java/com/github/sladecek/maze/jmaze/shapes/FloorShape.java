package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.generator.MazePath;
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;


/**
 * Floor in 3D maze.
 */
public class FloorShape implements IMazeShape {
    public FloorShape(int roomId, Point2DInt position) {
        this.position = position;
        this.roomId = roomId;
        this.isHole = false;
        this.wallId = -1;
    }

    public Point2DInt getPosition() {
        return position;
    }

    public int getRoomId() {
        return roomId;
    }

    public MarkType getMarkType() {
        return markType;
    }

    public boolean isHole() {
        return isHole;
    }

    @Override
    public void applyPath(MazePath mr) {

        isHole = wallId >=0 && !mr.isWallClosed(wallId);
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    private final Point2DInt position;
    private int roomId;
    private final MarkType markType = MarkType.none;
    private boolean isHole;

    // in some mazes we call pass throug the floor

    public int getWallId() {
        return wallId;
    }

    public void setWallId(int wallId) {
        this.wallId = wallId;
    }

    private int wallId;
}
