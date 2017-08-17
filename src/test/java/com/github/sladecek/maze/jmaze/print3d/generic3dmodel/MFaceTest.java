package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point3D;
import org.junit.Test;

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

}