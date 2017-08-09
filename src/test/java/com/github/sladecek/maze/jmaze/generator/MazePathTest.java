package com.github.sladecek.maze.jmaze.generator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class MazePathTest {

    @Test
    public void testMazeRealization() {
        MazePath r = new MazePath(3, 0, 2);
        assertEquals(true, r.isWallClosed(0));
        assertEquals(true, r.isWallClosed(1));
        assertEquals(true, r.isWallClosed(2));
        assertEquals(0, r.getStartRoom());
        assertEquals(2, r.getTargetRoom());
    }

    @Test
    public void testSetWallClosed() {
        MazePath r = new MazePath(3, 0, 2);
        assertEquals(true, r.isWallClosed(1));
        r.setWallClosed(1, false);
        assertEquals(false, r.isWallClosed(1));
        r.setWallClosed(1, true);
        assertEquals(true, r.isWallClosed(1));
    }

    @Test
    public void testPrintClosedWalls() {
        MazePath r = new MazePath(3, 0, 2);
        r.setWallClosed(1, false);
        assertEquals("0 2", r.printClosedWalls());
    }

}
