package com.github.sladecek.maze.jmaze.moebius;

import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;
import com.github.sladecek.maze.jmaze.print3d.Block;
import com.github.sladecek.maze.jmaze.print3d.BlockMakerBase;
import com.github.sladecek.maze.jmaze.print3d.IBlockMaker;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 *  Create list of solid blocks to make a 3D Moebius maze.
 *
 */
public final class MoebiusBlockMaker extends BlockMakerBase implements IBlockMaker  {
	
	public MoebiusBlockMaker( ShapeContainer shapes, Maze3DSizes sizes, 
			IPrintStyle colors, double approxRoomSizeInmm) {
		super(shapes, sizes, colors, approxRoomSizeInmm);
	}

	
	protected void prepareShapes() {		
		walls = new ArrayList<WallShape>();
		floors = new ArrayList<FloorShape>();

		cellHeight = shapes.getContext().getPictureHeight();
		cellWidth = shapes.getContext().getPictureWidth();

		for (IMazeShape shape: shapes.getShapes()) {
			if (shape.getShapeType() == ShapeType.hole) {
				floors.add((FloorShape) shape);
			} else {
				if (shape.getShapeType() == ShapeType.innerWall) {
					walls.add((WallShape) shape);
				}
			}
		}
	}
	
	private void printFloors()  {

		for (int cellX = 0; cellX < cellWidth; cellX++) {
			for (int cellY = 0; cellY < cellHeight; cellY++) {

				printFloorWithHoleOneRoom(cellY, cellX);				
			}
		}
	}
	
	private void fillHolesInFloors()  {
		for (FloorShape hs: floors) {
				fillHoleInTheFloorOneRoom(hs);
			}
		
	}
	
	private void printInnerWalls()  {		
		final double wallThickness = sizes.getInnerWallToCellRatio() / 2;
		
		for (WallShape wall: walls) {
				printWallElements(wallThickness, wall);
			}
		
	}


	private void printOuterWalls()  {
		for (int cellX = 0; cellX < cellWidth; cellX++) {
			for (SouthNorth snWall: SouthNorth.values()) {
				ArrayList<Point3D> p = new ArrayList<Point3D>();
				for (EastWest ew: EastWest.values()) {
					for (SouthNorth snEdge: SouthNorth.values()) {
						for (UpDown ud: UpDown.values()) {						
							p.add(maze3dMapper.mapCorner(cellX, ew, ud, snWall, snEdge));
						}
					}
				}
				printPolyhedron(p, "outer wall " + cellX, colors.getOuterWallColor());
			}
		}
	}

	@Override
	public void makeBlocks() {
		prepareShapes();
		maze3dMapper = new Moebius3dMapper(sizes, cellHeight, cellWidth);
		blocks = new ArrayList<Block>();
		printFloors();
		fillHolesInFloors();
		printOuterWalls();
		printInnerWalls();
		
	}


	private ArrayList<WallShape> walls;
	private ArrayList<FloorShape> floors;

	private int cellHeight;
	private int cellWidth;

}
