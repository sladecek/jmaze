package com.github.sladecek.maze.jmaze.print;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ThreeJsBlockPrinter {
	public ThreeJsBlockPrinter(final IBlockMaker blockMaker) {
		this.blockMaker = blockMaker;
	}
	
	public final String printMazeToString() {
		blockMaker.makeBlocks();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ThreeJsComposer tjs;
		try {
			tjs = new ThreeJsComposer(os);
			printBlocks(tjs);	
			tjs.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return os.toString();
	}
	
	public final void printMaze(final String fileName) {		
		blockMaker.makeBlocks();
		try (ThreeJsComposer tjs = new ThreeJsComposer(fileName)) {
			
			printBlocks(tjs);
			
	
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private void printBlocks(ThreeJsComposer tjs) throws IOException {
		for (Block b: blockMaker.getBlocks()) {
			tjs.printPolyhedron(b.getPolyhedron(), b.getComment(), b.getColor());
		}
	}

	private IBlockMaker blockMaker;

}
