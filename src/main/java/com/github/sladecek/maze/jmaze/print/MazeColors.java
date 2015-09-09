package com.github.sladecek.maze.jmaze.print;

public interface MazeColors {
	Color getBaseColor();
	Color getOuterWallColor();
	Color getInnerWallColor();
	Color getCornerColor();
	Color getHoleColor();
	Color getDebugWallColor();
	Color getDebugFloorColor();
}
