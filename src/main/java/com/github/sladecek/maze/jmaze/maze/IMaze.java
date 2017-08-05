package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

/**
 * One type of mazes such as rectangular, triangular or spherical mazes.
 */
public interface IMaze {
    /**
     * Generate maze graph and maze FlatModel.
     */
    void buildMaze();

    /**
     * Default properties for this type of the maze.
     * @return
     */
    MazeProperties getDefaultProperties();

    /**
     * Set properties for concrete maze such as size or colors.
     * @param value
     */
    void setProperties(MazeProperties value);

    /**
     * Create 3D mapper responsible for embedding planar maze into threedimensional space. May be return null for
     * mazes that can be printed in 2D only.
     * @return
     */
    IMaze3DMapper create3DMapper();

    /**
     * Certain maze types  such as the rectangular maze, can be printed in 2D, certain types, such as the
     * Moebius maze cannot.
     * @return
     */
    boolean canBePrintedIn2D();

    String getName();

    GenericMazeStructure getGraph();

    ShapeContainer getFlatModel();
}
