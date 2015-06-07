package com.github.sladecek.maze.jmaze.moebius;

import java.security.InvalidParameterException;

import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.geometry.Point;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;

public class Moebius3dMapper implements IMaze3DMapper {


	
	
	public Moebius3dMapper(Maze3DSizes sizes, int height, int width) {
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

		deformator = new MoebiusDeformator(length_mm);


	}

	MoebiusDeformator deformator;
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
		
		
	@Override
	public Point mapPoint(int cellY, int cellX, double offsetY,
			double offsetX, double z) {
		double y = (offsetY + cellY-height/2) * cellStep_mm;
		double x = (offsetX + cellX) * cellStep_mm;	
		return deformator.transform(new Point(y,x,z));
	}


	@Override
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
		
		return deformator.transform(new Point(x,y,z));
	}
	
}
