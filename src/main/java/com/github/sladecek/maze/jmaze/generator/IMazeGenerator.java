package com.github.sladecek.maze.jmaze.generator;

import com.github.sladecek.maze.jmaze.maze.IMazeStructure;


/** 
 * Generates randomly concrete pick of a maze by opening walls between rooms in maze structure.
 */
public interface IMazeGenerator {
	
	MazePick generateMaze(IMazeStructure maze);

}
