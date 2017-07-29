package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.Point2DDbl;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;

/**
 * Universal planar mapper - maps planar mazes such as rectangular maze into 3d coordinates.
 */
public class PlanarMapper implements IMaze3DMapper {


    @Override
    public Point3D map(Point2DDbl image, Altitude altitude) {
        return new Point3D(image.getX(), image.getY(), altitude.getValue());
    }

    @Override
    public double inverselyMapLengthAt(Point2DDbl center, double v) {
        return v;
    }
}
