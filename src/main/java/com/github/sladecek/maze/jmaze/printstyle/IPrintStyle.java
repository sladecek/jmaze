package com.github.sladecek.maze.jmaze.printstyle;

public interface IPrintStyle {
	Color getBaseColor();
	Color getOuterWallColor();
	Color getInnerWallColor();
	Color getCornerColor();
	Color getSolutionWallColor();
	Color getHoleColor();
	Color getDebugWallColor();
	Color getDebugFloorColor();
	int getInnerWallWidth();
	int getOuterWallWidth();
	int getSolutionWallWidth();
}
