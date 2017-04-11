package com.github.sladecek.maze.jmaze.model3d;

import java.util.ArrayList;

/**
 * Face in 3D model.
 */
public class MFace {
    public void replaceEdge(MEdge e, MEdge e22) {
        // TODO
    }

    private ArrayList<MEdge> edges;

    public ArrayList<MEdge> getEdges() {
        return edges;
    }

    public void addEdge(MEdge edge) {
        this.edges.add(edge);
    }
}
