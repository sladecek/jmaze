package com.github.sladecek.maze.jmaze.print;
//REV1
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.maze.MazeGenerationException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Print maze into a file.
 */
public interface IMazePrinter {
    void print(OutputStream stream) throws IOException, MazeGenerationException;
    Point2DInt get2dCanvasSize(); // may be null for 3d printers
}
