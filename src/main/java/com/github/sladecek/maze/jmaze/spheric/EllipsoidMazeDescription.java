package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;
import com.github.sladecek.maze.jmaze.properties.OptionLevel;
import com.github.sladecek.maze.jmaze.rectangular.RectangularMaze;

import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Configurable options for ellipsoid maze.
 */
public class EllipsoidMazeDescription extends EggMazeDescriptionBase {
    public EllipsoidMazeDescription() {
        super();
        findOption("eggness").setLevel(OptionLevel.Invisible).setValue(1.0);
    }

    @Override
    public String getName() {
        return "ellipsoid";
    }

}
