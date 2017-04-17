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
        this.coord = coord;
    }
    private Point3D coord;
}
