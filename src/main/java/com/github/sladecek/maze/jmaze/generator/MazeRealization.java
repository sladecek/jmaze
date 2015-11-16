package com.github.sladecek.maze.jmaze.generator;

import java.util.BitSet;
import java.util.Vector;

import com.github.sladecek.maze.jmaze.util.BitSetIntervalPrinter;

/**
 * 
 * Data structure which defines one particular maze. Defines which walls are open 
 * on the maze. Also contains solution.
 *
 */
public final class MazeRealization {
	
	
	public MazeRealization(int wallCount) {
		allocateWalls(wallCount);
	}

	public boolean isWallClosed(int wall) {
		return isWallClosed.get(wall);
	}

	public void setWallClosed(int wall, boolean value) {
		isWallClosed.set(wall, value);
	}

	public Vector<Integer> getSolution() {
		return solution;
	}

	public void setSolution(Vector<Integer> solution) {
		this.solution = solution;
	}

	public String printClosedWalls() {
		return new BitSetIntervalPrinter(isWallClosed).printAsIntervals();
	}	

	private void allocateWalls(int wallCount) {
		isWallClosed = new BitSet(wallCount);
		isWallClosed.set(0, isWallClosed.size(), true);
	}
	
	private BitSet isWallClosed;

	private Vector<Integer> solution = new Vector<Integer>();

}
