package com.github.sladecek.maze.jmaze.rectangular;

import java.util.BitSet;
import java.util.Vector;

public class RectangularMazeBase {

	protected int height;
	protected BitSet isWallClosed;
	protected int width;
	protected Vector<Integer> solution = new Vector<Integer>();

	public RectangularMazeBase(int height, int width) {
		this.width = width;
		this.height = height;
	}

	protected void allocateWalls(int wallCount) {
		isWallClosed = new BitSet(wallCount);
		isWallClosed.set(0, isWallClosed.size(), true);
	}

	public int getRoomCount() {
		return width * height;
	}

	public int getStartRoom() {
		return 0;
	}

	public int getTargetRoom() {
		return getRoomCount() - 1;
	}

	public boolean isWallClosed(int wall) {
		return isWallClosed.get(wall);
	}

	public void setWallClosed(int wall, boolean value) {
		isWallClosed.set(wall, value);
	}

	public int getPictureHeight() {
		return height;
	}

	public int getPictureWidth() {
		return width;
	}

	public void setSolution(Vector<Integer> solution) {
		this.solution = solution;

	}

}