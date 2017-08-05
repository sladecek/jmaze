package com.github.sladecek.maze.jmaze.rectangular;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print2d.MazeOutputFormat;
import com.github.sladecek.maze.jmaze.print2d.SvgMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print3d.ModelFromShapes;
import com.github.sladecek.maze.jmaze.print3d.PlanarMapper;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.output.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.output.StlMazePrinter;
import com.github.sladecek.maze.jmaze.print3d.output.ThreeJs3DPrinter;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class Rectangular3DMazeTest {

    @Before
    public void setUp() throws Exception {

        maze = new Rectangular2DMaze(2, 2);

        final Random randomGenerator = new Random();
        randomGenerator.setSeed(0);
        IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);

        // generate
        realization = g.generateMaze(maze);
        shapes = maze.applyRealization(realization);

        double approxRoomSizeInmm = 3;
        sizes = new Maze3DSizes();
        sizes.setCellSizeInmm(2);  // TODO

        colors = new DefaultPrintStyle();

        mapper = new PlanarMapper();
        model = ModelFromShapes.make(shapes, mapper, sizes, colors, false);
    }


    @Test
    public void testStl() throws IOException {
        IMaze3DMapper mapper = new PlanarMapper();
        model = ModelFromShapes.make(shapes, mapper, sizes, colors, false);


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        StlMazePrinter printer = new StlMazePrinter();
        printer.printModel(model, stream);
        stream.close();
        String s = stream.toString();
        assertEquals(22863, s.length());


    }
    private Rectangular2DMaze maze;
    private Model3d model;

    private Maze3DSizes sizes;
    private IPrintStyle colors;
    private IMaze3DMapper mapper;
    private ShapeContainer shapes;
    private MazeRealization realization;
}
