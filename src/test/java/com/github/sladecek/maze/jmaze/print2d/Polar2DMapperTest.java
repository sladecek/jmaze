package com.github.sladecek.maze.jmaze.print2d;

import static org.junit.Assert.assertEquals;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import org.junit.Test;

public class Polar2DMapperTest {

    @Test
    public void testMapPoint() {
        Polar2DMapper m = new Polar2DMapper(new Point2DInt(100, 50), 3);
        Point2DInt p = m.mapPoint(new Point2DInt(Point2DInt.ANGLE_2PI/4, 100));
        assertEquals(100, p.getX());
        assertEquals(50 + 100 * 3, p.getY());
        
    }

    @Test
    public void testMapLength() {
        Polar2DMapper m = new Polar2DMapper(new Point2DInt(20, 30), 3);
        assertEquals(3*7, m.mapLength(7));
    }

}
