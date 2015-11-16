package com.github.sladecek.maze.jmaze.print;

import java.io.IOException;

/**
 * Save maze blocks in open scad file format.
 */
public class OpenScadBlockPrinter {
	
	public OpenScadBlockPrinter(final IBlockMaker blockMaker) {
		this.blockMaker = blockMaker;
	}
	
	public final void printMaze(final String fileName) {		
		blockMaker.makeBlocks();
		try (OpenScadComposer scad = new OpenScadComposer(fileName)) {
			
			scad.beginUnion();
			for (Block b: blockMaker.getBlocks()) {
				scad.printPolyhedron(b.getPolyhedron(), b.getComment(), b.getColor());
			}
			
			scad.closeUnion();
	
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private IBlockMaker blockMaker;
}