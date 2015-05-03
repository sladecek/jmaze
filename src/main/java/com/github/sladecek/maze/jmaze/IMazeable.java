package com.github.sladecek.maze.jmaze;

import java.util.Vector;

/***
 * Interface for spaces (2D, 3D, rectangular, circular ...) that can be filled with maze.
 *
 * The space consists of rooms and walls between rooms. The maze generator obtains the list
 * or rooms and  walls in each room and closes some of the walls to create maze.
 * 
 * Wall as well as rooms are identified by integer numbers. The room number start at 0 and
 * are continuous up to GetRoomCount(). Walls can be numbered arbitrarily.
 */
public interface IMazeable {
	
	int getRoomCount();
	
	Iterable<Integer> getWalls(int room);
	
	int getStartRoom();
	
	int getTargetRoom();
	
	boolean isWallClosed(int wall);
	
	void setWallClosed(int wall, boolean value);
	
	double getRoomDistance(int r1, int r2);

	int getOtherRoom(int room, int wall);
	
	void setSolution(Vector<Integer> solution);
	
	int getWallProbabilityWeight(int wall);
	

}
