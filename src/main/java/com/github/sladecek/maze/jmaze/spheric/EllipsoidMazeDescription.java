package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;
import com.github.sladecek.maze.jmaze.rectangular.RectangularMaze;

import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Configurable options for ellipsoid maze.
 */
public class EllipsoidMazeDescription extends EggMazeDescriptionBase {
    public EllipsoidMazeDescription() {
        super();
        ownOptions.add(new MazeOption("ellipseMajor", 10.0,  0.1, 50.0, 0.1));
        ownOptions.add(new MazeOption("ellipseMinor", 10.0, 0.1, 50, 0.1));

    }

    @Override
    public String getName() {
        return "ellipsoid";
    }

    private static ResourceBundle bundle = getBundle("messages");

}
