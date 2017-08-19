package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.generator.MazePath;
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test <code>FloorShape</code> class.
 */
public class FloorShapeTest {

    @Before
    public void setup() {
        fs = new FloorShape(7, new Point2DInt(11, 22));
        fs.setWallId(2);
        pth = new MazePath(3, 0, 2);
    }

    @Test
    public void applyPathDefault() throws Exception {
        assertEquals(false, fs.isHole());
    }

    @Test
    public void applyPathClosed() throws Exception {
        pth.setWallClosed(2, true);
        fs.applyPath(pth);
        assertEquals(false, fs.isHole());
    }

    @Test
    public void applyPathOpened() throws Exception {
        pth.setWallClosed(2, false);
        fs.applyPath(pth);
        assertEquals(true, fs.isHole());
    }

    private FloorShape fs;
    private MazePath pth;
}