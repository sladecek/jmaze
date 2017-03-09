package com.github.sladecek.maze.jmaze.shapes;

import java.util.Vector;

/*
 * Collection of shapes.
 */
public final class ShapeContainer {
    public ShapeContainer(ShapeContext context) {
        shapes = new Vector<IMazeShape2D>();
        this.context = context;
    }

    public Vector<IMazeShape2D> getShapes() {
        return shapes;
    }

    public ShapeContext getContext() {
        return context;
    }

    public void add(IMazeShape2D s) {
        shapes.add(s);
    }

    public int length() { return shapes.size(); }
    private ShapeContext context;


    // TODO nemel by to byt array list - najit vsechny dalsi vector
    private Vector<IMazeShape2D> shapes;
}
