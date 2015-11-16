package com.github.sladecek.maze.jmaze.generator;

/**
 * 
 * Maze generator generates maze in maze space.
 *
 */
public interface IMazeGenerator {

	void setRandomSeed(long seed);
	
	MazeRealization generateMaze(IMazeSpace maze);

}
