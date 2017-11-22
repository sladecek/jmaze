package com.github.sladecek.maze.jmaze.properties;

import com.github.sladecek.maze.jmaze.printstyle.Color;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Description of certain maze type.
 */
abstract public class MazeDescription implements IValidator {


    public List<MazeOption> getOwnOptions() {
        return ownOptions;
    }


    protected MazeOption findOption(String name) {
        for (MazeOption o: getOwnOptions()) {
            if (o.getName().equals(name)) {
                return o;
            }
        }
        throw new InternalError("nvalid option "+name);
    }

    private List<MazeOption> getAllOptions() {
        ArrayList<MazeOption> result = new ArrayList<>();
        result.addAll(ownOptions);
        result.addAll(getUniversalOptions());
// TODO smazat        if (canBePrintedIn2D())
{
            result.addAll(get2dOptions());
        }
        // TODO smazat if (canBePrintedIn3D())
        {
            result.addAll(get3dOptions());
        }
        return result;
    }

    public static List<MazeOption> getUniversalOptions() {
        ArrayList<MazeOption> result = new ArrayList<>();
        result.add(new MazeOption("randomSeed", 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        return result;
    }



    public static List<MazeOption> get2dOptions() {
        ArrayList<MazeOption> result = new ArrayList<>();
        result.add(new MazeOption("printSolution", true));
        result.add((new MazeOption("printAllWalls", false)).setLevel(OptionLevel.Extended));

        result.add((new MazeOption("startMarkColor", new Color("ff0000")).setLevel(OptionLevel.Extended)));
        result.add((new MazeOption("targetMarkColor", new Color("00ff00")).setLevel(OptionLevel.Extended)));
        result.add((new MazeOption("solutionMarkColor", new Color("777777")).setLevel(OptionLevel.Extended)));

        result.add((new MazeOption("innerWallWidth", 1, 1, 100).setLevel(OptionLevel.Extended)));
        result.add((new MazeOption("outerWallWidth", 2, 1, 100).setLevel(OptionLevel.Extended)));
        result.add((new MazeOption("startMarkWidth", 4, 1, 100).setLevel(OptionLevel.Extended)));
        result.add((new MazeOption("targetMarkWidth", 4, 1, 100).setLevel(OptionLevel.Extended)));
        result.add((new MazeOption("solutionMarkWidth", 2, 1, 100).setLevel(OptionLevel.Extended)));

        result.add((new MazeOption("outerWallColor", new Color("000000")).setLevel(OptionLevel.Extended)));
        result.add((new MazeOption("innerWallColor", new Color("000000")).setLevel(OptionLevel.Extended)));
        result.add((new MazeOption("debugWallColor", new Color("eeeeff")).setLevel(OptionLevel.Extended)));

        result.add((new MazeOption("pdf", true).setLevel(OptionLevel.Invisible)));
        result.add((new MazeOption("svg", false).setLevel(OptionLevel.Invisible)));
        result.add((new MazeOption("margin", 10, 0, 100).setLevel(OptionLevel.Extended)));
        return result;

    }

    public static List<MazeOption> get3dOptions() {
        ArrayList<MazeOption> result = new ArrayList<>();
        result.add((new MazeOption("wallHeight", 30.0, 1.0, 100.0, 0.1).setLevel(OptionLevel.Extended)));
        result.add((new MazeOption("cellSize", 10.0, 1.0, 100.0, 0.1).setLevel(OptionLevel.Extended)));
        result.add((new MazeOption("wallSize", 2.0, 1.0, 100.0, 0.1).setLevel(OptionLevel.Extended)));

        result.add((new MazeOption("stl", true).setLevel(OptionLevel.Invisible)));
        result.add((new MazeOption("scad", false).setLevel(OptionLevel.Invisible)));
        result.add((new MazeOption("js", false).setLevel(OptionLevel.Invisible)));

        return result;

    }

    public abstract boolean canBePrintedIn2D();

    public abstract boolean canBePrintedIn3D();

    public abstract String getName();

    public abstract Class getMazeClass();

    public MazeProperties getOwnProperties() {
        return getDefaultProperties(ownOptions);
    }

    public MazeProperties getDefaultProperties() {
        return getDefaultProperties(getAllOptions());
    }


    private MazeProperties getDefaultProperties(List<MazeOption> options) {
        MazeProperties result = new MazeProperties();
        for (MazeOption o: options) {
            result.put(o.getName(), o.getDefaultValue());
        }
        return result;
    }


    protected final List<MazeOption> ownOptions = new ArrayList<>();

    @Override
    public MazeValidationErrors convertAndValidate(MazeProperties properties, String prefix, Locale locale) {
        MazeValidationErrors result = new MazeValidationErrors();
        getAllOptions().forEach(o->o.convertAndValidate(properties, locale, prefix, result));
        return result;
    }
}
