package com.github.sladecek.maze.jmaze.print2d;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.geometry.Point2D;

public class Polar2DMapperTest {

    @Test
    public void testMapPoint() {
        Polar2DMapper m = new Polar2DMapper(new Point2D(100, 50), 3);
        Point2D p = m.mapPoint(new Point2D(Point2D.ANGLE_2PI/4, 100));
        assertEquals(100, p.getX());
        assertEquals(50 + 100 * 3, p.getY());
        
    }

    @Test
    public void testMapLength() {
        Polar2DMapper m = new Polar2DMapper(new Point2D(20, 30), 3);
        assertEquals(3*7, m.mapLength(7));
    }

}
