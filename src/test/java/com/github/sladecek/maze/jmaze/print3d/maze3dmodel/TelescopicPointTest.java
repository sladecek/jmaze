package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for TelescopicPoint.
 */
public class TelescopicPointTest {

    private IMaze3DMapper fakeMapper = new IMaze3DMapper() {
        @Override
        public Point3D map(Point3D image) {
            return new Point3D(image.getX() * 2, image.getY() * 4, image.getZ() * 7);
        }

        @Override
        public double inverselyMapLengthAt(Point3D center, double v) {
            return 1;
        }
    };


    @Test
    public void setUnequalAltitudes() throws Exception {
        TelescopicPoint pp = new TelescopicPoint(1,2);
        assertEquals(false, pp.areAltitudesDefined());

        pp.setAltitudesUsingMapper(fakeMapper, 10, 20);
        assertEquals(true, pp.areAltitudesDefined());

        assertEquals(20, pp.getHighAltitude());
        assertEquals(10, pp.getLowAltitude());

        final double epsilon = 0.01;

        assertEquals(1*2, pp.getCoord().getX(), epsilon);
        assertEquals(2*4, pp.getCoord().getY(), epsilon);
        assertEquals(7*20, pp.getCoord().getZ(), epsilon);

        assertEquals(1*2, pp.getHighAltitudePoint().getCoord().getX(), epsilon);
        assertEquals(2*4, pp.getHighAltitudePoint().getCoord().getY(), epsilon);
        assertEquals(7*20, pp.getHighAltitudePoint().getCoord().getZ(), epsilon);

        assertEquals(1*2, pp.getLowAltitudePoint().getCoord().getX(), epsilon);
        assertEquals(2*4, pp.getLowAltitudePoint().getCoord().getY(), epsilon);
        assertEquals(7*10, pp.getLowAltitudePoint().getCoord().getZ(), epsilon);

        assert(pp.getHighAltitudePoint() != pp.getLowAltitudePoint());
        assert(pp.getRod() != null);
        assert(pp.getRod().getP2() == pp.getLowAltitudePoint());
        assert(pp.getRod().getP1() == pp.getHighAltitudePoint());
        assert(pp.getRod().getP2() == pp.getOppositeRodPoint());
    }


    @Test
    public void setEqualAltitudes() throws Exception {
        TelescopicPoint pp = new TelescopicPoint(1,2);
        assertEquals(false, pp.areAltitudesDefined());

        pp.setAltitudesUsingMapper(fakeMapper, 10, 10);
        assertEquals(true, pp.areAltitudesDefined());

        assertEquals(10, pp.getHighAltitude());
        assertEquals(10, pp.getLowAltitude());

        final double epsilon = 0.01;

        assertEquals(1*2, pp.getCoord().getX(), epsilon);
        assertEquals(2*4, pp.getCoord().getY(), epsilon);
        assertEquals(7*10, pp.getCoord().getZ(), epsilon);

        assertEquals(1*2, pp.getHighAltitudePoint().getCoord().getX(), epsilon);
        assertEquals(2*4, pp.getHighAltitudePoint().getCoord().getY(), epsilon);
        assertEquals(7*10, pp.getHighAltitudePoint().getCoord().getZ(), epsilon);

        assertEquals(1*2, pp.getLowAltitudePoint().getCoord().getX(), epsilon);
        assertEquals(2*4, pp.getLowAltitudePoint().getCoord().getY(), epsilon);
        assertEquals(7*10, pp.getLowAltitudePoint().getCoord().getZ(), epsilon);

        assert(pp.getHighAltitudePoint() == pp.getLowAltitudePoint());
        assert(pp.getOppositeRodPoint() == pp);
        assert(pp.getRod() == null);
    }

//    @Test
    public void setAltitudesRepeat() throws Exception {
        TelescopicPoint pp = new TelescopicPoint(1,2);
        assertEquals(false, pp.areAltitudesDefined());

        pp.setAltitudesUsingMapper(fakeMapper, 10, 10);
        pp.setAltitudesUsingMapper(fakeMapper, 10, 10);
        try {
            pp.setAltitudesUsingMapper(fakeMapper, 10, 11);
        } catch (IllegalStateException e) {
            return;
        }
        fail("Expected exception");
    }


    @Test
    public void setAltitudesReverse() throws Exception {
        TelescopicPoint pp = new TelescopicPoint(1,2);
        assertEquals(false, pp.areAltitudesDefined());

        try {
            pp.setAltitudesUsingMapper(fakeMapper, 20, 11);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("Expected exception");
    }
}