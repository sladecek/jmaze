package com.github.sladecek.maze.jmaze.shapes;

import java.util.Vector;


/*
 * Collection of shapes.
 */
public final class ShapeContainer {
	

	private ShapeContext context;


    public ShapeContainer(ShapeContext context) {
		shapes = new Vector<IMazeShape>();
		this.context = context;
	}

	public int getPictureHeight() {
		return context.getPictureHeight();
	}
	
	
	public int getPictureWidth() {
		return this.context.getPictureWidth();		
	}
		
	public Vector<IMazeShape> getShapes() {
		return shapes;
	}

	public void add(IMazeShape s) {
		shapes.add(s);
	}


	private Vector<IMazeShape> shapes;


    public ShapeContext getContext() {
        return context;
    }
	


}
