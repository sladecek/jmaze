package com.github.sladecek.maze.jmaze.makers.rectangular;

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

        MazeProperties p = new RectangularMazeDescription().getDefaultProperties();
        p.put("width", 2);
        p.put("height", 2);

        maze.setProperties(p);

        maze.makeMazeAllSteps(true);
    }


    @Test
    public void testStl() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        StlMazePrinter printer = new StlMazePrinter(maze.getModel3d());
        printer.print(stream);
        stream.close();
        String s = stream.toString();
        assertEquals(19335, s.length());


    }
    private RectangularMaze maze;
}
