package com.github.sladecek.maze.jmaze.moebius;

import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeOption;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.properties.MazeValidationErrors;

import java.util.Locale;
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

    private static final ResourceBundle bundle = getBundle("messages");

    @Override
    public MazeValidationErrors convertAndValidate(MazeProperties properties, String prefix, Locale locale) {
        MazeValidationErrors  mve = super.convertAndValidate(properties, prefix, locale);

        int moebWidth = properties.getInt("sizeAcross", 1, 1111);
        if (moebWidth % 2 == 1) {
            mve.addError(prefix, "sizeAccress", "must not be odd");
        }
        return mve;
    }
}