package com.github.sladecek.maze.jmaze.print2d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;

public class Cartesian2DMapper implements IMaze2DMapper {

	public Cartesian2DMapper(Point2D zeroPoint, int cellSize) {
		this.zeroPoint = zeroPoint;
		this.cellSize = cellSize;
	}

	@Override
	public Point2D mapPoint(Point2D p) {
		Point2D result = new Point2D(mapLength(p.getX())+zeroPoint.getX(), mapLength(p.getY())+zeroPoint.getY());
		return result;
	}

	@Override
	public int mapLength(int l) {
		return l * cellSize;
	}

	
	private Point2D zeroPoint;
	private int cellSize;
}
