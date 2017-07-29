package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
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

    public void setCenter(Point2DInt center) {
        this.center = center;
    }

    @Override
    public String toString() {
        return "MPillar{" +
                "intersections=" + intersections +
                ", center=" + center +
                '}';
    }

    private ArrayList<MPoint> intersections = new ArrayList<>();
    private Point2DInt center = new Point2DInt(0,0);

    public Point2DInt getCenter() {
        return center;
    }
}
