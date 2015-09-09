package com.github.sladecek.maze.jmaze.generator;

import java.util.Vector;

import com.github.sladecek.maze.jmaze.util.BitSetWithPrint;

/**
 * 
 * Data structure which defines one particular maze. Defines which walls are open 
 * on the maze. Also contains solution.
 *
 */
public class MazeRealization {
	
	protected BitSetWithPrint isWallClosed;
	protected Vector<Integer> solution = new Vector<Integer>();

	
	protected void allocateWalls(int wallCount) {
		isWallClosed = new BitSetWithPrint(wallCount);
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

	public String printClosedWalls()
	{
		return isWallClosed.printAsIntervals();
	}
	
	
}
