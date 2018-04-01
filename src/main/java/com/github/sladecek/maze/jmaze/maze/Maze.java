package com.github.sladecek.maze.jmaze.maze;
//REV1
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
import com.github.sladecek.maze.jmaze.print.PrintStyle;
import com.github.sladecek.maze.jmaze.properties.MazeValidationErrors;
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
        int randomSeed = properties.getInt("randomSeed");
        randomGenerator = new Random();
        randomGenerator.setSeed(randomSeed);
    }

    // for unit tests
    public void forceRandomGenerator(Random r) {
        randomGenerator = r;
    }


    public void printToFileInAllAvailableFormats(boolean with2d, boolean with3d) throws MazeGenerationException, IOException {
        final String fileName = getProperties().getString("fileName");
        for (MazeOutputFormat format : MazeOutputFormat.values()) {
            if (with3d && format.is3D() || with2d && format.is2D()) {
                IMazePrinter pr = constructMazePrinter(format);
                if (pr != null) {
                    FileOutputStream stream = new FileOutputStream(fileName + "." + format.fileExtension());
                    pr.print(stream);
                    stream.close();
                }
            }
        }
    }

    public MazeResult printMazeInFormat(MazeOutputFormat format) throws MazeGenerationException, IOException {
        IMazePrinter pr = constructMazePrinter(format);
        if (pr != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pr.print(stream);
            stream.close();
            final String fileName = getProperties().getString("fileName");
            if (format == MazeOutputFormat.json2d) {
                // svg packed in json
                String jsonEncodedSvg = JSONObject.quote(stream.toString());
                Point2DInt canvasSize = pr.get2dCanvasSize();
                String json = String.format("{ \"svg\": %s, \"width\": %d, \"height\": %d}",
                        jsonEncodedSvg, canvasSize.getX(), canvasSize.getY());
                return new MazeResult(fileName, format, json.getBytes());
            } else {
                return new MazeResult(fileName, format, stream.toByteArray());
            }
        }
        return null;
    }

    private IMazePrinter constructMazePrinter(MazeOutputFormat format) {
        IMazePrinter pr = null;

        switch (format) {
            case json2d:
            case svg:
            case pdf:
                pr = new SvgMazePrinter(getProperties(), format, getPathShapes());
                break;
            case json3d:
                pr = new ThreeJs3DPrinter(getModel3d());
                break;
            case scad:
                pr = new OpenScad3DPrinter(getModel3d());
                break;
            case stl:
                pr = new StlMazePrinter(getModel3d());
                break;
        }
        return pr;
    }


    public static MazeValidationErrors noErrors = new MazeValidationErrors();
}