package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;

import java.util.Vector;

/*
 * Collection of shapes.
 */
public final class ShapeContainer {
    public ShapeContainer(ShapeContext context) {

        this.context = context;
    }

    public ShapeContainer(boolean isPolar, int h, int w) {
        this.context = new ShapeContext(isPolar, h, w);
    }


    public ShapeContainer applyRealization(MazeRealization realization) {
        ShapeContainer result = new ShapeContainer(context);

        for (IMazeShape2D s : getShapes()) {
            s.applyRealization(realization);
            result.add(s);
        }
        return result;
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
    private Vector<IMazeShape2D> shapes = new Vector<IMazeShape2D>();
}
