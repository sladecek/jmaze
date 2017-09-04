package com.github.sladecek.maze.jmaze.print;

import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.util.MazeGenerationException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Print 3D maze into a file.
 */
public interface IMazePrinter {
    void print(OutputStream stream) throws IOException, MazeGenerationException;
}
