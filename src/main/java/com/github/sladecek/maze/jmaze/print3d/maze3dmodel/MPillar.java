package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;
//REV1
import com.github.sladecek.maze.jmaze.geometry.Point2DDbl;
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

    @Override
    public String toString() {
        return "MPillar{" +
                "intersections=" + intersections +
                ", center=" + center +
                '}';
    }

    public Point2DDbl getCenter() {
        return center;
    }

    public void setCenter(Point2DDbl center) {
        this.center = center;
    }
    private ArrayList<MPoint> intersections = new ArrayList<>();
    private Point2DDbl center = new Point2DDbl(0, 0);
}
