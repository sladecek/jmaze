package com.github.sladecek.maze.jmaze.model3d;

import java.util.ArrayList;

/**
 * Face in 3D model.
 */
public class MFace {
    public void replaceEdge(MEdge oldEdge, MEdge newEdge) {
        int ix = edges.indexOf(oldEdge);
        if (ix < 0) {
            throw new IllegalArgumentException("Edge " + oldEdge + " not found.");
        }
        edges.set(ix, newEdge);
    }

    public ArrayList<MEdge> getEdges() {
        return edges;
    }

    public void addEdge(MEdge edge) {
        this.edges.add(edge);
    }
    private ArrayList<MEdge> edges = new ArrayList<>();
}
