package com.github.sladecek.maze.jmaze.makers.spheric;

import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;

import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Base class for configurable options of all spheric mazes (egg, ellipsoid, sphere).
 */
public abstract class EggMazeDescriptionBase extends MazeDescription {
    EggMazeDescriptionBase() {
        ownOptions.add((new MazeOption("equatorCells", 8, 2, 64)).setEditor("powersOf2"));
        ownOptions.add(new MazeOption("ellipseMajor", 10.0, 0.1, 50.0, 0.1));
        ownOptions.add(new MazeOption("ellipseMinor", 10.0, 0.1, 50, 0.1));
        ownOptions.add(new MazeOption("eggness", 0.5, 0.0, 0.9, 0.1));
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

}
