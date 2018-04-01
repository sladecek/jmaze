package com.github.sladecek.maze.jmaze.makers.moebius;
//REV1
import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.properties.MazeValidationErrors;

import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

/**
 * Configurable options for Moebius maze.
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

    @Override
    public MazeValidationErrors convertAndValidate(MazeProperties properties, String prefix, Locale locale) {
        MazeValidationErrors mve = super.convertAndValidate(properties, prefix, locale);

        int moebiusWidth = properties.getInt("sizeAcross");
        if (moebiusWidth % 2 == 1) {
            mve.addError(prefix, "sizeAccross", "must not be odd");
        }
        return mve;
    }

    private static final ResourceBundle bundle = getBundle("jMazeMessages");
}