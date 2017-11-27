package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point3D;

/**
 * Point in a 3D model. All points in the model must be unique.
 */
public class MPoint {
    public MPoint(Point3D coordinate) {
        this.coordinate = coordinate;
    }

    public Point3D getCoordinate() {
        return coordinate;
    }

    protected void setCoordinate(Point3D coordinate) {
        if (coordinate == null) {
            throw new IllegalArgumentException("Coordinates may not be null");
        }
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return "MPoint{" +
                coordinate +
                '}';
    }

    public double distanceTo(MPoint p1) {
        return Point3D.computeDistance(p1.getCoordinate(), coordinate);
    }

    public double amplitude() {
        return Double.max(
                Double.max(
                        Math.abs(coordinate.getX()),
                        Math.abs(coordinate.getY())),
                Math.abs(coordinate.getZ()));
    }

    private Point3D coordinate;
}
