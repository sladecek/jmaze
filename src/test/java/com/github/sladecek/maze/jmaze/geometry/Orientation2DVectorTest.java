package com.github.sladecek.maze.jmaze.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Orientation2DVectorTest {

    private static final double delta = 0.0000001;

    @Test
    public void testGetOrthogonal() {

        final double x = 4d;
        final double y = -27d;
        OrientationVector2D ov1 = new OrientationVector2D(x, y);
        OrientationVector2D ov2 = ov1.getOrthogonal();
        double scalarProduct = ov2.getX() * x + ov2.getY() * y;
        assertEquals(0d, scalarProduct, delta);

    }

    @Test
    public void testToString() {
        OrientationVector2D o = new OrientationVector2D(1, 2);
        assertEquals("OrientationVector2D [x=1.0, y=2.0]", o.toString());
    }

}
