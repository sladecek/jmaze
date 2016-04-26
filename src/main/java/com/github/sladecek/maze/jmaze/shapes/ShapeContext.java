package com.github.sladecek.maze.jmaze.shapes;

/*
 * Contextual information for a collection of shapes.
 */
public class ShapeContext {

	public ShapeContext(boolean isPolarCoordinates, int pictureHeight, int pictureWidth, int resolutionX, int resolutionY,
			int markOffsetXPercent, int markOffsetYPercent) {
		super();
		this.isPolarCoordinates = isPolarCoordinates;
		this.pictureHeight = pictureHeight;
		this.pictureWidth = pictureWidth;
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
		this.markOffsetXPercent = markOffsetXPercent;
		this.markOffsetYPercent = markOffsetYPercent;
	}

	private boolean isPolarCoordinates;
	private int pictureHeight;
	private int pictureWidth;
	private int resolutionX;
	private int resolutionY;
	private int markOffsetXPercent;
	private int markOffsetYPercent;

	public boolean isPolarCoordinates() {
		return isPolarCoordinates;
	}

	public int getPictureHeight() {
		return pictureHeight;
	}

	public int getPictureWidth() {
		return pictureWidth;
	}

	public int getResolutionX() {
		return resolutionX;
	}

	public int getResolutionY() {
		return resolutionY;
	}

	public int getMarkOffsetXPercent() {
		return markOffsetXPercent;
	}

	public int getMarkOffsetYPercent() {
		return markOffsetYPercent;
	}
}
