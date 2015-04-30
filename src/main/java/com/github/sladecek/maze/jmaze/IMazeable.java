package com.github.sladecek.maze.jmaze;

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
	
	int GetRoomCount();
	
	Iterable<Integer> GetWalls(int room);
	
	int GetStartRoom();
	
	int GetTargetRoom();
	
	boolean IsWallClosed(int wall);
	
	void SetWallClosed(int wall, boolean value);
	
	double GetRoomDistance(int r1, int r2);
	

}
