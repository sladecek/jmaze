package com.github.sladecek.maze.jmaze.makers.moebius;

import com.github.sladecek.maze.jmaze.geometry.Point2DDbl;
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.ILocalCoordinateSystem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the <code>{@link Moebius3dMapper}</code> class.
 */
public class Moebius3dMapperTest {
    
    @Before
    public void setup() {
        mapper = new Moebius3dMapper(4,16, 10.0, 2.2);
    }

    @Test
    public void isAltitudeVisible() throws Exception {
        assertFalse(mapper.isAltitudeVisible(Altitude.FRAME));
        assertFalse(mapper.isAltitudeVisible(Altitude.GROUND));
        assertTrue(mapper.isAltitudeVisible(Altitude.FLOOR));
        assertTrue(mapper.isAltitudeVisible(Altitude.CEILING));
    }

    @Test
    public void testConstruction() throws Exception {
        assertEquals(16*(10+2.2), mapper.getLength(), epsilon);
        assertEquals(12.2, mapper.getCellStep(), epsilon);
    }

    @Test
    public void imageComputation_0() throws Exception {
        Point3D img = mapper.computeImagePoint(new Point2DDbl(0, 2), Altitude.GROUND);
        assertEquals(0, img.getX(), epsilon);
        assertEquals(0, img.getY(), epsilon);
        assertEquals(0, img.getZ(), epsilon);
    }

    @Test
    public void imageComputationAcrossDown() throws Exception {
        Point3D img = mapper.computeImagePoint(new Point2DDbl(0, 1), Altitude.GROUND);
        assertEquals(0, img.getX(), epsilon);
        assertEquals(-12.2, img.getY(), epsilon);
        assertEquals(0, img.getZ(), epsilon);
    }

    @Test
    public void imageComputationAcrossUp() throws Exception {
        Point3D img = mapper.computeImagePoint(new Point2DDbl(0, 4), Altitude.GROUND);
        assertEquals(0, img.getX(), epsilon);
        assertEquals(2*12.2, img.getY(), epsilon);
        assertEquals(0, img.getZ(), epsilon);
    }

    @Test
    public void imageComputationAlongDown() throws Exception {
        Point3D img = mapper.computeImagePoint(new Point2DDbl(-1, 2), Altitude.GROUND);
        assertEquals(-12.2, img.getX(), epsilon);
        assertEquals(0, img.getY(), epsilon);
        assertEquals(0, img.getZ(), epsilon);
    }

    @Test
    public void imageComputationAlongUp() throws Exception {
        Point3D img = mapper.computeImagePoint(new Point2DDbl(1, 2), Altitude.GROUND);
        assertEquals(12.2, img.getX(), epsilon);
        assertEquals(0, img.getY(), epsilon);
        assertEquals(0, img.getZ(), epsilon);
    }

    @Test
    public void imageComputationCeiling() throws Exception {
        Point3D img = mapper.computeImagePoint(new Point2DDbl(0, 2), Altitude.CEILING);
        assertEquals(0, img.getX(), epsilon);
        assertEquals(0, img.getY(), epsilon);
        assertEquals(2, mapper.mapAltitude(Altitude.CEILING), epsilon);
        assertEquals(2, img.getZ(), epsilon);
    }

    @Test
    public void map() throws Exception {
        // mapping is tested in MoebiusStripGeometryTest; image computation tested above
        // here only function composition is tested
        Point2DDbl p = new Point2DDbl(-1.222, 1.333);
        Point3D img = mapper.computeImagePoint(p, Altitude.FLOOR);
        Point3D r1 = mapper.getGeometry().transform(img);

        Point3D r2 = mapper.map(p, Altitude.FLOOR);

        assertEquals(r1.getX(), r2.getX(), epsilon);
        assertEquals(r1.getY(), r2.getY(), epsilon);
        assertEquals(r1.getZ(), r2.getZ(), epsilon);
    }


    @Test
    public void createLocalCoordinateSystem() throws Exception {
        ILocalCoordinateSystem lcs=mapper.createLocalCoordinateSystem(new Point2DInt(0,2));
        Point2DDbl lp = lcs.transformToLocal(new Point2DDbl(15,3));
        assertEquals(-1.0, lp.getX(), epsilon);
        assertEquals(3, lp.getY(), epsilon);
    }

    private static final double epsilon = 0.0000001;

    private Moebius3dMapper mapper;
}