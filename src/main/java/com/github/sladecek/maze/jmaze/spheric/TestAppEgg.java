package com.github.sladecek.maze.jmaze.spheric;

import java.io.FileNotFoundException;
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
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print3d.ModelFromShapes;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.output.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.output.StlMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.output.ThreeJs3DPrinter;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;


final class TestAppEgg {
    private TestAppEgg() {
    }

    private static final Logger LOG = Logger.getLogger("maze");

    public static void main(final String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%5$s%n");
//        "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");


        LogManager.getLogManager().reset();
        LOG.setLevel(Level.INFO);
        try {
            FileHandler fh = new FileHandler("maze.log");
            LOG.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());

            final int equatorCells = 8; // must be power of 2
            EggGeometry egg = new EggGeometry(10, 10, 0.2);

            EggMaze maze = new EggMaze(egg, equatorCells);
            final Random randomGenerator = new Random();
            randomGenerator.setSeed(0);
            IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);

            MazeRealization real = g.generateMaze(maze);

            Maze3DSizes sizes = new Maze3DSizes();
            sizes.setCellSizeInmm(0.1);
            sizes.setWallHeightInmm(0.3);

            sizes.setInnerWallToPixelRatio(5);

            IPrintStyle colors = new DefaultPrintStyle();

            ShapeContainer shapes = maze.applyRealization(real);


            double approxRoomSizeInmm = 3;
            final String fileName = "maze-egg";

            for (IMazeShape sh: shapes.getShapes()) {
                LOG.info(sh.toString());

            }


            IMaze3DMapper mapper = new Egg3dMapper(egg, maze);
            Model3d model = ModelFromShapes.make(shapes, mapper, sizes, colors, false);


            final boolean printInJs = true;
            final boolean printInScad = true;
            final boolean printStl = true;


            if (printInJs) {
                FileOutputStream fJs = new FileOutputStream(fileName + ".js");
                new ThreeJs3DPrinter().printModel(model, fJs);
                fJs.close();
            }
            if (printInScad) {
                FileOutputStream fScad = new FileOutputStream(fileName + ".scad");
                new OpenScad3DPrinter().printModel(model, fScad);
                fScad.close();
            }
            if (printStl) {
                FileOutputStream fStl = new FileOutputStream(fileName + ".stl");
                new StlMazePrinter().printModel(model, fStl);
                fStl.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
            EggBlockMaker maker = new EggBlockMaker(maze, shapes, sizes, colors, egg,
                    equatorCells);

            final boolean printInJs = true;
            final boolean printInScad = true;
            final boolean showSolution = true;
            
            if (printInJs) {
                FileOutputStream f = new FileOutputStream("maze-egg.js");
                new ThreeJs3DPrinter(colors).printBlocks(maker, showSolution, f);
            }
            if (printInScad) {
                FileOutputStream f = new FileOutputStream("maze-egg.scad");
                new OpenScad3DPrinter().printBlocks(maker, showSolution,  f);
            }

        } catch (SecurityException | IOException | MazeGenerationException e) {
            e.printStackTrace();
        }
*/
        }
}
