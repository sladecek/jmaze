package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MFace;

/**
 * A horizontal face that lies in the floor plan of the maze. The 3D maze is constructed
 * by extruding floor faces intro appropriate altitude in the {@code z} direction.
 */
public class FloorFace extends MFace {
    public Altitude getAltitude() {
        return altitude;
    }

    public void setAltitude(Altitude altitude) {
        this.altitude = altitude;
    }

    private Altitude altitude = Altitude.FLOOR;
}
