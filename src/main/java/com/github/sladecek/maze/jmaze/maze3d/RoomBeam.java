package com.github.sladecek.maze.jmaze.maze3d;

/**
 * Represents a floor touching a pillar between two walls (i.e. wall beams).
 */
public class RoomBeam extends Beam {
    private int floorId;

    public RoomBeam(int floorId) {
        this.floorId = floorId;
    }
}
