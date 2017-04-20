package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.geometry.Point2DTest;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.shapes.WallShape;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for WallEnd class..
 */
public class WallEndTest {

    MWall mw;
    final int leftId = 77;
    final int rightId = 99;
    WallShape ws;

    @Before
    public void setUp() throws Exception {
        mw = new MWall();
        ws = WallShape.newInnerWall(0, new Point2D(1,1),new Point2D(2,2), leftId, rightId);
    }

    @Test
    public void p1IsAtPillar() throws Exception {
        final boolean p1IsPilar = true;
        WallEnd w = new WallEnd(mw, ws, p1IsPilar);

        assertEquals(1, w.getPillarPoint().getX());
        assertEquals(2, w.getNonPillarPoint().getX());
        assertEquals(77, w.getLeftFaceId());
        assertEquals(99, w.getRightFaceId());
    }

    @Test
    public void p1IsNotAtPillar() throws Exception {
        final boolean p1IsPilar = false;
        WallEnd w = new WallEnd(mw, ws, p1IsPilar);

        assertEquals(2, w.getPillarPoint().getX());
        assertEquals(1, w.getNonPillarPoint().getX());
        assertEquals(99, w.getLeftFaceId());
        assertEquals(77, w.getRightFaceId());
    }

    @Test
    public void addEdge() throws Exception {

    }

}