package com.github.sladecek.maze.jmaze.topology;


/**
 * 
 * Maze generator generates maze in maze space.
 *
 */
public interface IMazeGenerator {
	
	MazeRealization generateMaze(IMazeTopology maze);

}
