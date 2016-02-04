package com.github.sladecek.maze.jmaze.print2d;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.function.Supplier;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

/**
 * Base class for 2D test applications
 * 
 */
public class TestApp2DBase {

    public void printTestMaze(String fileName,
            Supplier<Maze> mazeProvider) {
        LogManager.getLogManager().reset();
        LOG.setLevel(Level.INFO);
        try {
            FileHandler fh = new FileHandler("maze.log");
            LOG.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
            Maze maze = mazeProvider.get();
            final Random randomGenerator = new Random();
            randomGenerator.setSeed(0);
            IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);

            MazeRealization realization = g.generateMaze(maze);
            ShapeContainer shapes = maze.applyRealization(realization);

            SvgMazePrinter printer = new SvgMazePrinter();
            final boolean showSolution = true;
            FileOutputStream f = new FileOutputStream(fileName + ".svg");
            printer.printShapes(shapes, MazeOutputFormat.svg, f, showSolution);
            FileOutputStream fp = new FileOutputStream(fileName + ".pdf");
            printer.printShapes(shapes, MazeOutputFormat.pdf, fp, showSolution);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    private static final Logger LOG = Logger.getLogger("maze.jmaze");

}
