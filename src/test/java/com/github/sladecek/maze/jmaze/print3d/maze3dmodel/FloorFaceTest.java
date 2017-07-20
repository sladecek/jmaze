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
        assertEquals(Altitude.FLOOR, ff.getAltitude());
        ff.setAltitude(Altitude.FRAME);
        assertEquals(Altitude.FRAME, ff.getAltitude());
        assertEquals(Altitude.FRAME.getValue(), ff.getAltitude().getValue());
    }
}