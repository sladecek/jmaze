package com.github.sladecek.maze.jmaze.shapes;
//REV1
import com.github.sladecek.maze.jmaze.generator.MazePath;
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;

/**
 * Floor shape. Makes sense only in 3D mazes.
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

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public MarkType getMarkType() {
        return markType;
    }

    public boolean isHole() {
        return isHole;
    }

    @Override
    public void applyPath(MazePath mr) {
        isHole = wallId >= 0 && !mr.isWallClosed(wallId);
    }

    public int getWallId() {
        return wallId;
    }

    public void setWallId(int wallId) {
        this.wallId = wallId;
    }

    private final Point2DInt position;
    private final MarkType markType = MarkType.none;

    // in some mazes we call pass through the floor
    private int roomId;
    private boolean isHole;
    private int wallId;
}
