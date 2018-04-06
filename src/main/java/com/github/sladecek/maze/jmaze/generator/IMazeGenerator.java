package com.github.sladecek.maze.jmaze.generator;
//REV1

import com.github.sladecek.maze.jmaze.maze.IMazeGraph;


/**
 * Generates randomly concrete path of a maze by opening walls between rooms in maze structure.
 */
public interface IMazeGenerator {

    MazePath generateRandomSetOfWalls(IMazeGraph maze);

}
