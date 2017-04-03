package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.model3d.MEdge;

/**
 * Wall in 3D model.
 */
public class MWall extends FloorFace {


    public void addEndEdge(MEdge edge, boolean reversed) {
        if (reversed) {
            assert(e3 == null);
            e3 = edge;
        } else {
            assert(e1 == null);
            e1 = edge;
        }
    }

    public MEdge getSideEdge(boolean leftSide) {
return null;
    }

    public void finishEdges() {
        assert(e1 != null);
        assert(e3 != null);
        assert(getEdges().size() == 0);

        addEdge(e1);
        addEdge(new MEdge(e1.getP2(), e3.getP2()));
        addEdge(e3);
        addEdge(new MEdge(e1.getP1(), e3.getP1()));
    }

    MEdge e1;
    MEdge e3;
}
