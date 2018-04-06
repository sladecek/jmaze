package com.github.sladecek.maze.jmaze.shapes;
//REV1
import com.github.sladecek.maze.jmaze.generator.MazePath;

/**
 * Shapes for maze drawing.
 *
 * Are used bot for 2D or 3D mazes.
 */
public interface IMazeShape {
    /**
     * Modify the shape by applying generation result such as removing opened walls
     * or drawing a solution.
     * @param mr
     */
    void applyPath(MazePath mr);
}
