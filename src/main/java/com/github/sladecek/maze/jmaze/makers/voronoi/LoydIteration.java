package com.github.sladecek.maze.jmaze.makers.voronoi;
//REV1

import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.Polygon;
import be.humphreys.simplevoronoi.PolygonSeparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Loyd iterative algorithm works on a set of points in a rectangle. Makes the
 * distribution o points smoother by repeatedly replacing every point with a centroid of
 * its Voronoi rectangle.
 */
class LoydIteration {

    public LoydIteration(PointsInRectangle input, int numberOfIterations) {
        super();
        this.input = input;
        this.numberOfIterations = numberOfIterations;
        computeOutput();
    }

    public PointsInRectangle getInput() {
        return input;
    }

    public PointsInRectangle getOutput() {
        return output;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    private void computeOutput() {
        output = PointsInRectangle.newCopyOf(input);

        for (int l = 0; l < numberOfIterations; l++) {
            List<GraphEdge> e = new VoronoiAlgorithm().computeEdges(output);

            ArrayList<Polygon> polygons = PolygonSeparator.makePolygons(e);
            for (Polygon p : polygons) {
                Polygon.CenterOfGravity cog = p.computeAreaAndCog();
                int i = p.getCenterSite();
                if (i >= 0) {
                    output.setRoomCenterX(i, cog.getX());
                    output.setRoomCenterY(i, cog.getY());
                }

            }
        }

    }

    private final PointsInRectangle input;
    private final int numberOfIterations;
    private PointsInRectangle output;

}
