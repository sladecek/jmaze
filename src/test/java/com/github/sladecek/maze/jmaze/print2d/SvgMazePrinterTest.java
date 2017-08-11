package com.github.sladecek.maze.jmaze.print2d;

import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.rectangular.RectangularMaze;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import java.io.ByteArrayOutputStream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Tests <code>SvgMazePrinter</code> class.
 */
public class SvgMazePrinterTest {

    @Before
    public void setUp() throws Exception {
        maze = new RectangularMaze();

        MazeProperties p = maze.getDefaultProperties();
        p.put("width", 5);
        p.put("height", 3);

        maze.setProperties(p);
        final boolean with3d = false;
        maze.makeMazeAllSteps(with3d);
        printer = new SvgMazePrinter(p);
    }


    @Test
    public void printShapesSvg() throws Exception {
        SvgDocument svg = printer.createSvgDocument(maze.getPathShapes());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        printer.printSvgDocument(MazeOutputFormat.svg, stream, svg);
        stream.close();
        String s = stream.toString();
        Assert.assertEquals(2811, s.length());
    }


    @Test
    public void printShapesPdf() throws Exception {
        SvgDocument svg = printer.createSvgDocument(maze.getPathShapes());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        printer.printSvgDocument(MazeOutputFormat.pdf, stream, svg);
        stream.close();
        String s = stream.toString();
        Assert.assertEquals(1408, s.length());
    }

    @Test
    public void createSvgDocument() throws Exception {
        SvgDocument svg = printer.createSvgDocument(maze.getPathShapes());
        assertNotNull(svg);
        assertEquals(37, countNodes(svg.getDocument()));
    }

    private int countNodes(Node n) {
        int sum = 1;
        for (int i = 0; i < n.getChildNodes().getLength(); i++) {
            sum += countNodes(n.getChildNodes().item(i));
        }
        return sum;
    }

    private RectangularMaze maze;
    private SvgMazePrinter printer;

}