package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.generator.MazePath;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.Shapes;

import java.util.Random;

/**
 * Data structures for maze representation.
 */
public class MazeData {

    /**
     * Set properties for concrete maze such as size or colors.
     * @param value
     */

    public void setProperties(MazeProperties value) {
        properties = value;
    }


    public String getName() {
        return properties.getString("name");
    }


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


    public MazeProperties getProperties() {
        return properties;
    }

    protected MazeProperties properties;

    protected Random randomGenerator;

    protected MazeGraph graph = new MazeGraph();
    protected Shapes allShapes;

    public Random getRandomGenerator() {
        return randomGenerator;
    }

    public MazePath getPath() {
        return path;
    }

    protected Shapes pathShapes;
    protected MazePath path;
    protected Model3d model3d;
}
