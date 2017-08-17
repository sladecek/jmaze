package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point3D;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test class for MFace
 */
public class MFaceVisitorTest {

    @Test
    public void testVisitInNaturalOrder() {
        e12 = new MEdge(new MPoint(p1), new MPoint(p2));
        e34 = new MEdge(new MPoint(p3), new MPoint(p4));
        e23 = new MEdge(new MPoint(p2), new MPoint(p3));
        e41 = new MEdge(new MPoint(p4), new MPoint(p1));

        e12.setRightFace(f);
        e34.setRightFace(f);
        e23.setRightFace(f);
        e41.setRightFace(f);

        compute();
        validateOrder(result, p2, p3, p4, p1);
    }

    @Test(expected = InternalError.class)
    public void testStartingEdgeInvalid() throws Exception {
        e12 = new MEdge(new MPoint(p1), new MPoint(p2));
        e34 = new MEdge(new MPoint(p3), new MPoint(p4));
        e23 = new MEdge(new MPoint(p2), new MPoint(p3));
        e41 = new MEdge(new MPoint(p4), new MPoint(p1));

        compute();
    }

    @Test()
    public void testStartingEdgeReverted() throws Exception {
        e12 = new MEdge(new MPoint(p2), new MPoint(p1));
        e34 = new MEdge(new MPoint(p3), new MPoint(p4));
        e23 = new MEdge(new MPoint(p2), new MPoint(p3));
        e41 = new MEdge(new MPoint(p4), new MPoint(p1));

        e12.setLeftFace(f);
        e34.setRightFace(f);
        e23.setRightFace(f);
        e41.setRightFace(f);
        compute();

        validateOrder(result, p2, p3, p4, p1);

    }

    @Test()
    public void testOtherEdgeReverted() throws Exception {
        e12 = new MEdge(new MPoint(p1), new MPoint(p2));
        e34 = new MEdge(new MPoint(p4), new MPoint(p3));
        e23 = new MEdge(new MPoint(p2), new MPoint(p3));
        e41 = new MEdge(new MPoint(p4), new MPoint(p1));

        e12.setRightFace(f);
        e34.setLeftFace(f);
        e23.setRightFace(f);
        e41.setRightFace(f);
        compute();

        validateOrder(result, p2, p3, p4, p1);
    }

    private void compute() {
        f.addEdge(e12);
        f.addEdge(e34);
        f.addEdge(e23);
        f.addEdge(e41);
        result = f.visitPointsCounterclockwise();
    }

    private void validateOrder(ArrayList<MPoint> p, Point3D r1, Point3D r2, Point3D r3, Point3D r4) {
        assertEquals(p.size(), 4);
        final double epsilon = 1e-6;
        assertEquals(0, Point3D.computeDistance(r1, p.get(0).getCoordinate()), epsilon);
        assertEquals(0, Point3D.computeDistance(r2, p.get(1).getCoordinate()), epsilon);
        assertEquals(0, Point3D.computeDistance(r3, p.get(2).getCoordinate()), epsilon);
        assertEquals(0, Point3D.computeDistance(r4, p.get(3).getCoordinate()), epsilon);
    }

    private final Point3D p1 = new Point3D(1.0, 1.0, 2.0);
    private final Point3D p2 = new Point3D(-1.0, -1.0, 2.0);
    private final Point3D p3 = new Point3D(-1.0, 20.0, 2.0);
    private final Point3D p4 = new Point3D(1.0, 19.0, 2.0);

    private final MFace f = new MFace();

    private MEdge e12;
    private MEdge e23;
    private MEdge e34;
    private MEdge e41;

    private ArrayList<MPoint> result;
}