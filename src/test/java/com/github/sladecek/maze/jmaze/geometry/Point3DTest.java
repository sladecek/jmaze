package com.github.sladecek.maze.jmaze.geometry;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.geometry.Point3D;


public class Point3DTest {
    static final double delta = 0.0000001;
    
    @Test
    public void testMidpoint() {
        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(2, 4, 13);
        Point3D p3 = Point3D.midpoint(p1, p2);
        assertEquals(1.5, p3.getX(), delta);
        assertEquals(3, p3.getY(), delta);
        assertEquals(8, p3.getZ(), delta);
    }
    
 

}
