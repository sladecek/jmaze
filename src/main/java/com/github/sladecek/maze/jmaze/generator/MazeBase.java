package com.github.sladecek.maze.jmaze.generator;

import java.util.BitSet;
import java.util.Vector;

/**
 * 
 * Data structure which defines one particular maze. Defines which walls are open 
 * on the maze. Also contains solution.
 *
 */
public class MazeBase {
	
	protected BitSet isWallClosed;
	protected Vector<Integer> solution = new Vector<Integer>();

	
	protected void allocateWalls(int wallCount) {
		isWallClosed = new BitSet(wallCount);
		isWallClosed.set(0, isWallClosed.size(), true);
	}


	public boolean isWallClosed(int wall) {
		return isWallClosed.get(wall);
	}

	public void setWallClosed(int wall, boolean value) {
		isWallClosed.set(wall, value);
	}

	public void setSolution(Vector<Integer> solution) {
		this.solution = solution;

	}

	
}
