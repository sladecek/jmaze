package com.github.sladecek.maze.jmaze.triangular;

import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;

import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Configurable options for rectangular maze.
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
    public String getLocalisedName() {
        return bundle.getString("triangular");
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

    private static ResourceBundle bundle = getBundle("messages");
}
