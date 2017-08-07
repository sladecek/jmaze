package com.github.sladecek.maze.jmaze.rectangular;

import com.github.sladecek.maze.jmaze.print3d.output.StlMazePrinter;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Rectangular3DMazeTest {

    @Before
    public void setUp() throws Exception {

        maze = new RectangularMaze();

        MazeProperties p = maze.getDefaultProperties();
        p.put("width", 2);
        p.put("height", 2);

        maze.setProperties(p);

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
