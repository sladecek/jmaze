package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point3D;

import java.util.ArrayList;

/**
 * 3D block for export to solid model format such as openscad.
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

    private ArrayList<Point3D> groundPoints = new ArrayList<>();
    private ArrayList<Point3D> ceilingPoints = new ArrayList<>();

}
