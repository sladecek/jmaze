package com.github.sladecek.maze.jmaze.print2d;

import static org.junit.Assert.assertEquals;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import org.junit.Test;

public class Cartesian2DMapperTest {

    @Test
    public void testMapPoint() {
        Cartesian2DMapper m = new Cartesian2DMapper(new Point2DInt(20, 30), 3, 3);
        Point2DInt p = m.mapPoint(new Point2DInt(10, 100));
        assertEquals(20 + 10 * 3, p.getX());
        assertEquals(30 + 100 * 3, p.getY());

    }

    @Test
    public void testMapLength() {
        Cartesian2DMapper m = new Cartesian2DMapper(new Point2DInt(20, 30), 3, 3);
        assertEquals(3*7, m.mapLength(7));
    }

}
