package com.github.sladecek.maze.jmaze.generator;

import com.github.sladecek.maze.jmaze.maze.IMazeStructure;


/** 
 * Generates randomly concrete realization of a maze by opening walls between rooms in maze structure. 
 */
public interface IMazeGenerator {
	
	MazeRealization generateMaze(IMazeStructure maze);

}
