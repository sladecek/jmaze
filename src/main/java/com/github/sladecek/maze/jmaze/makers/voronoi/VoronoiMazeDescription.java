package com.github.sladecek.maze.jmaze.makers.voronoi;

import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;

import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Configurable options for rectangular maze.
 */
public class VoronoiMazeDescription extends MazeDescription {
    public VoronoiMazeDescription() {
        ownOptions.add(new MazeOption("width", 20, 2, 100000));
        ownOptions.add(new MazeOption("height", 20, 2, 100000));
        ownOptions.add(new MazeOption("roomCount", 100, 2, 1000000));
        ownOptions.add(new MazeOption("loydCount", 10, 2, 10000));


    }

    @Override
    public String getName() {
        return "voronoi";
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
        return VoronoiMaze.class;
    }
    private static final ResourceBundle bundle = getBundle("jMazeMessages");

}
