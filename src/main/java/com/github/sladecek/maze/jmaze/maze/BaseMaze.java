package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.print2d.MazeOutputFormat;
import com.github.sladecek.maze.jmaze.print2d.SvgMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.ModelFromShapes;
import com.github.sladecek.maze.jmaze.print3d.output.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.output.StlMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.output.ThreeJs3DPrinter;
import com.github.sladecek.maze.jmaze.printstyle.Color;
import com.github.sladecek.maze.jmaze.printstyle.PrintStyle;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.util.MazeGenerationException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Builds a maze using maze data and IMaze methods.
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
        properties.put("targetMarkColor", new Color("00ff00"));
        properties.put("solutionMarkColor", new Color("777777"));

        properties.put("innerWallWidth", 1);
        properties.put("outerWallWidth", 2);
        properties.put("startMarkWidth", 4);
        properties.put("targetMarkWidth", 4);
        properties.put("solutionMarkWidth", 2);

        properties.put("outerWallColor", new Color("000000"));
        properties.put("innerWallColor", new Color("000000"));
        properties.put("debugWallColor", new Color("eeeeff"));

        properties.put("pdf", true);
        properties.put("svg", false);
        properties.put("margin", 10);
    }

    protected void addDefault3DProperties(MazeProperties properties) {
        properties.put("wallHeight", 30.0);
        properties.put("cellSize", 10.0);
        properties.put("wallSize", 2.0);

        properties.put("stl", true);
        properties.put("scad", false);
        properties.put("js", false);
    }

    protected void addComputedProperties(MazeProperties defaultProperties) {
        defaultProperties.put("fileName", "maze" + defaultProperties.getString("name"));
    }

    public void makeMazeAllSteps(boolean with3d) {
        setupRandomGenerator();
        buildMazeGraphAndShapes();
        randomlyGenerateMazePath();

        if (with3d) {
            make3DModel();
        }
    }

    private void make3DModel() {
        IMaze3DMapper mapper = create3DMapper();
        if (mapper != null) {

            PrintStyle colors;
            colors = new PrintStyle();

            model3d = ModelFromShapes.make(pathShapes, mapper, colors, properties.getDouble("wallSize"));
        }
    }

    private void randomlyGenerateMazePath() {
        IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);
        path = g.generatePick(getGraph());
        pathShapes = getAllShapes().applyRealization(path);
    }


    private void setupRandomGenerator() {
        int randomSeed = properties.getInt("randomSeed", Integer.MIN_VALUE, Integer.MAX_VALUE);
        randomGenerator = new Random();
        randomGenerator.setSeed(randomSeed);
    }

    // for unit tests
    public void forceRandomGenerator(Random r) {
        randomGenerator = r;
    }


    public void printAccordingToProperties() throws MazeGenerationException, IOException {
        final String fileName = getProperties().getString("fileName");
        // print 2D
        if (canBePrintedIn2D()) {
            SvgMazePrinter printer = new SvgMazePrinter(getProperties());

            if (getProperties().getBoolean("svg")) {
                FileOutputStream sSvg = new FileOutputStream(fileName + ".svg");
                printer.printShapes(getPathShapes(), MazeOutputFormat.svg, sSvg);
            }

            if (getProperties().getBoolean("pdf")) {
                FileOutputStream fPdf = new FileOutputStream(fileName + ".pdf");
                printer.printShapes(getPathShapes(), MazeOutputFormat.pdf, fPdf);
            }
        }

        IMaze3DMapper mapper = create3DMapper();
        if (mapper != null) {
            if (getProperties().getBoolean("js")) {
                FileOutputStream fJs = new FileOutputStream(fileName + ".js");
                new ThreeJs3DPrinter().printModel(getModel3d(), fJs);
                fJs.close();
            }
            if (getProperties().getBoolean("scad")) {
                FileOutputStream fScad = new FileOutputStream(fileName + ".scad");
                new OpenScad3DPrinter().printModel(getModel3d(), fScad);
                fScad.close();
            }
            if (getProperties().getBoolean("stl")) {
                FileOutputStream fStl = new FileOutputStream(fileName + ".stl");
                new StlMazePrinter().printModel(getModel3d(), fStl);
                fStl.close();
            }
        }
    }

}
