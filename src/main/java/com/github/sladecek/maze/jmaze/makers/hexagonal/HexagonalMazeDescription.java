package com.github.sladecek.maze.jmaze.makers.hexagonal;
//REV1
import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;

/**
 * Configurable options for hexagonal maze.
 */
public class HexagonalMazeDescription extends MazeDescription {
    public HexagonalMazeDescription() {
        ownOptions.add(new MazeOption("size", 6, 2, 1000));
    }

    @Override
    public String getName() {
        return "hexagonal";
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
        return HexagonalMaze.class;
    }
}
