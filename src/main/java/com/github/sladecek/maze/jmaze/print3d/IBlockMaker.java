package com.github.sladecek.maze.jmaze.print3d;

/**
 * Converts 2D maze shapes to 3D blocks.
 */
public interface IBlockMaker {

	void makeBlocks();

	Iterable<Block> getBlocks();
}
