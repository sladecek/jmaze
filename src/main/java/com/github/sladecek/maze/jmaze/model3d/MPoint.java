package com.github.sladecek.maze.jmaze.model3d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.geometry.Point3D;

/**
 * Point in a 3D model. All points in the model must be unique.
 */
public class MPoint {
    public MPoint(Point3D coord) {
        this.coord = coord;
    }

    private final Point3D coord;

    public Point3D getCoord() {
        return coord;
    }
}
