package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.shapes.*;




/**
 * Base class for all mazes.
 */
public class Irrengarten extends GenericMazeStructure {

    public Irrengarten() {
        shapes = new ShapeContainer(null);

    }

    public ShapeContainer applyRealization(MazeRealization realization) {
        ShapeContainer result = new ShapeContainer(context);

        for (IMazeShape2D s : shapes.getShapes()) {
            s.applyRealization(realization);
            result.add(s);
        }
       return result;

    }

    public ShapeContainer getShapes() {
        return shapes;
    }

    protected void addShape(IMazeShape2D shape) {
        shapes.add(shape);
    }


    public ShapeContext getContext() {
        return context;
    }

    protected void setContext(ShapeContext context) {
        this.context = context;
    }

    private ShapeContext context;
    private ShapeContainer shapes;

    private boolean debug;


}