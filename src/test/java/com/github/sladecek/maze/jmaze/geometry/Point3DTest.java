package com.github.sladecek.maze.jmaze.geometry;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class Point3DTest {
    private static final double delta = 0.0000001;
    
    @Test
    public void testMidpoint() {
        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(2, 4, 13);
        Point3D p3 = Point3D.midpoint(p1, p2);
        assertEquals(1.5, p3.getX(), delta);
        assertEquals(3, p3.getY(), delta);
        assertEquals(8, p3.getZ(), delta);
    }
    

    @Test
    public void testSetX() {
        Point3D p = new Point3D(0, 1, 2);
        p.setX(4d);
        assertEquals(4d, p.getX(), delta);
    }

    @Test
    public void testSetY() {
        Point3D p = new Point3D(0, 1, 2);
        p.setY(4d);
        assertEquals(4d, p.getY(), delta);
    }

    @Test
    public void testSetZ() {
        Point3D p = new Point3D(0, 1, 2);
        p.setZ(4d);
        assertEquals(4d, p.getZ(), delta);
    }

    @Test
    public void testToString() {
        Point3D p = new Point3D(0, 1, 2);
        assertEquals("Point [x=0.0, y=1.0, z=2.0]", p.toString());
    }

}
