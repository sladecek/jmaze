package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point3D;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test class for MFace
 */
public class MFaceTest {
    @Test
    public void replaceEdge() throws Exception {
        MFace mf = new MFace();
        MEdge e1 = new MEdge(new MPoint(new Point3D(1,1,1)), new MPoint(new Point3D(2,2,2)));
        mf.addEdge(e1);
        MEdge e2 = new MEdge(new MPoint(new Point3D(3,3,3)), new MPoint(new Point3D(2,2,2)));
        mf.addEdge(e2);

        assertEquals(1, (int)mf.getEdges().get(0).getP1().getCoordinate().getX());
        assertEquals(3, (int)mf.getEdges().get(1).getP1().getCoordinate().getX());

        MEdge e3 = new MEdge(new MPoint(new Point3D(5,5,5)), new MPoint(new Point3D(2,2,2)));
        mf.replaceEdge(e1, e3);
        assertEquals(5, (int)mf.getEdges().get(0).getP1().getCoordinate().getX());
        assertEquals(3, (int)mf.getEdges().get(1).getP1().getCoordinate().getX());

        mf.replaceEdge(e2, e1);
        assertEquals(5, (int)mf.getEdges().get(0).getP1().getCoordinate().getX());
        assertEquals(1, (int)mf.getEdges().get(1).getP1().getCoordinate().getX());
    }

    @Test
    public void replaceEdgeFailing() throws Exception {
        MFace mf = new MFace();
        MEdge e1 = new MEdge(new MPoint(new Point3D(1,1,1)), new MPoint(new Point3D(2,2,2)));
        mf.addEdge(e1);
        MEdge e2 = new MEdge(new MPoint(new Point3D(3,3,3)), new MPoint(new Point3D(2,2,2)));
        mf.addEdge(e2);

        assertEquals(1, (int)mf.getEdges().get(0).getP1().getCoordinate().getX());
        assertEquals(3, (int)mf.getEdges().get(1).getP1().getCoordinate().getX());

        MEdge e3 = new MEdge(new MPoint(new Point3D(5,5,5)), new MPoint(new Point3D(2,2,2)));
        try {
            mf.replaceEdge(e3, e2);
        } catch (Exception e) {
            return;
        }
        fail("Exception expected");
    }

    @Test
    public void testVisit()
    {
        MFace f = new MFace();
        f.addEdge(new MEdge(new MPoint(new Point3D(1.0, 1.0,2.0)), new MPoint(new Point3D(-1.0, -1.0,2.0))));
        f.addEdge(new MEdge(new MPoint(new Point3D(-1.0, 20.0,2.0)), new MPoint(new Point3D(1.0, 19.0,2.0))));
        f.addEdge(new MEdge(new MPoint(new Point3D(-1.0, -1.0,2.0)), new MPoint(new Point3D(-1.0, 20.0,2.0))));
        f.addEdge(new MEdge(new MPoint(new Point3D(1.0, 19.0,2.0)), new MPoint(new Point3D(1.0, 1.0,2.0))));
        ArrayList<MPoint> p = f.visitPointsAroundEdges();
        assertEquals(p.size(), 4);
        final double epsilon=1e-6;
        assertEquals(0, Point3D.computeDistance( new Point3D(1.0, 1.0,2.0), p.get(0).getCoordinate()), epsilon);
        assertEquals(0, Point3D.computeDistance( new Point3D(1.0, 19.0,2.0), p.get(1).getCoordinate()), epsilon);
        assertEquals(0, Point3D.computeDistance( new Point3D(-1.0, 20.0,2.0), p.get(2).getCoordinate()), epsilon);
        assertEquals(0, Point3D.computeDistance( new Point3D(-1.0, -1.0,2.0), p.get(3).getCoordinate()), epsilon);

    }
}