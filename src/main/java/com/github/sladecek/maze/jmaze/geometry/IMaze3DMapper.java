package com.github.sladecek.maze.jmaze.geometry;

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
