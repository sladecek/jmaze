package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.PrintStyle;

/**
 * Maze shapes that can be printed in 2D.
 */
public interface IPrintableMazeShape2D extends IMazeShape {
   void print2D(I2DDocument doc, PrintStyle printStyle);
}
