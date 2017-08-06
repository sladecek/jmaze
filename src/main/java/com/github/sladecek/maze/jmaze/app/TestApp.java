package com.github.sladecek.maze.jmaze.app;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.github.sladecek.maze.jmaze.circular.Circular2DMaze;
import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.hexagonal.Hexagonal2DMaze;
import com.github.sladecek.maze.jmaze.maze.BaseMaze;
import com.github.sladecek.maze.jmaze.maze.IMaze;
import com.github.sladecek.maze.jmaze.print2d.MazeOutputFormat;
import com.github.sladecek.maze.jmaze.print2d.SvgMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print3d.ModelFromShapes;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.output.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.output.StlMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.output.ThreeJs3DPrinter;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;

import com.github.sladecek.maze.jmaze.rectangular.RectangularMaze;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.util.MazeGenerationException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.function.Supplier;
import java.util.logging.*;

/**
 * Generate any maze..
 */
public class TestApp {

    /**
     * Prints a maze generated by an external maze provider to a .jpg file.
     *
     * @param fileName     name of the output file.
     * @param mazeProvider external function that generates the maze.
     */
    public final void printTestMaze(final Supplier<BaseMaze> mazeProvider) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%5$s%n");
        LogManager.getLogManager().reset();
        LOG.setLevel(Level.INFO);
        try {
            // logging
            FileHandler fh = new FileHandler("maze.log");
            LOG.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());

            BaseMaze maze = mazeProvider.get();
            maze.makeMazeAllSteps(true);

            final String fileName = "maze-" + maze.getName();
            // print 2D
            if (maze.canBePrintedIn2D()) {
                SvgMazePrinter printer = new SvgMazePrinter();

                FileOutputStream sSvg = new FileOutputStream(fileName + ".svg");
                printer.printShapes(maze.getRealisedModel(), MazeOutputFormat.svg, sSvg);
                FileOutputStream fPdf = new FileOutputStream(fileName+ ".pdf");
                printer.printShapes(maze.getRealisedModel(), MazeOutputFormat.pdf, fPdf);
            }

            IMaze3DMapper mapper = maze.create3DMapper();
            if (mapper != null) {
                final boolean printInJs = false;
                final boolean printInScad = true;
                final boolean printStl = true;


                if (printInJs) {
                    FileOutputStream fJs = new FileOutputStream(fileName + ".js");
                    new ThreeJs3DPrinter().printModel(maze.getModel3d(), fJs);
                    fJs.close();
                }
                if (printInScad) {
                    FileOutputStream fScad = new FileOutputStream(fileName + ".scad");
                    new OpenScad3DPrinter().printModel(maze.getModel3d(), fScad);
                    fScad.close();
                }
                if (printStl) {
                    FileOutputStream fStl = new FileOutputStream(fileName + ".stl");
                    new StlMazePrinter().printModel(maze.getModel3d(), fStl);
                    fStl.close();
                }
            }
        } catch (SecurityException | IOException | MazeGenerationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logging facility.
     */
    private static final Logger LOG = Logger.getLogger("maze");

    public static void main(String[] args) {
        new TestApp().printTestMaze( () -> {

            // return new RectangularMaze(2, 2);
            //return new Circular2DMaze(4);
            return new Hexagonal2DMaze(4);
        });
    }


}
