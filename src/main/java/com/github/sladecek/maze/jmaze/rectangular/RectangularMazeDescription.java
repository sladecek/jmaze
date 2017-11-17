package com.github.sladecek.maze.jmaze.rectangular;

import com.github.sladecek.maze.jmaze.properties.MazeOption;
import com.github.sladecek.maze.jmaze.properties.MazeDescription;

import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Configurable options for rectangular maze.
 */
public class RectangularMazeDescription extends MazeDescription {
    public RectangularMazeDescription() {
        ownOptions.add(new MazeOption("width", 20, 2, 100000));
        ownOptions.add(new MazeOption("height", 20, 2, 100000));
    }

    @Override
    public String getName() {
        return "rectangular";
    }

    @Override
    public String getLocalisedName() {
        return bundle.getString("rectangular");
    }

    @Override
    public boolean canBePrintedIn2D() {
        return true;
    }

    @Override
    public boolean canBePrintedIn3D() {
        return true;
    }

    @Override
    public Class getMazeClass() {
        return RectangularMaze.class;
    }
    private static final ResourceBundle bundle = getBundle("messages");

}
