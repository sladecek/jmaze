package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

/**
 * Represents a floor touching a pillar between two walls.
 */
public class RoomCorner {
    public RoomCorner(int floorId) {
        this.floorId = floorId;
    }

    public int getFloorId() {
        return floorId;
    }

    public WallEnd getWall1() {
        return wall1;
    }

    public void setWall1(WallEnd wall1) {
        this.wall1 = wall1;
    }

    public WallEnd getWall2() {
        return wall2;
    }

    public void setWall2(WallEnd wall2) {
        this.wall2 = wall2;
    }

    private int floorId;
    private WallEnd wall1;
    private WallEnd wall2;
}
