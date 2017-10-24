package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.properties.OptionLevel;
import com.github.sladecek.maze.jmaze.spheric.EggMazeDescriptionBase;

/**
 * Configurable options for spheric maze.
 */
public class SphericMazeDescription extends EggMazeDescriptionBase {
    public SphericMazeDescription() {
        super();
        findOption("ellipseMajor").setLevel(OptionLevel.Invisible);
        findOption("ellipseMinor").setLevel(OptionLevel.Invisible);
        findOption("eggness").setLevel(OptionLevel.Invisible).setValue(0.0);
    }

    @Override
    public String getName() {
        return "spheric";
    }

}
