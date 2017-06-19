package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.LeftRight;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;

/**
 * Wall in 3D model.
 * <p>
 * Wall has 4 edges. The edges e1 and e3 are oriented towards pillars. Edges e2 and e4 close the circle.
 */
public class MWall extends FloorFace {

    /**
     * Adds an edge to the pillar.
     *
     * @param edge
     * @param edgeAtP1 if true the edge is added to the 'p1' side of the wall. Otherwise to the p2 side.
     */
    public void addEndEdge(MEdge edge, boolean edgeAtP1) {
        if (edge.getLeftFace() == null) {
            edge.setLeftFace(this);
        }
        if (edge.getLeftFace() != this) {
            throw new IllegalArgumentException("The left face of the edge added to a face must be the face to which the edge is being added.");
        }
        if (edgeAtP1) {
            assert (e1 == null);
            e1 = edge;
        } else {
            assert (e3 == null);
            e3 = edge;
        }
    }

    public MEdge getSideEdge(LeftRight side) {
        return side == LeftRight.left ? e4 : e2;
    }

    public MEdge getE1() {
        return e1;
    }

    public MEdge getE3() {
        return e3;
    }

    public MEdge getE2() {
        return e2;
    }

    public MEdge getE4() {
        return e4;
    }

    public void finishEdges() {
        assert (e1 != null);
        assert (e3 != null);
        assert (getEdges().size() == 0);

        addEdge(e1);
        e2 = new MEdge(e1.getP2(), e3.getP1());
        e2.setLeftFace(this);
        addEdge(e2);
        addEdge(e3);
        e4 = new MEdge(e3.getP2(), e1.getP1());
        e4.setLeftFace(this);
        addEdge(e4);
    }

    public double getKeyForSortingInTests() {
        ProjectedPoint pr11 = ((ProjectedPoint) getE1().getP1());
        ProjectedPoint pr12 = ((ProjectedPoint) getE1().getP2());
        ProjectedPoint pr31 = ((ProjectedPoint) getE3().getP1());
        ProjectedPoint pr32 = ((ProjectedPoint) getE3().getP2());
        double x = pr11.getPlanarX() + pr12.getPlanarX() + pr31.getPlanarX() + pr32.getPlanarX();
        double y = pr11.getPlanarY() + pr12.getPlanarY() + pr31.getPlanarY() + pr32.getPlanarY();
        return 1000 * y + x;
    }

    private MEdge e1;
    private MEdge e3;
    private MEdge e2;
    private MEdge e4;
}
