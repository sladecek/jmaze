package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;

/**
 * Wall in 3D model.
 */
public class MWall extends FloorFace {


    public void addEndEdge(MEdge edge, boolean reversed) {
        if (reversed) {
            assert (e3 == null);
            e3 = edge;
        } else {
            assert (e1 == null);
            e1 = edge;
        }
    }

    public MEdge getSideEdge(boolean leftSide) {
        return leftSide ? e2 : e4;
        // TODO zkontrolovat
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
        e2 = new MEdge(e1.getP2(), e3.getP2());
        e2.setLeftFace(this);
        addEdge(e2);
        addEdge(e3);
        e4 = new MEdge(e1.getP1(), e3.getP1());
        e4.setLeftFace(this);
        addEdge(e4);

    }

    MEdge e1;
    MEdge e3;
    MEdge e2;
    MEdge e4;
}
