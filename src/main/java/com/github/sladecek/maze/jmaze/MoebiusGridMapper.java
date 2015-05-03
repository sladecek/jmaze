package com.github.sladecek.maze.jmaze;

import java.security.InvalidParameterException;

/// Converts integer points of a maze into 3D points without deformation.
public class MoebiusGridMapper {

	public MoebiusGridMapper(Maze3DSizes sizes, int height, int width) {
		super();
		this.sizes = sizes;
		this.width = width;
		this.height = height;
		if (width %2 != 0) {
			throw new InvalidParameterException("GridMapper - maze width must be even");
		}
		innerWallThickness_mm = sizes.getCellSize_mm() * sizes.getInnerWallToCellRatio();
		outerWallThickness_mm = sizes.getCellSize_mm() * sizes.getOuterWallToCellRatio();
		width_mm = sizes.getCellSize_mm() * width + innerWallThickness_mm * (width -1) + 2* outerWallThickness_mm;
		// TODO moebius only
		length_mm = sizes.getCellSize_mm() * width + innerWallThickness_mm * width;
		cellStep_mm = sizes.getCellSize_mm() + innerWallThickness_mm;
	}

	Maze3DSizes sizes;
	int width;
	int height;
	double length_mm;
	double width_mm;
	double innerWallThickness_mm;
	double outerWallThickness_mm;
	double cellStep_mm;
	
	public double getLength_mm() {
		return length_mm;
	}
	
	public double getWidth_mm() {
		return width_mm;
	}
	
	Point getOuterPoint(int cellX, UpDown ud, SouthNorth sn, InnerOuter io) {
		double x = cellX * cellStep_mm;
		double y = width_mm/2;
		if (io == InnerOuter.inner) {
			y -= outerWallThickness_mm;
		}
		if (sn == SouthNorth.north) {
			y *= -1;
		}
		double z = ud == UpDown.down ? 0 : sizes.getWallHeight_mm();
		return new Point(x,y,z);
	}

}
