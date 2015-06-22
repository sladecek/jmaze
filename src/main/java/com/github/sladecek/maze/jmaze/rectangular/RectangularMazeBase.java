package com.github.sladecek.maze.jmaze.rectangular;


public class RectangularMazeBase {

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