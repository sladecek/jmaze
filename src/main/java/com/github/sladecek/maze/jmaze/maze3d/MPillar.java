package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.model3d.MPoint;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Room corner in 3D model.
 */
public class MPillar extends FloorFace
{


    private ArrayList<MPoint> intersections = new ArrayList<>();

    public void setIntersections(ArrayList<MPoint> intersections) {
        this.intersections = intersections;
    }

    public ArrayList<MPoint> getIntersections() {

        return intersections;
    }
}
