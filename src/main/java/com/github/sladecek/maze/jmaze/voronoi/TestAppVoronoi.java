package com.github.sladecek.maze.jmaze.voronoi;

import java.util.Random;

import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;

/**
 * Ad-hoc test of Voronou maze.
 */
public class TestAppVoronoi extends TestApp2DBase {

    /**
     * Main method.
     *
     * @param args Program arguments.
     */
    public static void main(final String[] args) {
        new TestAppVoronoi().printTestMaze("maze-voronoi", () -> {
            final int width = 10;
            final int height = 10;
            final int roomCount = 100;
            final int loydCount = 10;
            final int seed = 4;

            final Random randomGenerator = new Random();
            randomGenerator.setSeed(seed);
            return new Voronoi2DMaze(width, height, roomCount, loydCount, randomGenerator, true);
        });
    }

}
