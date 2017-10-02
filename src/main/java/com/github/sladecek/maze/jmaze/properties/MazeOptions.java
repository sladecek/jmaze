package com.github.sladecek.maze.jmaze.properties;

import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.printstyle.Color;
import com.github.sladecek.maze.jmaze.rectangular.RectangularMaze;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Description of certain maze type.
 */
abstract public class MazeOptions implements IValidator {


    public List<MazeOption> getOwnOptions() {
        return ownOptions;
    }

    public List<MazeOption> getAllOptions() {
        ArrayList<MazeOption> result = new ArrayList<>();
        result.addAll(ownOptions);
        result.addAll(getUniversalOptions());
        if (canBePrintedIn2D()) {
            result.addAll(get2dOptions());
        }
        if (canBePrintedIn3D()) {
            result.addAll(get3dOptions());
        }
        return result;
    }

    public List<MazeOption> getUniversalOptions() {
        ArrayList<MazeOption> result = new ArrayList<>();
        result.add(new MazeOption("randomSeed", 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        result.add(new MazeOption("name",  getName()));
        result.add(new MazeOption("fileName", "maze" + getName()));
        return result;
    }

    public  List<MazeOption> get2dOptions() {
        ArrayList<MazeOption> result = new ArrayList<>();
        result.add(new MazeOption("printSolution", true));
        result.add(new MazeOption("printAllWalls", false));

        result.add(new MazeOption("startMarkColor", new Color("ff0000")));
        result.add(new MazeOption("targetMarkColor", new Color("00ff00")));
        result.add(new MazeOption("solutionMarkColor", new Color("777777")));

        result.add(new MazeOption("innerWallWidth", 1, 1, 100));
        result.add(new MazeOption("outerWallWidth", 2, 1, 100));
        result.add(new MazeOption("startMarkWidth", 4, 1, 100));
        result.add(new MazeOption("targetMarkWidth", 4, 1, 100));
        result.add(new MazeOption("solutionMarkWidth", 2, 1, 100));

        result.add(new MazeOption("outerWallColor", new Color("000000")));
        result.add(new MazeOption("innerWallColor", new Color("000000")));
        result.add(new MazeOption("debugWallColor", new Color("eeeeff")));

        result.add(new MazeOption("pdf", true));
        result.add(new MazeOption("svg", false));
        result.add(new MazeOption("margin", 10, 0, 100));
        return result;

    }

    public List<MazeOption> get3dOptions() {
        ArrayList<MazeOption> result = new ArrayList<>();
        result.add(new MazeOption("wallHeight", 30.0, 1.0, 100.0, 0.1));
        result.add(new MazeOption("cellSize", 10.0, 1.0, 100.0, 0.1));
        result.add(new MazeOption("wallSize", 2.0, 1.0, 100.0, 0.1));

        result.add(new MazeOption("stl", true));
        result.add(new MazeOption("scad", false));
        result.add(new MazeOption("js", false));

        return result;

    }

    public abstract boolean canBePrintedIn2D();

    public abstract boolean canBePrintedIn3D();

    public abstract String getName();

    public abstract String getLocalisedName();

    public abstract Class getMazeClass();

    public MazeProperties getDefaultProperties() {
        return getDefaultProperties(ownOptions);
    }

    public MazeProperties getDefaultProperties(List<MazeOption> options) {
        MazeProperties result = new MazeProperties();
        for (MazeOption o: options) {
            result.put(o.getName(), o.getDefaultValue());
        }
        return result;
    }


    protected List<MazeOption> ownOptions = new ArrayList<>();

    @Override
    public MazeValidationErrors convertAndValidate(MazeProperties properties, Locale locale) {
        MazeValidationErrors result = new MazeValidationErrors();
        getAllOptions().forEach(o->o.convertAndValidate(properties, locale, result));
        return result;
    }
}
