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
        LOG.setLevel(Level.SEVERE);
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
                maze.printAccordingToProperties();
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



