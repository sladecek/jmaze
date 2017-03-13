package com.github.sladecek.maze.jmaze.print3dn;

/**
 * Conditions for face presence.
 */
public enum WallConditionType {
    wall,
    wallOnlyOuter,
    wallOrPreceding,
    notWall,
    notWallAndNotPreceding,
    notPreceding,
    notFloor,

    notPrecedingAndNotFloor,
    notWallAndNotFloor,
    precedingOuter,


}
