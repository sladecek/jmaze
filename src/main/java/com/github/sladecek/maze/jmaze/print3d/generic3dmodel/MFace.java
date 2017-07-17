package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;

import java.util.*;

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

    public ArrayList<MPoint> visitPointsAroundEdges() {
        ArrayList<MPoint> result = new ArrayList<>();
        for (MEdge e : getEdges()) {
            // for each edge add endpoint that is not contained in the previous edge
            if (result.isEmpty()) {
                // starting edge
                MEdge lastEdge = getEdges().get(getEdges().size() - 1);
                if (e.getP1() == lastEdge.getP1() || e.getP1() == lastEdge.getP2()) {
                    result.add(e.getP2());
                } else {
                    result.add(e.getP1());
                }
            } else {
                // continue from existing point
                MPoint prev = result.get(result.size() - 1);
                if (prev == e.getP1()) {
                    result.add(e.getP2());
                } else if (prev == e.getP2()) {
                    result.add(e.getP1());
                } else {
                    throw new InternalError("Edges of a face are not continuous." + toString());
                }
            }
        }
        return result;

    }

    private ArrayList<MEdge> edges = new ArrayList<>();

    @Override
    public String toString() {
        return "MFace{" +
                "edges=" + edges +
                '}';
    }
}
