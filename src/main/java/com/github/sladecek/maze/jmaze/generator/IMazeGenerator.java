package com.github.sladecek.maze.jmaze.generator;

import com.github.sladecek.maze.jmaze.maze.IMazeTopology;


/**
 * 
 * Maze generator generates maze in maze space.
 *
 */
public interface IMazeGenerator {
	
	MazeRealization generateMaze(IMazeTopology maze);

}
