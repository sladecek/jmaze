package com.github.sladecek.maze.jmaze.model3d;

import java.util.Collection;

/**
 * Complete 3D model.
 */
public interface IModel3d {
    Collection<MPoint> getPoints();
    Collection<MEdge> getEdges();
    Collection<MFace> getFaces();
    Collection<MBlock> getBlocks();
}
