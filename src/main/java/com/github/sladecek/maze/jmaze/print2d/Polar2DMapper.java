package com.github.sladecek.maze.jmaze.print2d;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;

/*
 * Maps 2D polar coordinates used by shapes into SVG cartesian coordinates (for circular maze).
 */
public class Polar2DMapper implements IMaze2DMapper {

    public Polar2DMapper(Point2DInt zeroPoint, int resolution) {
        this.zeroPoint = zeroPoint;
        this.resolution = resolution;
    }

    @Override
    public Point2DInt mapPoint(Point2DInt p) {
        int r = mapLength(p.getY());
        double x = r * Math.cos(p.getAngleRad()) + zeroPoint.getX();
        double y = r * Math.sin(p.getAngleRad()) + zeroPoint.getY();
        return new Point2DInt((int) Math.floor(x), (int) Math.floor(y));
    }

    @Override
    public int mapLength(int l) {
        return l * resolution;
    }

    private Point2DInt zeroPoint;
    private int resolution;

}
