package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;
//REV1
import com.github.sladecek.maze.jmaze.geometry.Point3D;

import java.util.ArrayList;

/**
 * 3D block for export to solid model format such as OpenScad.
 */
public class MBlock {
    public MBlock() {
    }

    public ArrayList<Point3D> getGroundPoints() {
        return groundPoints;
    }

    public ArrayList<Point3D> getCeilingPoints() {
        return ceilingPoints;
    }

    public void addGroundPoint(Point3D p) {
        groundPoints.add(p);
    }

    public void addCeilingPoint(Point3D p) {
        ceilingPoints.add(p);
    }

    @Override
    public String toString() {
        return "MBlock{" +
                "groundPoints=" + groundPoints +
                ", ceilingPoints=" + ceilingPoints +
                '}';
    }

    private final ArrayList<Point3D> groundPoints = new ArrayList<>();
    private final ArrayList<Point3D> ceilingPoints = new ArrayList<>();

}
