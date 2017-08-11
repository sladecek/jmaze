package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2DDbl;

/**
 * Local coordinate system for 2D embeddings into 3D. Enables to transform coordinates around a certain point so that
 * normal geometry can be applied. Is used to iron the sews in the 2D embeddings.
 */
public interface ILocalCoordinateSystem {
    Point2DDbl transformToLocal(Point2DDbl image);
}
