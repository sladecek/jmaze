package com.github.sladecek.maze.jmaze.app;

import com.github.sladecek.maze.jmaze.maze.BaseMaze;
import com.github.sladecek.maze.jmaze.maze.MazeFactory;
import com.github.sladecek.maze.jmaze.print2d.MazeOutputFormat;
import com.github.sladecek.maze.jmaze.print2d.SvgMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.output.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.output.StlMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.output.ThreeJs3DPrinter;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.util.MazeGenerationException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.*;

/**
 * Generate any maze..
 */
public class MazeApp {

    public static void main(String[] args) {
        new MazeApp().printTestMaze(args);
    }

    /**
     * Prints a maze in all possible formats.
     */
    public final void printTestMaze(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%5$s%n");
        LogManager.getLogManager().reset();
        LOG.setLevel(Level.INFO);
        try {
            // logging
            FileHandler fh = new FileHandler("maze.log");
            LOG.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());

            CommandLineArguments cla = new CommandLineArguments(args);
            cla.parseArguments();
            if (cla.hasErrors() || cla.isUsageRequired()) {
                System.out.print(cla.getUsage());
                return;
            }
            BaseMaze maze = new MazeFactory().newMaze(cla.getMazeType());

            if (cla.isPropertiesListRequired()) {
                MazeProperties properties = maze.getDefaultProperties();
                System.out.print(properties.toUserString());
            } else {
                maze.getProperties().updateFromStrings(cla.getProperties());
                maze.makeMazeAllSteps(true);

                final String fileName = "maze-" + maze.getName();
                // print 2D
                if (maze.canBePrintedIn2D()) {
                    SvgMazePrinter printer = new SvgMazePrinter(maze.getProperties());

                    FileOutputStream sSvg = new FileOutputStream(fileName + ".svg");
                    printer.printShapes(maze.getPathShapes(), MazeOutputFormat.svg, sSvg);
                    FileOutputStream fPdf = new FileOutputStream(fileName + ".pdf");
                    printer.printShapes(maze.getPathShapes(), MazeOutputFormat.pdf, fPdf);
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



