package com.github.sladecek.maze.jmaze.makers.spheric;

import com.github.sladecek.maze.jmaze.properties.OptionLevel;

/**
 * Configurable options for ellipsoid maze.
 */
public class EllipsoidMazeDescription extends EggMazeDescriptionBase {
    public EllipsoidMazeDescription() {
        super();
        findOwnOption("eggness").setLevel(OptionLevel.Invisible).setValue(0.0);
    }

    @Override
    public String getName() {
        return "ellipsoid";
    }

}
