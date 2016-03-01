package com.github.sladecek.maze.jmaze.print3d;

import java.io.OutputStream;

import com.github.sladecek.maze.jmaze.util.MazeGenerationException;

/*
 * Prints 3D blocks to stream.
 */
public interface IMaze3DPrinter {

    void printBlocks(IBlockMaker blockMaker, OutputStream stream) throws MazeGenerationException;

}