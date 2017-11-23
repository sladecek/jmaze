package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.properties.*;

import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Base class fo configurable options of all spheric mazes (egg, ellipsoid, sphere).
 */
public abstract class EggMazeDescriptionBase extends MazeDescription {
    EggMazeDescriptionBase() {
        ownOptions.add((new MazeOption("equatorCells", 8, 2, 64)).setEditor("powersOf2"));
        ownOptions.add(new MazeOption("ellipseMajor", 10.0,  0.1, 50.0, 0.1));
        ownOptions.add(new MazeOption("ellipseMinor", 10.0, 0.1, 50, 0.1));
        ownOptions.add(new MazeOption("eggness", 0.5, 0.1, 0.9, 0.1));
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

    private static final ResourceBundle bundle = getBundle("jMazeMessages");

    @Override
    public MazeValidationErrors convertAndValidate(MazeProperties properties, String prefix, Locale locale) {
        MazeValidationErrors  mve = super.convertAndValidate(properties, prefix, locale);

        double eggnes = properties.getDouble("eggness");
        if (eggnes <= 0.01) {
            mve.addError(prefix,"eggness", "must be positive >= 0.01");
        }
        if (eggnes > 0.9) {
            mve.addError(prefix,"eggness", "must be <= 0.9");
        }

        /* TODO smazat
        double symmetry = properties.getDouble("symmetry");
        if (symmetry < 0.01) {
            mve.addError(prefix,"symmetry", "must be positive >= 0.01");
        }
        if (symmetry > 10.0) {
            mve.addError(prefix,"symmetry", "must be <= 10.0");
        }
*/

        return mve;
    }
}
