package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;
//REV1
import com.github.sladecek.maze.jmaze.geometry.Point2DDbl;

/**
 * Local coordinate system is equal to global coordinate system. For planar mazes without any sews.
 */
public class TrivialCoordinateSystem implements  ILocalCoordinateSystem {

    @Override
    public Point2DDbl transformToLocal(Point2DDbl image) {
        return image;
    }
}
