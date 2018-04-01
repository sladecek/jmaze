package com.github.sladecek.maze.jmaze.makers.circular;
//REV1

import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;

/**
 * Configurable options for circular maze.
 */
public class CircularMazeDescription extends MazeDescription {
    public CircularMazeDescription() {
        ownOptions.add(new MazeOption("layerCount", 4, 1, 1000));
    }

    @Override
    public String getName() {
        return "circular";
    }

    @Override
    public boolean canBePrintedIn2D() {
        return true;
    }

    @Override
    public boolean canBePrintedIn3D() {
        return false;
    }

    @Override
    public Class getMazeClass() {
        return CircularMaze.class;
    }
}
