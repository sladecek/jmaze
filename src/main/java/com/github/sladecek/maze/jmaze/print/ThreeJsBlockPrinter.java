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
			printMarks(tjs);	
			tjs.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return os.toString();
	}
	
	// TODO sloucit s predchozi metodou
	public final void printMaze(final String fileName) {		
		blockMaker.makeBlocks();
		try (ThreeJsComposer tjs = new ThreeJsComposer(fileName)) {			
			printBlocks(tjs);
			printMarks(tjs);	
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private void printBlocks(ThreeJsComposer tjs) throws IOException {
		tjs.beginList("blocks");
		for (Block b: blockMaker.getBlocks()) {
			if (!b.isMark()) {
				tjs.printPolyhedron(b.getPolyhedron(), b.getComment(), b.getColor());	
			}			
		}		
		final boolean insertComma = true;
		tjs.closeList(insertComma);
	}

	private void printMarks(ThreeJsComposer tjs) throws IOException {
		tjs.beginList("marks");
		for (Block b: blockMaker.getBlocks()) {
			if (b.isMark()) {
				assert b.getPolyhedron().size() == 1 : "mark blocks must have exactly one point";
				tjs.printMark(b.getPolyhedron().get(0), b.getComment(), b.getColor());	
			}			
		}		
		final boolean insertComma = false;
		tjs.closeList(insertComma);
	}

	private IBlockMaker blockMaker;

}
