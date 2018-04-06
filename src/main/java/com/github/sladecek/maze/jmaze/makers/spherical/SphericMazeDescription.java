package com.github.sladecek.maze.jmaze.makers.spherical;
//REV1
import com.github.sladecek.maze.jmaze.properties.OptionLevel;

/**
 * Configurable options for spherical maze. Most options are shared with other spherical mazes.
 */
public class SphericMazeDescription extends EggMazeDescriptionBase {
    public SphericMazeDescription() {
        super();
        findOwnOption("ellipseMajor").setLevel(OptionLevel.Invisible);
        findOwnOption("ellipseMinor").setLevel(OptionLevel.Invisible);
        findOwnOption("eggness").setLevel(OptionLevel.Invisible).setValue(0.0);
    }

    @Override
    public String getName() {
        return "spherical";
    }

}
