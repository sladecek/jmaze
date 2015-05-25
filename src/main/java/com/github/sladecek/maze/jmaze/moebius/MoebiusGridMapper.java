package com.github.sladecek.maze.jmaze.moebius;

import java.security.InvalidParameterException;

import com.github.sladecek.maze.jmaze.EastWest;
import com.github.sladecek.maze.jmaze.Point;
import com.github.sladecek.maze.jmaze.SouthNorth;
import com.github.sladecek.maze.jmaze.UpDown;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;

/// Converts integer points of a maze into 3D points without Moebius deformation.
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
		height_mm = sizes.getCellSize_mm() * height + innerWallThickness_mm * (height -1) + 2* outerWallThickness_mm;
		// TODO moebius only
		length_mm = sizes.getCellSize_mm() * width + innerWallThickness_mm * width;
		cellStep_mm = sizes.getCellSize_mm() + innerWallThickness_mm;
	}

	Maze3DSizes sizes;
	int width;
	int height;
	double length_mm;
	double height_mm;
	double innerWallThickness_mm;
	double outerWallThickness_mm;
	double cellStep_mm;
	
	public double getLength_mm() {
		return length_mm;
	}
	
	public double getHeight_mm() {
		return height_mm;
	}
	
	Point getBasePointWithOffset(int cellY, int cellX, double offsetY, double offsetX, double z) {
		double y = (offsetY + cellY-height/2) * cellStep_mm;
		double x = (offsetX + cellX) * cellStep_mm;		
		return new Point(x,y,z);
	}
	
	Point getBasePointWithOffsetAndZ(int cellY, int cellX, UpDown ud, double offsetY, double offsetX) {
		double z = ud == UpDown.down ? 0 : sizes.getBaseThickness_mm();
		return getBasePointWithOffset(cellY, cellX,  offsetY, offsetX, z);
	}
	
	
	Point getBasePoint(int cellY, int cellX, UpDown ud, SouthNorth sn, EastWest ew) {
		double offsetY = (sn == SouthNorth.south) ? 0 : 1;
		double offsetX = (ew == EastWest.east) ? 0 : 1;		
		return getBasePointWithOffsetAndZ(cellY, cellX,  ud, offsetY, offsetX);
		
	}

	public Point getOuterPoint(int cellX, EastWest ew, UpDown ud,
			SouthNorth snWall, SouthNorth snEdge) {
		
		double x = (((ew == EastWest.east) ? 0 : 1) + cellX) * cellStep_mm;
		double y = height_mm/2;
		if (snWall == snEdge) {
			y -= outerWallThickness_mm;
		}
		if (snWall == SouthNorth.north) {
			y *= -1;
		}
	
		double z = ud == UpDown.down ? 0 : sizes.getWallHeight_mm();
		return new Point(x,y,z);
	}
	

}
