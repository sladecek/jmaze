package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.*;

/**
 * Map planar maze coordinates into 3D points.
 */
public interface IMaze3DMapper {
    Point3D map(Point3D image);
    double inverselyMapLengthAt(Point3D center, double v);
}
