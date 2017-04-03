package com.github.sladecek.maze.jmaze.model3d;

import java.util.ArrayList;

/**
 * Face in 3D model.
 */
public class MFace {
    private ArrayList<MEdge> edges;

    public ArrayList<MEdge> getEdges() {
        return edges;
    }

    public void addEdge(MEdge edge) {
        this.edges.add(edge);
    }
}
