package com.github.sladecek.maze.jmaze.rectangular;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print3d.ModelFromShapes;
import com.github.sladecek.maze.jmaze.print3d.PlanarMapper;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.output.StlMazePrinter;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class Rectangular3DMazeTest {

    @Before
    public void setUp() throws Exception {

        maze = new RectangularMaze(2, 2);
        maze.makeMazeAllSteps(true);
    }


    @Test
    public void testStl() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        StlMazePrinter printer = new StlMazePrinter();
        printer.printModel(maze.getModel3d(), stream);
        stream.close();
        String s = stream.toString();
        assertEquals(23007, s.length());


    }
    private RectangularMaze maze;
}
