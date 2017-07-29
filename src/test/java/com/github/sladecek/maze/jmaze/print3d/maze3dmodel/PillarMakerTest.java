package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.print3d.PillarMaker;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.shapes.WallShape;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test for pillar maker.
 */
public class PillarMakerTest {
    @Test
    public void makePillarAtOrigin() throws Exception {
        testOneOffset(0,0);
    }

    @Test
    public void makePillarAtDistance() throws Exception {
        testOneOffset(100,-100);
    }

    void testOneOffset(int dx, int dy) {
        final int[][] endpoints = {{-7, 7}, {-7, 0}, {0, -7}, {7, 7}, {0, 7}};
        Point2DInt center = new Point2DInt(dx, dy);
        ArrayList<WallEnd> walls = new ArrayList<>();
        final int cnt = endpoints.length;
        for (int i = 0; i < cnt; ++i) {
            MWall mw = new MWall();
            Point2DInt p1 = new Point2DInt(dx+endpoints[i][0], dy+endpoints[i][1]);
            final int leftId = (i + 1) % cnt;
            final int rightId = i;
            WallShape ws = WallShape.newInnerWall(0, center, p1, leftId, rightId);
            final boolean p1IsPillar = true;
            WallEnd w = new WallEnd(mw, ws, p1IsPillar);
            walls.add(w);
        }
        final double wallWidthInMm = 1.5;
        PillarMaker pm = new PillarMaker(center, walls, wallWidthInMm);
        pm.makePillar();

        ArrayList<MEdge> baseEdges = pm.getBase().getEdges();
        assertEquals(5, baseEdges.size());

        final double epsilon = 1e-5;
        final double[][] expected = {
                {-0.75, 1.81066017178},
                {-1.81066017178, 0.75},
                {-0.75, -0.75},
                {0.75, -0.31066017178},
                {0.75, 1.81066017178},
        };

        for (int i = 0; i < baseEdges.size(); ++i) {
            System.out.println(i);
            TelescopicPoint pp = (TelescopicPoint) baseEdges.get(i).getP1();
            System.out.println("x ");
            assertEquals(expected[i][0]+dx, pp.getPlanarX(), epsilon);
            System.out.println("y ");
            assertEquals(expected[i][1]+dy, pp.getPlanarY(), epsilon);
        }

        assertEquals(5, pm.getBase().getIntersections().size());
    }

}