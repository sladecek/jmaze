package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.shapes.WallShape;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for WallEnd class..
 */
public class WallEndTest {

    @Before
    public void setUp() throws Exception {
        mw = new MWall();
        ws = WallShape.newInnerWall(0, new Point2DInt(1, 1), new Point2DInt(2, 2), leftId, rightId);
    }

    @Test
    public void p1IsAtPillar() throws Exception {
        final boolean p1IsPilar = true;
        WallEnd w = new WallEnd(mw, ws, p1IsPilar);

        assertEquals(true, w.isP1Pilar());
        assertEquals(1, w.getPillarPoint().getX());
        assertEquals(2, w.getNonPillarPoint().getX());
        assertEquals(77, w.getLeftFaceId());
        assertEquals(99, w.getRightFaceId());

        MEdge e = new MEdge(new MPoint(new Point3D(0, 0, 0)), new MPoint(new Point3D(0, 0, 0)));
        w.addEdge(e);
        assertEquals(e, w.getMWall().getE1());

        assertEquals(ws, w.getWallShape());
        assertEquals(mw, w.getMWall());
    }

    @Test
    public void p1IsNotAtPillar() throws Exception {
        final boolean p1IsPilar = false;
        WallEnd w = new WallEnd(mw, ws, p1IsPilar);

        assertEquals(false, w.isP1Pilar());
        assertEquals(2, w.getPillarPoint().getX());
        assertEquals(1, w.getNonPillarPoint().getX());
        assertEquals(99, w.getLeftFaceId());
        assertEquals(77, w.getRightFaceId());

        MEdge e = new MEdge(new MPoint(new Point3D(0, 0, 0)), new MPoint(new Point3D(0, 0, 0)));
        w.addEdge(e);
        assertEquals(e, w.getMWall().getE3());
    }

    @Test
    public void addEdge() throws Exception {

    }
    final int leftId = 77;
    final int rightId = 99;
    MWall mw;
    WallShape ws;

}