package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point3D;

/**
 * Point in a 3D model. All points in the model must be unique.
 */
public class MPoint {
    public MPoint(Point3D coord) {
        this.coord = coord;
    }

    public Point3D getCoord() {
        return coord;
    }

    protected void setCoord(Point3D coord) {
        if (coord == null) {
            throw new IllegalArgumentException("Coordinates may not be null");
        }
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "MPoint{" +
                coord +
                '}';
    }

    public double distanceTo(MPoint p1) {
        double result = Point3D.computeDistance(p1.getCoord(), coord);
        System.out.println("   comparing ---"+p1.getCoord()+" and "+coord+" dist="+result);
        return result;
    }

    private Point3D coord;
}
