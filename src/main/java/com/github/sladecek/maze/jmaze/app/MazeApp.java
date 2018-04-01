package com.github.sladecek.maze.jmaze.app;

import com.github.sladecek.maze.jmaze.maze.AllMazeTypes;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;

import java.util.logging.*;
//REV1
/**
 * Main program.
 */
class MazeApp {

    public static void main(String[] args) {
        new MazeApp().printTestMaze(args);
    }

    /**
     * Prints maze in all possible formats.
     */
    private void printTestMaze(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%5$s%n");
        LogManager.getLogManager().reset();
        LOG.setLevel(Level.SEVERE);
        try {
            // logging
            FileHandler fh = new FileHandler("maze.log");
            LOG.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());

            CommandLineArguments argumentParser = new CommandLineArguments(args);
            argumentParser.parseArguments();
            if (argumentParser.hasErrors() || argumentParser.isUsageRequired()) {
                System.out.print(argumentParser.getUsage());
                return;
            }

            final String mazeType = argumentParser.getMazeType();
            MazeDescription description = AllMazeTypes.findByName(mazeType);
            if (description == null) {
                System.out.println("Unknown maze type '"+mazeType+"' . Known types are: "+AllMazeTypes.listOfNamesAsString());
                return;
            }

            if (argumentParser.isPropertiesListRequired()) {
                MazeProperties properties = description.getOwnProperties();
                System.out.print(properties.toUserString());
            } else {
                Maze maze = (Maze)description.getMazeClass().newInstance();

                MazeProperties p = description.getDefaultProperties().deepCopy();
                p.put("name", mazeType);
                p.put("fileName", "maze_"+mazeType);
                p.updateFromStrings(argumentParser.getProperties());

                maze.setProperties(p);
                maze.makeMazeAllSteps(true);
                maze.printToFileInAllAvailableFormats(
                        description.canBePrintedIn2D(),
                        description.canBePrintedIn3D());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Logging facility.
     */
    private static final Logger LOG = Logger.getLogger("maze");

}
