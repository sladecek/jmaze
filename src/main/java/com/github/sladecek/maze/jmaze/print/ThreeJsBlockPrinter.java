package com.github.sladecek.maze.jmaze.print;

import java.io.IOException;

public class ThreeJsBlockPrinter {
	public ThreeJsBlockPrinter(final IBlockMaker blockMaker) {
		this.blockMaker = blockMaker;
	}
	
	public final void printMaze(final String fileName) {		
		blockMaker.makeBlocks();
		try (ThreeJsWriter tjs = new ThreeJsWriter(fileName)) {
			
			for (Block b: blockMaker.getBlocks()) {
				tjs.printPolyhedron(b.getPolyhedron(), b.getComment(), b.getColor());
			}
			
	
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private IBlockMaker blockMaker;

}
