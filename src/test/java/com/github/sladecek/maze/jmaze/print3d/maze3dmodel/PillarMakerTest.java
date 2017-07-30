package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import org.junit.Test;

/**
 * Test for pillar maker.
 */
public class PillarMakerTest extends PillarMakerTestBase {
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