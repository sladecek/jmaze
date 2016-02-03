package com.github.sladecek.maze.jmaze.topology;


/***
 * Interface for spaces (2D, 3D, rectangular, circular ...) that can be filled with maze.
 *
 * The space consists of rooms and walls between rooms. Maze generator obtains list
 * or rooms and  walls in each room and closes some of the walls to create maze.
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
		
	double getRoomDistance(int r1, int r2);

	int getWallProbabilityWeight(int wall);

}
