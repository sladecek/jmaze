package com.github.sladecek.maze.jmaze.generator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MazeRealizationTest {

    @Test
    public void testMazeRealization() {
        MazeRealization r = new MazeRealization(3);
        assertEquals(true, r.isWallClosed(0));
        assertEquals(true, r.isWallClosed(1));
        assertEquals(true, r.isWallClosed(2));
    }

    @Test
    public void testSetWallClosed() {
        MazeRealization r = new MazeRealization(3);
        assertEquals(true, r.isWallClosed(1));
        r.setWallClosed(1, false);
        assertEquals(false, r.isWallClosed(1));
        r.setWallClosed(1, true);
        assertEquals(true, r.isWallClosed(1));
    }

    @Test
    public void testPrintClosedWalls() {
        MazeRealization r = new MazeRealization(3);
        r.setWallClosed(1, false);
        assertEquals("0 2", r.printClosedWalls());
    }

}
