package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.generator.MazePath;

/**
 * Shapes for maze drawing.
 *
 * Are used bot for 2D or 3D mazes.
 */
public interface IMazeShape {
    void applyPath(MazePath mr);
}
