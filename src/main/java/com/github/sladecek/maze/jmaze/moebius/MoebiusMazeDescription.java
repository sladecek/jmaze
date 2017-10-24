package com.github.sladecek.maze.jmaze.moebius;

import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;
import com.github.sladecek.maze.jmaze.rectangular.RectangularMaze;

import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Configurable options for rectangular maze.
 */
public class MoebiusMazeDescription extends MazeDescription {
    public MoebiusMazeDescription() {
        ownOptions.add(new MazeOption("sizeAlong", 40, 2, 10000));
        ownOptions.add(new MazeOption("sizeAcross", 4, 2, 1000));


    }

    @Override
    public String getName() {
        return "moebius";
    }

    @Override
    public String getLocalisedName() {
        return bundle.getString("moebius");
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
        return MoebiusMaze.class;
    }
    private static ResourceBundle bundle = getBundle("messages");

}
