package com.github.sladecek.maze.jmaze.print3dn;

/**
 * Represents a position in one dimension Room3D mesh.
 *
 * The 'z' coordinate has not a 'inBig' corrdinate
 */
public enum RoomPosition {
    outSmall,                       // outer wall, smallest coordinate in the mesh (mesh[0])
    inSmall,                        // inner wall
    inBig,                          // inner wall on the other side
    outBig                          // outer wall, largest coordinate in the mesh
}
