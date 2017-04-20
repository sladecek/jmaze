package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for FloorFace.
 */
public class FloorFaceTest {
    @Test
    public void setAltitude() throws Exception {
        FloorFace ff = new FloorFace();
        assertEquals(FloorFace.FLOOR_ALTITUDE, ff.getAltitude());
        ff.setAltitude(27);
        assertEquals(27, ff.getAltitude());
    }
}