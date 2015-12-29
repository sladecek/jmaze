package com.github.sladecek.maze.jmaze.print3d;


/**
 * Create list of solid blocks to make a 3D maze.
 * @author sladecek
 *
 */
public interface IBlockMaker {
	void makeBlocks();
	Iterable<Block> getBlocks();
}