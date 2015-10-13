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
		ThreeJsWriter tjs;
		try {
			tjs = new ThreeJsWriter(os);
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
		try (ThreeJsWriter tjs = new ThreeJsWriter(fileName)) {
			
			printBlocks(tjs);
			
	
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private void printBlocks(ThreeJsWriter tjs) throws IOException {
		for (Block b: blockMaker.getBlocks()) {
			tjs.printPolyhedron(b.getPolyhedron(), b.getComment(), b.getColor());
		}
	}

	private IBlockMaker blockMaker;

}
