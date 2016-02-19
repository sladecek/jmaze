package com.github.sladecek.maze.jmaze.printstyle;

public interface IPrintStyle {
	Color getBaseColor();
	Color getOuterWallColor();
	Color getInnerWallColor();
	Color getCornerColor();
	Color getSolutionWallColor();
	Color getSolutionMarkColor();
	Color getStartMarkColor();
	Color getTargetMarkColor();
	Color getHoleColor();
	Color getDebugWallColor();
	Color getDebugFloorColor();
	int getInnerWallWidth();
	int getOuterWallWidth();
	int getSolutionWallWidth();
	int getSolutionMarkWidth();
	int getStartTargetMarkWidth();
}
