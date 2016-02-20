package com.github.sladecek.maze.jmaze.print3d;

import java.io.OutputStream;

/*
 * Prints 3D blocks to stream.
 */
public interface IMaze3DPrinter {

    void printBlocks(IBlockMaker blockMaker, OutputStream stream);

}