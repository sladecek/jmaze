package com.github.sladecek.maze.jmaze.maze;


/***
 * Defines graph of rooms and walls. 
 * 
 * Walls as well as rooms are identified by integer numbers. The room number start at 0 and
 * are continuous up to GetRoomCount(). Walls can be numbered arbitrarily.
 */
public interface IMazeTopology {
	
	int getRoomCount();
	
	int getWallCount();
	
	Iterable<Integer> getWalls(int room);
	
	int getStartRoom();
	
	int getTargetRoom();
	
	int getRoomBehindWall(int room, int wall);

	int getWallProbabilityWeight(int wall);

}
