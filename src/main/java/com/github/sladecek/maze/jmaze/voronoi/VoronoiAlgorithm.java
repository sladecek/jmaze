package com.github.sladecek.maze.jmaze.voronoi;

import java.util.List;
import java.util.logging.Logger;

import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.Voronoi;

/***
 *
 * Given a list of points (sites) in plane, partition plane into polygons so
 * that each point in the plane belongs to the nearest site.
 *
 * Implemented using simplevoronoi library.
 */
public final class VoronoiAlgorithm {

    /**
     * Make Voronoi maze.
     * @param points center points.
     * @return graph edges separating the rooms
     */
    public List<GraphEdge> computeEdges(final PointsInRectangle points) {
        final boolean withBorderEdges = true;
        final boolean withoutDegenerateEdges = true;
        final double minimalDistanceBetweenSites = 0.1;
        Voronoi v = new Voronoi(minimalDistanceBetweenSites, withBorderEdges, withoutDegenerateEdges);
        List<GraphEdge> allEdges = v.generateVoronoi(
                points.getRoomCenterX(), points.getRoomCenterY(),
                0, points.getWidth() - 1,
                0, points.getHeight() - 1);

        return allEdges;
    }

    /**
     * Logging facility.
     */
    private static final Logger LOGGER = Logger.getLogger("maze");

}
