package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.print3d.PillarMaker;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.MWall;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.TelescopicPoint;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.WallEnd;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Base class for PillarMakerTests
 */
public class PillarMakerTestBase {
    void testOneComputation(int dx, int dy, int[][] endpoints, int expectedIntersectionCount, double[][] expected, int expectedBaseEdgesSize) {
        Point2DInt center = new Point2DInt(dx, dy);
        ArrayList<WallEnd> walls = new ArrayList<>();
        final int cnt = endpoints.length;
        for (int i = 0; i < cnt; ++i) {
            MWall mw = new MWall();
            Point2DInt p1 = new Point2DInt(dx+endpoints[i][0], dy+endpoints[i][1]);
            final int leftId = (i + 1) % cnt;
            @SuppressWarnings("UnnecessaryLocalVariable") final int rightId = i;
            WallShape ws = WallShape.newInnerWall(0, center, p1, leftId, rightId);
            final boolean p1IsPillar = true;
            WallEnd w = new WallEnd(mw, ws, p1IsPillar);
            walls.add(w);
        }
        final double wallWidthInMm = 1.5;
        PillarMaker pm = new PillarMaker(new TrivialCoordinateSystem(), center, walls, wallWidthInMm);
        pm.makePillar();

        ArrayList<MEdge> baseEdges = pm.getBase().getEdges();
        assertEquals(expectedBaseEdgesSize, baseEdges.size());

        final double epsilon = 1e-5;

        for (int i = 0; i < baseEdges.size(); ++i) {
            System.out.println(i);
            TelescopicPoint pp = (TelescopicPoint) baseEdges.get(i).getP1();
            System.out.println("x ");
            assertEquals(expected[i][0]+dx, pp.getPlanarX(), epsilon);
            System.out.println("y ");
            assertEquals(expected[i][1]+dy, pp.getPlanarY(), epsilon);
        }

        assertEquals(expectedIntersectionCount, pm.getBase().getIntersections().size());
    }
}
