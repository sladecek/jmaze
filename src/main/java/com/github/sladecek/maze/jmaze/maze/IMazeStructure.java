package com.github.sladecek.maze.jmaze.maze;


/***
 */
public interface IMazeStructure {
	
	int getRoomCount();
	
	int getWallCount();
	
	Iterable<Integer> getWalls(int room);
	
	int getStartRoom();
	
	int getTargetRoom();
	
	int getRoomBehindWall(int room, int wall);

	int getWallProbabilityWeight(int wall);

}
