package com.github.sladecek.maze.jmaze.print3dn;

import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;

/**
 * Creates 3D mesh of a maze.
 *
 * The class takes the information about visible rooms from a collenctions of {@link @FloorShape} objects. Using
 * maze geometry is defined by {@link IMaze3DMapper}, and required graphic paramaters from {@link Maze3DSizes}
 * generates a {@link Mesh}.
 */
public class MeshMaker {
    private Iterable<FloorShape> rooms;
    private IMaze3DMapper mapper;
    private Maze3DSizes sizes;
}
