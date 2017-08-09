package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.generator.MazePath;

/**
 * Shapes for maze drawing.
 *
 * Either 2D or 3D.
 */
public interface IMazeShape {
    void applyPath(MazePath mr);
}
