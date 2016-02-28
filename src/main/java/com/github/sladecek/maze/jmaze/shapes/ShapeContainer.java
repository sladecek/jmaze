package com.github.sladecek.maze.jmaze.shapes;

import java.util.Vector;

/*
 * Collection of shapes.
 */
public final class ShapeContainer {
	public ShapeContainer(ShapeContext context) {
		shapes = new Vector<IMazeShape>();
		this.context = context;
	}

	public Vector<IMazeShape> getShapes() {
		return shapes;
	}

	public ShapeContext getContext() {
		return context;
	}

	public void add(IMazeShape s) {
		shapes.add(s);
	}

	private ShapeContext context;
	
	
	// TODO nemel by to byt array list - najit vsechny dalsi vector
	private Vector<IMazeShape> shapes;
}
