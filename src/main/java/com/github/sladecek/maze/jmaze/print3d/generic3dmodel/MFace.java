package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;
//REV1
import java.util.*;

/**
 * Face in 3D model. Face is defined by its surrounding edges.
 */
public class MFace {
    public ArrayList<MEdge> getEdges() {
        return edges;
    }

    public void addEdge(MEdge edge) {
        this.edges.add(edge);
    }

    public void replaceEdge(MEdge oldEdge, MEdge newEdge) {
        int ix = edges.indexOf(oldEdge);
        if (ix < 0) {
            throw new IllegalArgumentException("Edge " + oldEdge + " not found.");
        }
        edges.set(ix, newEdge);
    }



    public ArrayList<MPoint> visitPointsCounterclockwise() {
        ArrayList<MPoint> result = new ArrayList<>();
        ArrayList<MEdge> unfinished = new ArrayList<>();
        unfinished.addAll(getEdges());

        if (!unfinished.isEmpty()) {
            MEdge e0 = unfinished.get(0);
            unfinished.remove(e0);
            if (e0.getLeftFace() == this) {
                // first edge visited from p2 to p1
                result.add(e0.getP1());
            } else if (e0.getRightFace() == this) {
                result.add(e0.getP2());
            } else {
                throw new InternalError("Inconsistent left/right side of edge "+e0+" face " + toString()+".");
            }
            while (!unfinished.isEmpty()) {
                MPoint prev = result.get(result.size() - 1);

                MPoint next = null;
                for (MEdge e : unfinished) {
                    // for each edge add endpoint that is not contained in the previous edge
                    // continue from existing point
                    final double epsilon = 1e-6;
                    if ((e.getRightFace() == this) && (prev.distanceTo(e.getP1()) < epsilon)) {
                        next = e.getP2();
                        unfinished.remove(e);
                        break;
                    } else if ((e.getLeftFace() == this) && (prev.distanceTo(e.getP2()) < epsilon)) {
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

    private boolean isVisible = true;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    private final ArrayList<MEdge> edges = new ArrayList<>();
}
