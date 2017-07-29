package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.*;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;

/**
 * Map planar maze coordinates into 3D points.
 */
public interface IMaze3DMapper {
    Point3D map(Point2DDbl image, Altitude altitude);
    double inverselyMapLengthAt(Point2DDbl center, double v);
}
