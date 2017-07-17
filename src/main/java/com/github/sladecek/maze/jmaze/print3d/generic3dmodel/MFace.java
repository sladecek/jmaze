package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.MRoom;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.MWall;

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
        if (getEdges().size() == 4 && (!(this instanceof MWall)) && (!(this instanceof MRoom))) {
            System.out.println("*++++");
        }
        ArrayList<MPoint> result = new ArrayList<>();
        ArrayList<MEdge> unfinished = new ArrayList<>();
        unfinished.addAll(getEdges());

        if (!unfinished.isEmpty()) {
            MEdge e0 = unfinished.get(0);
            unfinished.remove(e0);
            result.add(e0.getP1());
            while (!unfinished.isEmpty()) {
                MPoint next = null;
                for (MEdge e : unfinished) {
                    // for each edge add endpoint that is not contained in the previous edge
                    // continue from existing point
                    MPoint prev = result.get(result.size() - 1);
                    final double epsilon = 1e-6;
                    if (prev.distanceTo(e.getP1()) < epsilon) {
                        next = e.getP2();
                        unfinished.remove(e);
                        break;
                    } else if (prev.distanceTo(e.getP2()) < epsilon) {
                        next = e.getP1();
                        unfinished.remove(e);
                        break;
                    }
                }
                if (next == null) {
                    throw new InternalError("Edges of a face are not continuous." + toString());
                } else {
                    result.add(next);
                }
            }
        }
        return result;

    }

    @Override
    public String toString() {
        return "MFace{" +
                "edges=" + edges +
                '}';
    }

    private ArrayList<MEdge> edges = new ArrayList<>();
}
