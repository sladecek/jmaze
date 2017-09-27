package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.print.IMazePrinter;
import com.github.sladecek.maze.jmaze.print.MazeOutputFormat;
import com.github.sladecek.maze.jmaze.print.MazeResult;
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
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Builds a maze using maze data and IMaze methods.
 */
public abstract class Maze extends MazeData implements IMaze {
    protected Maze() {
        properties = getDefaultProperties().deepCopy();
    }

    @Override
    public MazeProperties getDefaultProperties() {
        // TODO smazat
        MazeProperties defaultProperties = new MazeProperties();
        defaultProperties.put("randomSeed", 0);
        return defaultProperties;
    }

    protected void addDefault2DProperties(MazeProperties properties) {
        // TODO smazat
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
        // TODO smazat
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


    public void printToFileInAllAvailableFormats() throws MazeGenerationException, IOException {
        final String fileName = getProperties().getString("fileName");
        for (MazeOutputFormat format : MazeOutputFormat.values()) {
            IMazePrinter pr = constructMazePrinterIfInProperties(format);
            if (pr != null) {
                FileOutputStream stream = new FileOutputStream(fileName + "." + format.fileExtension());
                pr.print(stream);
                stream.close();
            }
        }
    }

    public MazeResult printMazeInFormat(MazeOutputFormat format) throws MazeGenerationException, IOException {

        boolean storeSvgToJson = false;
        if (canBePrintedIn2D() && format == MazeOutputFormat.json) {
            // json stored in svg
            format = MazeOutputFormat.svg;
            storeSvgToJson = true;
        }

        IMazePrinter pr = constructMazePrinter(format);
        if (pr != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pr.print(stream);
            stream.close();
            final String fileName = getProperties().getString("fileName");
            if (storeSvgToJson) {
                String jsonEncodedSvg = JSONObject.quote(stream.toString());

                Point2DInt canvasSize = pr.getCanvasSize();
//
                String json = String.format("{ \"svg\": %s, \"width\": %d, \"height\": %d}",
                        jsonEncodedSvg, canvasSize.getX(), canvasSize.getY());
                return new MazeResult(fileName, format, json);
            } else {
                return new MazeResult(fileName, format, stream.toString());
            }
        }
        return null;
    }

    public IMazePrinter constructMazePrinterIfInProperties(MazeOutputFormat format) {

        if (getProperties().getBooleanOrFalse(format.name())) {
            return constructMazePrinter(format);

        }
        return null;
    }

    public IMazePrinter constructMazePrinter(MazeOutputFormat format) {
        // print 2D
        IMazePrinter pr = null;
        if (format.is2D() && canBePrintedIn2D()) {
            pr = new SvgMazePrinter(getProperties(), format, getPathShapes());
        } else if (format.is3D() && canBePrintedIn3D()) {
            switch (format) {
                case json:
                    pr = new ThreeJs3DPrinter(getModel3d());
                case scad:
                    pr = new OpenScad3DPrinter(getModel3d());
                case stl:
                    pr = new StlMazePrinter(getModel3d());
            }
        }
        return pr;
    }

    public boolean canBePrintedIn3D() {
        // TODO smazat
        return create3DMapper() != null;
    }
}