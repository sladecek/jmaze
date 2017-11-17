package com.github.sladecek.maze.jmaze.print;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.util.MazeGenerationException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Print 3D maze into a file.
 */
public interface IMazePrinter {
    void print(OutputStream stream) throws IOException, MazeGenerationException;
    Point2DInt getCanvasSize(); // may be null for 3d printers TODO
}
