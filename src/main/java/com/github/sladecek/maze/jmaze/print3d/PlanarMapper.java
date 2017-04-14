package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.Point3D;

/**
 * Universal planar mapper - maps planar mazes such as rectangular maze into 3d coordinates.
 */
public class PlanarMapper implements IMaze3DMapper {
    @Override
    public double inverselyMapLengthAt(Point3D center, double v) {
        return v;
    }

    @Override
    public Point3D map(Point3D image) {
        return new Point3D(image);
    }
}
