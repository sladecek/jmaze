package com.github.sladecek.maze.jmaze.colors;

public interface MazeColors {
	Color getBaseColor();
	Color getOuterWallColor();
	Color getInnerWallColor();
	Color getCornerColor();
	Color getHoleColor();
	Color getDebugWallColor();
	Color getDebugFloorColor();
}
