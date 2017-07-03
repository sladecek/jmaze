package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.LeftRight;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MFace;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Test for MWall.
 */
public class MWallTest {
    @Before
    public void setUp() throws Exception {
        p41 = new MPoint(new Point3D(4.1, 0, 0));
        p12 = new MPoint(new Point3D(1.2, 0, 0));
        p23 = new MPoint(new Point3D(2.3, 0, 0));
        p34 = new MPoint(new Point3D(3.4, 0, 0));

        e1 = new MEdge(p41, p12);
        e3 = new MEdge(p23, p34);

        w = new MWall();
    }

    @Test
    public void addEndEdgeE1() throws Exception {
        w.addEdgeToHead(e1, true);
        assertEquals(e1, w.getE1());
    }

    @Test
    public void addEndEdgeE3() throws Exception {
        w.addEdgeToHead(e3, false);
        assertEquals(e3, w.getE3());
    }


    @Test
    public void addEndEdgeWithInvalidFace() throws Exception {
        e3.setRightFace(new MFace());
        try {
            w.addEdgeToHead(e3, false);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("Expected InvalidArgumentException");
    }

    @Test
    public void finishEdges() throws Exception {
        w.addEdgeToHead(e1, true);
        w.addEdgeToHead(e3, false);
        w.finishEdges();

        MEdge e2 = w.getE2();
        MEdge e4 = w.getE4();

        assertEquals(1.2, e2.getP1().getCoord().getX(), epsilon);
        assertEquals(2.3, e2.getP2().getCoord().getX(), epsilon);

        assertEquals(3.4, e4.getP1().getCoord().getX(), epsilon);
        assertEquals(4.1, e4.getP2().getCoord().getX(), epsilon);

        assertEquals(w, e2.getRightFace());
        assertEquals(w, e4.getRightFace());

        assertEquals(e4, w.getSideEdge(LeftRight.left));
        assertEquals(e2, w.getSideEdge(LeftRight.right));

    }

    private final double epsilon = 0.001;
    private MPoint p41;
    private MPoint p12;
    private MPoint p23;
    private MPoint p34;
    private MEdge e1;
    private MEdge e3;
    private MWall w;
}