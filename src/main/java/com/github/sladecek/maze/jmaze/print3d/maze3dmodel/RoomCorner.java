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

    public WallEnd getWallEnd1() {
        return wallEnd1;
    }

    public void setWallEnd1(WallEnd wallEnd1) {
        this.wallEnd1 = wallEnd1;
    }

    public WallEnd getWallEnd2() {
        return wallEnd2;
    }

    public void setWallEnd2(WallEnd wallEnd2) {
        this.wallEnd2 = wallEnd2;
    }

    @Override
    public String toString() {
        return "RoomCorner{" +
                "floorId=" + floorId +
                ", wallEnd1=" + wallEnd1 +
                ", wallEnd2=" + wallEnd2 +
                '}';
    }

    private int floorId;
    private WallEnd wallEnd1;
    private WallEnd wallEnd2;
}
