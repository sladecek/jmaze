package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;

/**
 * Represents one type of mazes such as rectangular, triangular or spherical maze.
 */
interface IMaze {

    /**
     * Generate maze graph and maze shapes. Usually uses MazeData structures for results.
     */
    void buildMazeGraphAndShapes();



    /**
     * Create 3D mapper responsible for embedding planar maze into three dimensional space. May be return null for
     * mazes that can be printed in 2D only.
     */
    IMaze3DMapper create3DMapper();

}
