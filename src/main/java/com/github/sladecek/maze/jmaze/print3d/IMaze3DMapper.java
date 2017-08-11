package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.*;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.Altitude;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.ILocalCoordinateSystem;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.TrivialCoordinateSystem;

/**
 * Maps planar maze coordinates into 3D points. Also represents intimate details about maze space topology.
 */
public interface IMaze3DMapper {
    Point3D map(Point2DDbl image, Altitude altitude);



    default boolean isAltitudeVisible(Altitude alt) {
        return true;
    }



    default ILocalCoordinateSystem createLocalCoordinateSystem(Point2DInt center) {
        return new TrivialCoordinateSystem();
    }

    default double inverselyMapLengthAt(Point2DDbl center, double v)
    {
        final double epsilon = 0.001;
        final double epsilonSide = epsilon / Math.sqrt(2);
        Point2DDbl shifted = new Point2DDbl(center.getX() + epsilonSide, center.getY()+epsilonSide);
        Point3D p1 = map(center, Altitude.GROUND);
        Point3D p2 = map(shifted, Altitude.GROUND);
        double distance = Point3D.computeDistance(p1, p2);
        return v*epsilon/distance*0+0.5;
    }
}
