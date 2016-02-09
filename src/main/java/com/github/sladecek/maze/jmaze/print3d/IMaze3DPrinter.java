package com.github.sladecek.maze.jmaze.print3d;

import java.io.OutputStream;

public interface IMaze3DPrinter {

    void printBlocks(IBlockMaker blockMaker, OutputStream stream);

}