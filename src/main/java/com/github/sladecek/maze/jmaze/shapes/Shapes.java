package com.github.sladecek.maze.jmaze.shapes;
//REV1
import com.github.sladecek.maze.jmaze.generator.MazePath;

import java.util.ArrayList;

/*
 * Collection of shapes.
 */
public final class Shapes {
    public Shapes(ShapeContext context) {
        this.context = context;
    }

    public Shapes(boolean isPolar, int h, int w, int margin) {
        this.context = new ShapeContext(isPolar, h, w, margin);
    }

    public Shapes applyRealization(MazePath realization) {
        Shapes result = new Shapes(context);

        for (IMazeShape s : getShapes()) {
            s.applyPath(realization);
            result.add(s);
        }
        return result;
    }

    public ArrayList<IMazeShape> getShapes() {
        return shapes;
    }

    public ShapeContext getContext() {
        return context;
    }

    public void add(IMazeShape s) {
        shapes.add(s);
    }

    public int length() {
        return shapes.size();
    }

    private final ShapeContext context;


    private final ArrayList<IMazeShape> shapes = new ArrayList<>();
}
