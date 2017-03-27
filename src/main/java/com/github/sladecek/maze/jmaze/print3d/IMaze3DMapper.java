package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.*;

/** 
 * Map planar maze coordinates into 3D points.
 */
public interface IMaze3DMapper {

	Point3D mapPoint(int cellY, int cellX, double offsetY, double offsetX, double offsetZ);
	
	Point3D mapCorner(int cellX, EastWest ew, UpDown ud, SouthNorth snWall, SouthNorth snEdge);

	int getStepY(int y, int x);


    double inverselyMapLengthAt(Point2D center, double v);
}
