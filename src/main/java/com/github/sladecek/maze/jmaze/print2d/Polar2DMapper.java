package com.github.sladecek.maze.jmaze.print2d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;

/*
 * Polar mapping from 2D coordinates used by shapes to svg coordinates (for circular maze).
 */
public class Polar2DMapper implements IMaze2DMapper {

    public Polar2DMapper(Point2D zeroPoint, int cellSize) {
        this.zeroPoint = zeroPoint;
        this.cellSize = cellSize;

    }

    @Override
    public Point2D mapPoint(Point2D p) {
        int r = mapLength(p.getY());
        double x = r * Math.cos(p.getAngleRad()) + zeroPoint.getX();
        double y = r * Math.sin(p.getAngleRad()) + zeroPoint.getY();
        return new Point2D((int) Math.floor(x), (int) Math.floor(y));
    }

    @Override
    public int mapLength(int l) {
        return l * cellSize;
    }

    private Point2D zeroPoint;
    private int cellSize;

}
