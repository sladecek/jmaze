package com.github.sladecek.maze.jmaze.generator;


/**
 * 
 * Maze generator generates maze in maze space.
 *
 */
public interface IMazeGenerator {
	
	MazeRealization generateMaze(IMazeTopology maze);

}
