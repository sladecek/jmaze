package com.github.sladecek.maze.jmaze.print;

import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.Point;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;

/**
 * 
 * Map planar maze coordinates into 3D points.
 *
 */
public interface IMaze3DMapper {



	Point mapPoint(int cellY, int cellX, double offsetY, double offsetX, double z);
	
	public Point getOuterPoint(int cellX, EastWest ew, UpDown ud,
			SouthNorth snWall, SouthNorth snEdge);
		
	
}
