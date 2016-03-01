package com.github.sladecek.maze.jmaze.spheric;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print3d.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.ThreeJs3DPrinter;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.util.MazeGenerationException;

final class TestAppEgg {
    private TestAppEgg() {
    }

    private static final Logger LOG = Logger.getLogger("maze.jmaze");

    public static void main(final String[] args) {
        LogManager.getLogManager().reset();
        LOG.setLevel(Level.INFO);
        try {
            FileHandler fh = new FileHandler("maze.log");
            LOG.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());

            final int equatorCells = 32; // must be power of 2
            EggGeometry egg = new EggGeometry(5, 5, 0);

            EggMaze maze = new EggMaze(egg, equatorCells);
            final Random randomGenerator = new Random();
            randomGenerator.setSeed(0);
            IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);

            MazeRealization real = g.generateMaze(maze);

            Maze3DSizes sizes = new Maze3DSizes();
            sizes.setCellSizeInmm(0.1);
            sizes.setBaseThicknessInmm(0.03);
            sizes.setWallHeightInmm(0.3);
            sizes.setInnerWallToCellRatio(0.05);

            IPrintStyle colors = new DefaultPrintStyle();

            ShapeContainer shapes = maze.applyRealization(real);

            EggBlockMaker maker = new EggBlockMaker(maze, shapes, sizes, colors, egg,
                    equatorCells);

            final boolean printInJs = true;
            final boolean printInScad = true;
            if (printInJs) {
                FileOutputStream f = new FileOutputStream("maze-egg.js");
                new ThreeJs3DPrinter().printBlocks(maker, f);
            }
            if (printInScad) {
                FileOutputStream f = new FileOutputStream("maze-egg.scad");
                new OpenScad3DPrinter().printBlocks(maker, f);
            }

        } catch (SecurityException | IOException | MazeGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
