package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;
import com.github.sladecek.maze.jmaze.rectangular.RectangularMaze;

import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Base class fo configurable options of all spheric mazes (egg, ellipsoid, sphere).
 */
public abstract class EggMazeDescriptionBase extends MazeDescription {
    public EggMazeDescriptionBase() {
        ownOptions.add(new MazeOption("eggness", 0.2, 0.1, 0.9, 0.1));
        ownOptions.add(new MazeOption("equatorCells", 8, 2, 64));

    }


    @Override
    public String getLocalisedName() {
        return bundle.getString(getName());
    }

    @Override
    public boolean canBePrintedIn2D() {
        return false;
    }

    @Override
    public boolean canBePrintedIn3D() {
        return true;
    }

    @Override
    public Class getMazeClass() {
        return EggMaze.class;
    }

    private static ResourceBundle bundle = getBundle("messages");

}
