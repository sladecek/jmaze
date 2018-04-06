package com.github.sladecek.maze.jmaze.makers.spherical;
//REV1
import com.github.sladecek.maze.jmaze.properties.OptionLevel;

/**
 * Configurable options for ellipsoid maze. Most options are shared with other spherical mazes.
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
