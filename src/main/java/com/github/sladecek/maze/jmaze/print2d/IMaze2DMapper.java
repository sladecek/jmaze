package com.github.sladecek.maze.jmaze.print2d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;

/*
 * Mapping from 2D coordinates used by shapes to svg coordinates.
 */
public interface IMaze2DMapper {
    Point2D mapPoint(Point2D p);

    int mapLength(int l);

}
