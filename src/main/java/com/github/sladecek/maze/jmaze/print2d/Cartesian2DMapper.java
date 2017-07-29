package com.github.sladecek.maze.jmaze.print2d;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;

/*
 * Cartesian mapping from 2D coordinates used by shapes to svg coordinates.
 */
public class Cartesian2DMapper implements IMaze2DMapper {

    public Cartesian2DMapper(Point2DInt zeroPoint, int resolutionX, int resolutionY) {
        this.zeroPoint = zeroPoint;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
    }

    @Override
    public Point2DInt mapPoint(Point2DInt p) {
        Point2DInt result = new Point2DInt(resolutionX * (p.getX()) + zeroPoint.getX(),
                resolutionY * (p.getY()) + zeroPoint.getY());
        return result;
    }

    @Override
    public int mapLength(int l) {
        assert resolutionX == resolutionY;
        return l * resolutionX;
    }

    private Point2DInt zeroPoint;
    private int resolutionX;
    private int resolutionY;
}
