package com.github.sladecek.maze.jmaze.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Point2DIntTest {

    private static final double delta = 0.0000001;
    
    @Test
    public void testGetAngle() {
        final int y = 10;
        final int x = Point2DInt.ANGLE_2PI / 2;
        Point2DInt p = new Point2DInt(x, y);
        assertEquals(x, p.getAngle());
    }

    @Test
    public void testGetAngleRad() {
        final int y = 10;
        final int x = Point2DInt.ANGLE_2PI / 2;
        Point2DInt p = new Point2DInt(x, y);
        assertEquals(Math.PI, p.getAngleRad(), delta);
    }

    @Test
    public void testGetDiameter() {
        final int y = 10;
        final int x = Point2DInt.ANGLE_2PI / 2;
        Point2DInt p = new Point2DInt(x, y);
        assertEquals(y, p.getDiameter());
    }

}
