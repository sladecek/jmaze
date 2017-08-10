package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.ModelFromShapes;
import com.github.sladecek.maze.jmaze.printstyle.Color;
import com.github.sladecek.maze.jmaze.printstyle.PrintStyle;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;

import java.util.Random;

/**
 *  Builds a maze using maze data and IMaze methods.
 */
public abstract class BaseMaze extends MazeData implements IMaze {
    public BaseMaze() {
        properties = getDefaultProperties().clone();
    }

    @Override
    public MazeProperties getDefaultProperties() {
        MazeProperties defaultProperties = new MazeProperties();
        defaultProperties.put("randomSeed", 0);
        return defaultProperties;
    }

    protected void addDefault2DProperties(MazeProperties properties) {

        properties.put("printSolution", true);
        properties.put("printAllWalls", false);

        properties.put("startMarkColor", new Color("ff0000"));
        properties.put("targetMarkColor",  new Color("00ff00"));
        properties.put("solutionMarkColor", new Color("777777"));

        properties.put("innerWallWidth", 1);
        properties.put("outerWallWidth", 2);
        properties.put("startMarkWidth", 4);
        properties.put("targetMarkWidth",4);
        properties.put("solutionMarkWidth",2);

        properties.put("outerWallColor", new Color("000000"));
        properties.put("innerWallColor", new Color("000000"));
        properties.put("debugWallColor",  new Color("eeeeff"));

    }

    protected void addDefault3DProperties(MazeProperties properties) {
        properties.put("wallHeight", 30.0);
        properties.put("cellSize", 10.0);
        properties.put("wallSize", 2.0);
    }

    public void makeMazeAllSteps(boolean with3d) {
        setupRandomGenerator();
        buildMazeGraphAndShapes();
        randomlyGenerateMazePath();

        if (with3d) {
            make3DModel();
        }

    }

    public void make3DModel() {
        IMaze3DMapper mapper = create3DMapper();
        if (mapper != null) {

            PrintStyle colors;

            double approxRoomSizeInmm = 3;
            colors = new PrintStyle();

            model3d = ModelFromShapes.make(pathShapes, mapper, colors, properties.getDouble("wallSize"));
        }
    }

    public void randomlyGenerateMazePath() {
        IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);
        path = g.generatePick(getGraph());
        pathShapes = getAllShapes().applyRealization(path);
    }


    public void setupRandomGenerator() {
        int randomSeed = properties.getInt("randomSeed", Integer.MIN_VALUE, Integer.MAX_VALUE);
        randomGenerator = new Random();
        randomGenerator.setSeed(randomSeed);
    }

    // for unit tests
    public void forceRandomGenerator(Random r) {
        randomGenerator = r;
    }


}
