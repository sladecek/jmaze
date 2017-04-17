package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;

import java.util.ArrayList;

/**
 * Room corner in 3D model.
 */
public class MPillar extends FloorFace {
    public ArrayList<MPoint> getIntersections() {
        return intersections;
    }

    public void setIntersections(ArrayList<MPoint> intersections) {
        this.intersections = intersections;
    }
    private ArrayList<MPoint> intersections = new ArrayList<>();
}
