package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

/**
 *
 */
public abstract class BaseMaze implements IMaze {
    public BaseMaze() {
        defaultProperties.put("wallHeight", 10.0);
        defaultProperties.put("cellSize", 2.0);
        defaultProperties.put("innerWallToPixelRatio", 1.0);
    }

    @Override
    public void setProperties(MazeProperties value) {
        properties = value;
    }

    @Override
    public MazeProperties getDefaultProperties() {
        return defaultProperties;
    }

    @Override
    public String getName() {
        return defaultProperties.getString("name");
    }

    @Override
    public GenericMazeStructure getGraph() {
        return graph;
    }

    @Override
    public ShapeContainer getFlatModel() {
        return flatModel;
    }

    protected GenericMazeStructure graph;
    protected ShapeContainer flatModel;


    protected MazeProperties defaultProperties = new MazeProperties();
    protected MazeProperties properties;

}
