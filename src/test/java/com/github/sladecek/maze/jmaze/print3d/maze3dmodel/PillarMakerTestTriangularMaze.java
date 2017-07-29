package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.print3d.PillarMaker;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.shapes.WallShape;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test for pillar maker - errors found in triangular maze.
 */
public class PillarMakerTestTriangularMaze extends PillarMakerTestBase {
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
        final int expectedIntersectionCount = 5;
        final double[][] expected = {
                {-0.75, 1.81066017178},
                {-1.81066017178, 0.75},
                {-0.75, -0.75},
                {0.75, -0.31066017178},
                {0.75, 1.81066017178},
        };
        final int expectedBaseEdgesSize = 5;
        testOneComputation(dx, dy, endpoints, expectedIntersectionCount, expected, expectedBaseEdgesSize);
    }



}