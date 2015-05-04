package com.github.sladecek.maze.jmaze;

public class Maze3DSizes {

	double cellSize_mm = 4;
	double baseThickness_mm = 5;
	double innerWallToCellRatio = 0.3;
	double outerWallToCellRatio = 0.1;
	double wallHeight_mm = 10;

	public double getCellSize_mm() {
		return cellSize_mm;
	}
	public void setCellSize_mm(double cellSize_mm) {
		this.cellSize_mm = cellSize_mm;
	}
	
	public double getBaseThickness_mm() {
		return baseThickness_mm;
	}
	public void setBaseThickness_mm(double baseThickness_mm) {
		this.baseThickness_mm = baseThickness_mm;
	}
	public double getInnerWallToCellRatio() {
		return innerWallToCellRatio;
	}
	public void setInnerWallToCellRatio(double innerWallToCellRatio) {
		this.innerWallToCellRatio = innerWallToCellRatio;
	}
	public double getOuterWallToCellRatio() {
		return outerWallToCellRatio;
	}
	public void setOuterWallToCellRatio(double outerWallToCellRatio) {
		this.outerWallToCellRatio = outerWallToCellRatio;
	}
	public double getWallHeight_mm() {
		return wallHeight_mm;
	}
	public void setWallHeight_mm(double wallHeight_mm) {
		this.wallHeight_mm = wallHeight_mm;
	}


}
