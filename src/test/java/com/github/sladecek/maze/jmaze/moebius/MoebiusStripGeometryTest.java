package com.github.sladecek.maze.jmaze.moebius;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.sladecek.maze.jmaze.geometry.Point3D;

public class MoebiusStripGeometryTest {

    private static final double delta = 0.002;

    @Before
    public void setUp() throws Exception {
        final double circumferenceInmm = 1256.637d;  // 2*pi*2*100
        msg = new MoebiusStripGeometry(circumferenceInmm);
    }
    
    @Test
    public void testTransform() {
        Point3D p = new Point3D(628.32d, 20d, 10d);
        Point3D p2 = msg.transform(p);
        assertEquals(90.00d, p2.getX(), delta);
        assertEquals(0, p2.getY(), delta);
        assertEquals(-20.0, p2.getZ(), delta);
    }

    @Test
    public void testGetCircumferenceInmm() {
        assertEquals(1256.637d, msg.getCircumferenceInmm(), delta);
    }

    @Test
    public void testGetRadiusInmm() {
        assertEquals(100d, msg.getRadiusInmm(), delta);
    }

    @Test
    public void testCmputeTheta() {
        assertEquals(2*Math.PI, msg.computeTheta(628.32d), delta);
    }
    
    private MoebiusStripGeometry msg;
}
