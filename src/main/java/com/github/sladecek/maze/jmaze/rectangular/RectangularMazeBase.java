package com.github.sladecek.maze.jmaze.rectangular;

import com.github.sladecek.maze.jmaze.generator.MazeBase;

public class RectangularMazeBase extends MazeBase {

	protected int height;
	protected int width;

	public RectangularMazeBase(int height, int width) {
		this.width = width;
		this.height = height;
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

	public int getPictureHeight() {
		return height;
	}

	public int getPictureWidth() {
		return width;
	}


}