package com.github.sladecek.maze.jmaze.moebius;

import java.io.IOException;
import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.Point;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;
import com.github.sladecek.maze.jmaze.print.Block;
import com.github.sladecek.maze.jmaze.print.BlockMakerBase;
import com.github.sladecek.maze.jmaze.print.IBlockMaker;
import com.github.sladecek.maze.jmaze.print.IPrintableMaze;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print.MazeColors;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 *  Create list of solid blocks to make a 3D Moebius maze.
 *
 */
public final class MoebiusBlockMaker extends BlockMakerBase implements IBlockMaker  {
	
	private MoebiusMaze maze;
	public MoebiusBlockMaker(MoebiusMaze maze, MazeRealization realization, Maze3DSizes sizes, 
			MazeColors colors, double approxRoomSizeInmm) {
		super(sizes, colors, realization, approxRoomSizeInmm);
		this.maze = maze;
	}

	protected void prepareShapes(IPrintableMaze maze) {
		walls = new ArrayList<WallShape>();
		floors = new ArrayList<FloorShape>();
		for (IMazeShape shape: maze.getShapes()) {
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
	
	private void fillHolesInFloors(final MazeRealization realization)  {
		for (FloorShape hs: floors) {
			if (!hs.isOpen(realization)) {
				fillHoleInTheFloorOneRoom(hs);
			}
		}
	}
	
	private void printInnerWalls(final MazeRealization realization)  {		
		final double wallThickness = sizes.getInnerWallToCellRatio() / 2;
		
		for (WallShape wall: walls) {
			if (!wall.isOpen(realization)) {
				printWallElements(wallThickness, wall);
			}
		}
	}


	private void printOuterWalls()  {
		for (int cellX = 0; cellX < cellWidth; cellX++) {
			for (SouthNorth snWall: SouthNorth.values()) {
				ArrayList<Point> p = new ArrayList<Point>();
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
		prepareShapes(maze);
		cellHeight = maze.getPictureHeight();
		cellWidth = maze.getPictureWidth();
		maze3dMapper = new Moebius3dMapper(sizes, cellHeight, cellWidth);
		blocks = new ArrayList<Block>();
		printFloors();
		fillHolesInFloors(realization);
		printOuterWalls();
		printInnerWalls(realization);
		
	}


	private ArrayList<WallShape> walls;
	private ArrayList<FloorShape> floors;

	private int cellHeight;
	private int cellWidth;
	
}
