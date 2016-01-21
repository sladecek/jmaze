package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;

/**
 * 
 * Map planar maze coordinates into 3D points.
 *
 */
public interface IMaze3DMapper {

	Point3D mapPoint(int cellY, int cellX, double offsetY, double offsetX, double offsetZ);
	
	Point3D mapCorner(int cellX, EastWest ew, UpDown ud, SouthNorth snWall, SouthNorth snEdge);

	int getStepY(int y, int x);
		
	
}
