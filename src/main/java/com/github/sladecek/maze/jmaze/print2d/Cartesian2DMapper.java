package com.github.sladecek.maze.jmaze.print2d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;

/*
 * Cartesian mapping from 2D coordinates used by shapes to svg coordinates.
 */
public class Cartesian2DMapper implements IMaze2DMapper {

    public Cartesian2DMapper(Point2D zeroPoint, int cellSizeX, int cellSizeY) {
        this.zeroPoint = zeroPoint;
        this.cellSizeX = cellSizeX;
        this.cellSizeY = cellSizeY;
    }

    @Override
    public Point2D mapPoint(Point2D p) {
        Point2D result = new Point2D(cellSizeX*(p.getX()) + zeroPoint.getX(),
        		cellSizeY*(p.getY()) + zeroPoint.getY());
        return result;
    }

    @Override
    public int mapLength(int l) {
        assert cellSizeX == cellSizeY;
    	return l * cellSizeX;
    }

    private Point2D zeroPoint;
    private int cellSizeX;
    private int cellSizeY;
}
