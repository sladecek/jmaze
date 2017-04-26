package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.MWall;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.ProjectedPoint;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.WallEnd;
import com.github.sladecek.maze.jmaze.shapes.WallShape;
import org.junit.Test;
import scala.collection.mutable.LinkedList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static javafx.scene.input.KeyCode.L;
import static org.junit.Assert.*;

/**
 * Test for pillar maker.
 */
public class PillarMakerTest {
    @Test
    public void makePillar() throws Exception {
        final int[][] endpoints = {{-7, 7}, {-7, 0}, {0, -7}, {7, 7}, {0, 7}};
        Point2D center = new Point2D(0, 0);
        ArrayList<WallEnd> walls = new ArrayList<>();
        final int cnt = endpoints.length;
        for (int i = 0; i < cnt; ++i) {
            MWall mw = new MWall();
            Point2D p1 = new Point2D(endpoints[i][0], endpoints[i][1]);
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
            ProjectedPoint pp = (ProjectedPoint) baseEdges.get(i).getP1();
            System.out.println("x ");
            assertEquals(expected[i][0], pp.getPlanarX(), epsilon);
            System.out.println("y ");
            assertEquals(expected[i][1], pp.getPlanarY(), epsilon);
        }

        assertEquals(5, pm.getBase().getIntersections().size());
    }

}