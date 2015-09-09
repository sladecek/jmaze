package com.github.sladecek.maze.jmaze.generator;


public interface IMazeGenerator {

	void setRandomSeed(long seed);
	MazeRealization generateMaze(IMazeSpace maze);

}
