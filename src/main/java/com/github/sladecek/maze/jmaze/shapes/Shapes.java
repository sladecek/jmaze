package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.generator.MazePick;

import java.util.Vector;

/*
 * Collection of shapes.
 */
public final class Shapes {
    public Shapes(ShapeContext context) {

        this.context = context;
    }

    public Shapes(boolean isPolar, int h, int w) {
        this.context = new ShapeContext(isPolar, h, w);
    }


    public Shapes applyRealization(MazePick realization) {
        Shapes result = new Shapes(context);

        for (IPrintableMazeShape2D s : getShapes()) {
            s.applyPick(realization);
            result.add(s);
        }
        return result;
    }

    public Vector<IPrintableMazeShape2D> getShapes() {
        return shapes;
    }

    public ShapeContext getContext() {
        return context;
    }

    public void add(IPrintableMazeShape2D s) {
        shapes.add(s);
    }

    public int length() { return shapes.size(); }
    private ShapeContext context;


    // TODO nemel by to byt array list - najit vsechny dalsi vector
    private Vector<IPrintableMazeShape2D> shapes = new Vector<IPrintableMazeShape2D>();
}
