package com.github.sladecek.maze.jmaze.moebius;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print3d.output.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.output.StlMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.output.ThreeJs3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.ModelFromShapes;
import com.github.sladecek.maze.jmaze.print3d.*;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

import java.io.FileOutputStream;
import java.util.Random;
import java.util.logging.*;

/***
 * Command line application generating a Moebius maze..
 */
public final class TestAppMoebius {

    public static void main(final String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%5$s%n");
        LogManager.getLogManager().reset();
        LOG.setLevel(Level.INFO);
        try {
            FileHandler fh = new FileHandler("maze.log");
            LOG.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());

            final int widthCells = 4;
            final int lengthCells = 40;
            MoebiusMaze maze = new MoebiusMaze(widthCells, lengthCells);
            final Random randomGenerator = new Random();
            randomGenerator.setSeed(0);
            IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);
            MazeRealization r = g.generateMaze(maze);

            Maze3DSizes sizes = new Maze3DSizes();
            sizes.setCellSizeInmm(2);  // TODO
            sizes.setWallHeightInmm(30);

            IPrintStyle colors = new DefaultPrintStyle();

//maze.setDebug(true);
            ShapeContainer shapes = maze.applyRealization(r);
            for (IMazeShape2D sh: shapes.getShapes()) {
                System.out.println(sh);
            }

            double approxRoomSizeInmm = 3;
            Moebius3dMapper mapper = new Moebius3dMapper(sizes, widthCells, lengthCells);
            mapper.configureAltitudes(sizes);

            Model3d model = ModelFromShapes.make(shapes, mapper, sizes, colors, true);

            final boolean printInJs = false;
            final boolean printInScad = true;
            final boolean printStl = true;


            if (printInJs) {
                FileOutputStream f;
                f = new FileOutputStream("maze-moebius.js");
                new ThreeJs3DPrinter().printModel(model, f);
            }
            if (printInScad) {
                FileOutputStream f = new FileOutputStream("maze-moebius.scad");
                new OpenScad3DPrinter().printModel(model, f);
            }
            if (printStl) {
                FileOutputStream f = new FileOutputStream("maze-moebius.stl");
                new StlMazePrinter().printModel(model, f);
                f.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final Logger LOG = Logger.getLogger("maze");
}
