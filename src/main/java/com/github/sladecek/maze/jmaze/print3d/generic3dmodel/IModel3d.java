package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;

import java.util.Collection;

/**
 * Complete 3D model.
 */
interface IModel3d {
    Collection<MPoint> getPoints();
    Collection<MEdge> getEdges();
    Collection<MFace> getFaces();
    Collection<MBlock> getBlocks();
}
