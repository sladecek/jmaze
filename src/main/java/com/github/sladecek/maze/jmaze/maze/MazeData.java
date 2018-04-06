package com.github.sladecek.maze.jmaze.maze;
//REV1
import com.github.sladecek.maze.jmaze.generator.MazePath;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.Shapes;

import java.util.Random;

/**
 * Data structures for maze representation.
 */
public class MazeData {

    public MazeGraph getGraph() {
        return graph;
    }

    public Model3d getModel3d() {
        return model3d;
    }

    public Shapes getPathShapes() {
        return pathShapes;
    }

    public Shapes getAllShapes() {
        return allShapes;
    }

    MazeProperties getProperties() {
        return properties;
    }

    /**
     * Set properties for concrete maze such as size or colors.
     */
    public void setProperties(MazeProperties value) {
        properties = value;
    }

    public Random getRandomGenerator() {
        return randomGenerator;
    }

    public MazePath getPath() {
        return path;
    }
    protected final MazeGraph graph = new MazeGraph();
    protected MazeProperties properties;
    protected Random randomGenerator;
    protected Shapes allShapes;
    Shapes pathShapes;
    MazePath path;
    Model3d model3d;
}
