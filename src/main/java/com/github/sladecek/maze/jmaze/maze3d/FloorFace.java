package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.model3d.MFace;

/**
 * A horizontal face that lies in the floor plan of the maze. The 3D maze is constructed
 * by extruding floor faces intro approprite altitude in the {@code z} direction.
 */
public class FloorFace extends MFace {
    private int altitude;

}
