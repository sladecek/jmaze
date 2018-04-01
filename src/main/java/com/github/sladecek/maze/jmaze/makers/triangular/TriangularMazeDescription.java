package com.github.sladecek.maze.jmaze.makers.triangular;
//REV1

import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;

/**
 * Configurable options for triangular maze.
 */
public class TriangularMazeDescription extends MazeDescription {
    public TriangularMazeDescription() {
        ownOptions.add(new MazeOption("size", 10, 2, 100000));
    }

    @Override
    public String getName() {
        return "triangular";
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
        return TriangularMaze.class;
    }
}
