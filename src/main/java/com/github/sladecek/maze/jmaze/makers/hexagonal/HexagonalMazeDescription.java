package com.github.sladecek.maze.jmaze.makers.hexagonal;

import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;
import com.github.sladecek.maze.jmaze.makers.rectangular.RectangularMaze;

import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Configurable options for rectangular maze.
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
    private static final ResourceBundle bundle = getBundle("jMazeMessages");

}
