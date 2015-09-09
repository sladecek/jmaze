package com.github.sladecek.maze.jmaze.moebius;

import java.io.IOException;
import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.Point;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;
import com.github.sladecek.maze.jmaze.print.IMazePrinter;
import com.github.sladecek.maze.jmaze.print.IPrintableMaze;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print.MazeColors;
import com.github.sladecek.maze.jmaze.print.OpenScadMazePrinter;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 *  Print rooms of 3D Moebius maze as OpenScad file.
 *
 */
public class MoebiusOpenScadPrinter extends OpenScadMazePrinter implements IMazePrinter  {
	public MoebiusOpenScadPrinter(Maze3DSizes sizes, MazeColors colors) {
		super(sizes, colors, 1);
	}

	protected void prepareShapes(IPrintableMaze maze) {
		walls = new ArrayList<WallShape>();
		floors = new ArrayList<FloorShape>();
		for(IMazeShape shape: maze.getShapes()) {
			if (shape.getShapeType() == ShapeType.hole ) {
				floors.add((FloorShape)shape);
			} else {
				if (shape.getShapeType() == ShapeType.innerWall) {
					walls.add((WallShape)shape);
				}
			}
		}
	}

	@Override
	protected void printShapes(final IPrintableMaze maze, final MazeRealization realization) throws IOException {
 
		cellHeight = maze.getPictureHeight();
		cellWidth = maze.getPictureWidth();
		maze3dMapper = new Moebius3dMapper(sizes, cellHeight, cellWidth);
		scad.beginUnion();
		printFloors();
		fillHolesInFloors(realization);
		printOuterWalls();
		printInnerWalls(realization);
		scad.closeUnion();
	}

	private void printFloors() throws IOException {
		scad.beginUnion();
		for (int cellX = 0; cellX < cellWidth; cellX++) {
			for (int cellY = 0; cellY < cellHeight; cellY++) {

				printFloorWithHoleOneRoom(cellY, cellX);				
			}
		}
		scad.closeUnion();	
	}

	
	private void fillHolesInFloors(final MazeRealization realization) throws IOException {
		for (FloorShape hs: floors) {
			if (!hs.isOpen(realization)) {
				fillHoleInTheFloorOneRoom(hs);
			}
		}
	}

	
	private void printInnerWalls(final MazeRealization realization) throws IOException {		
		final double wallThickness = sizes.getInnerWallToCellRatio() / 2;
		
		for (WallShape wall: walls) {
			if (!wall.isOpen(realization)) {
				printWallElements(wallThickness, wall);
			}
		}
	}


	private void printOuterWalls() throws IOException {
		scad.beginUnion();
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
				scad.printPolyhedron(p, "outer wall " + cellX, colors.getOuterWallColor());
			}
		}
		scad.closeUnion();
	}


	private ArrayList<WallShape> walls;
	private ArrayList<FloorShape> floors;

	int cellHeight;
	int cellWidth;

	
}
