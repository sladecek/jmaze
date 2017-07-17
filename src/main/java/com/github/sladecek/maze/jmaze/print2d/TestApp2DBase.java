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
import com.github.sladecek.maze.jmaze.print3d.output.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.output.StlMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.output.ThreeJs3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.ModelFromShapes;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.*;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.util.MazeGenerationException;

/**
 * Base class for 2D test applications.
 */
public class TestApp2DBase {

    /**
     * Prints a maze generated by an external maze provider to a .jpg file.
     *
     * @param fileName     name of the output file.
     * @param mazeProvider external function that generates the maze.
     */
    public final void printTestMaze(final String fileName,
                                    final Supplier<Maze> mazeProvider) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%5$s%n");
        LogManager.getLogManager().reset();
        LOG.setLevel(Level.INFO);
        try {
            // logging
            FileHandler fh = new FileHandler("maze.log");
            LOG.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
            // construct maze generator
            Maze maze = mazeProvider.get();

            final Random randomGenerator = new Random();
            randomGenerator.setSeed(0);
            IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);

            // generate
            MazeRealization realization = g.generateMaze(maze);
            ShapeContainer shapes = maze.applyRealization(realization);

            // print 2D
            SvgMazePrinter printer = new SvgMazePrinter();
            FileOutputStream sSvg = new FileOutputStream(fileName + ".svg");
            printer.printShapes(shapes, MazeOutputFormat.svg, sSvg);
            FileOutputStream fPdf = new FileOutputStream(fileName + ".pdf");
            printer.printShapes(shapes, MazeOutputFormat.pdf, fPdf);

            // print 3D
            double approxRoomSizeInmm = 3;
            Maze3DSizes sizes = new Maze3DSizes();
            sizes.setCellSizeInmm(2);  // TODO

            IPrintStyle colors = new DefaultPrintStyle();

            IMaze3DMapper mapper = new PlanarMapper();
            Model3d model = ModelFromShapes.make(shapes, mapper, sizes, colors);

            final boolean printInJs = true;
            final boolean printInScad = true;
            final boolean printStl = true;


            if (printInJs) {
                FileOutputStream fJs = new FileOutputStream(fileName+".js");
                new ThreeJs3DPrinter().printModel(model, fJs);
                fJs.close();
            }
            if (printInScad) {
                FileOutputStream fScad = new FileOutputStream(fileName+".scad");
                new OpenScad3DPrinter().printModel(model, fScad);
                fScad.close();
            }
            if (printStl) {
                FileOutputStream fStl = new FileOutputStream(fileName+".stl");
                new StlMazePrinter().printModel(model, fStl);
                fStl.close();
            }

        } catch (SecurityException | IOException | MazeGenerationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logging facility.
     */
    private static final Logger LOG = Logger.getLogger("maze");

}
