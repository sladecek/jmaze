package com.github.sladecek.maze.jmaze.shapes;

import java.util.Vector;


/*
 * Collection of shapes.
 */
public final class ShapeContainer {
	

	public ShapeContainer() {
		shapes = new Vector<IMazeShape>();
		isPolarCoordinates = false;
	}

	public int getPictureHeight() {
		return pictureHeight;
	}
	
	public void setPictureHeight(int pictureHeight) {
		this.pictureHeight = pictureHeight;
	}
	
	public int getPictureWidth() {
		return pictureWidth;
	}
	public void setPictureWidth(int pictureWidth) {
		this.pictureWidth = pictureWidth;
	}
	
	public Vector<IMazeShape> getShapes() {
		return shapes;
	}

	public void add(IMazeShape s) {
		shapes.add(s);
	}

	public boolean isPolarCoordinates() {
		return isPolarCoordinates;
	}

	public void setPolarCoordinates(boolean usePolarCoordinates) {
		this.isPolarCoordinates = usePolarCoordinates;
	}

	
	private Vector<IMazeShape> shapes;
	private boolean isPolarCoordinates;
	private int pictureHeight;
	private int pictureWidth;


}
