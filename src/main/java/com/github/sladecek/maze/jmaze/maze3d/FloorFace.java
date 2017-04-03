package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.model3d.MFace;

/**
 * A horizontal face that lies in the floor plan of the maze. The 3D maze is constructed
 * by extruding floor faces intro approprite altitude in the {@code z} direction.
 */
public class FloorFace extends MFace {
    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }
    public static final int FLOOR_ALTITUDE = 1;
    public static final int GROUND_ALTITUDE = 0;
    public static final int CEILING_ALTITUDE = 2;
    public static final int FRAME_ALTITUDE = -1;
    private int altitude = FLOOR_ALTITUDE;
}
