package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.LeftRight;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;

/**
 * Wall in 3D model.
 * <p>
 * Wall has 4 edges. The edges e1 and e3 (heads) are oriented towards pillars. Edges e2 and e4 (sides) close the circle.
 */
public class MWall extends FloorFace {

    private static int glId = 0;
    private int id = 0;
    public MWall() {
        id = glId++;
    }

    /**
     * Adds an edge to the wall at one of its two pillar positions.
     *
     * @param edgeAtP1 if true the edge is added to the 'p1' side of the wall. Otherwise to the p2 side.
     */
    public void addEdgeToHead(MEdge edge, boolean edgeAtP1) {
        if (edge.getRightFace() == null) {
            edge.setRightFace(this);
        }
        if (edge.getRightFace() != this) {
            throw new IllegalArgumentException("The right face of the edge added to a face must be the face to which the edge is being added.");
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
        e2.setRightFace(this);
        addEdge(e2);
        addEdge(e3);
        e4 = new MEdge(e3.getP2(), e1.getP1());
        e4.setRightFace(this);
        addEdge(e4);
    }

    public double getKeyForSortingInTests() {
        TelescopicPoint pr11 = ((TelescopicPoint) getE1().getP1());
        TelescopicPoint pr12 = ((TelescopicPoint) getE1().getP2());
        TelescopicPoint pr31 = ((TelescopicPoint) getE3().getP1());
        TelescopicPoint pr32 = ((TelescopicPoint) getE3().getP2());
        double x = pr11.getPlanarX() + pr12.getPlanarX() + pr31.getPlanarX() + pr32.getPlanarX();
        double y = pr11.getPlanarY() + pr12.getPlanarY() + pr31.getPlanarY() + pr32.getPlanarY();
        return 1000 * y + x;
    }


    @Override
    public String toString() {
        return "MWall["+id+"]{" +
                "e1=" + e1 +
                ", e3=" + e3 +
                ", e2=" + e2 +
                ", e4=" + e4 +
                '}';
    }

    private MEdge e1;
    private MEdge e3;
    private MEdge e2;
    private MEdge e4;
}
