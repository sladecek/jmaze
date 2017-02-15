package com.github.sladecek.maze.jmaze.print2d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;

/*
 * Cartesian mapping from 2D coordinates used by shapes to svg coordinates.
 */
public class Cartesian2DMapper implements IMaze2DMapper {

    public Cartesian2DMapper(Point2D zeroPoint, int resolutionX, int resolutionY) {
        this.zeroPoint = zeroPoint;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
    }

    @Override
    public Point2D mapPoint(Point2D p) {
        Point2D result = new Point2D(resolutionX * (p.getX()) + zeroPoint.getX(),
                resolutionY * (p.getY()) + zeroPoint.getY());
        return result;
    }

    @Override
    public int mapLength(int l) {
        assert resolutionX == resolutionY;
        return l * resolutionX;
    }

    private Point2D zeroPoint;
    private int resolutionX;
    private int resolutionY;
}
