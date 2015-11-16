package com.github.sladecek.maze.jmaze.print;

public final class Maze3DSizes {

	public double getCellSizeInmm() {
		return cellSizeInmm;
	}
	public void setCellSizeInmm(double cellSizeInmm) {
		this.cellSizeInmm = cellSizeInmm;
	}
	
	public double getBaseThicknessInmm() {
		return baseThicknessInmm;
	}
	public void setBaseThicknessInmm(double baseThicknessInmm) {
		this.baseThicknessInmm = baseThicknessInmm;
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
	public double getWallHeightInmm() {
		return wallHeightInmm;
	}
	public void setWallHeightInmm(double wallHeightInmm) {
		this.wallHeightInmm = wallHeightInmm;
	}


	private double cellSizeInmm = 4;
	private double baseThicknessInmm = 1;
	private double innerWallToCellRatio = 0.2;
	private double outerWallToCellRatio = 0.4;
	private double wallHeightInmm = 5;

}
