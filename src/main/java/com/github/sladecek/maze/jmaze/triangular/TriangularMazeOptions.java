package com.github.sladecek.maze.jmaze.triangular;

import com.github.sladecek.maze.jmaze.properties.MazeOption;
import com.github.sladecek.maze.jmaze.properties.MazeOptions;
import com.github.sladecek.maze.jmaze.rectangular.RectangularMaze;

/**
 * Configurable options for rectangular maze.
 */
public class TriangularMazeOptions extends MazeOptions {
    public TriangularMazeOptions() {
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
