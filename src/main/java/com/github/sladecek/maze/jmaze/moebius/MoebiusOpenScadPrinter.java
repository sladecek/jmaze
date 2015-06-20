package com.github.sladecek.maze.jmaze.moebius;

import java.io.IOException;
import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.Point;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;
import com.github.sladecek.maze.jmaze.print.IMazePrinter;
import com.github.sladecek.maze.jmaze.print.IPrintableMaze;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
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
	public MoebiusOpenScadPrinter(Maze3DSizes sizes) {
		super(sizes,1);
	}

	protected void prepareShapes(IPrintableMaze maze) {
		walls = new ArrayList<WallShape>();
		floors = new ArrayList<FloorShape>();
		for(IMazeShape shape: maze.getShapes()) {
			if (shape.getShapeType() == ShapeType.nonHole ) {
				floors.add((FloorShape)shape);
			} else {
				if (shape.getShapeType() == ShapeType.innerWall) {
					walls.add((WallShape)shape);
				}
			}
		}
	}

	@Override
	protected void printShapes(IPrintableMaze maze) throws IOException {
 
		cellHeight = maze.getPictureHeight();
		cellWidth = maze.getPictureWidth();
		maze3dMapper = new Moebius3dMapper(sizes, cellHeight, cellWidth);
		scad.beginUnion();
		printFloors();
		fillHolesInFloors();
		printOuterWalls();
		printInnerWalls();
		scad.closeUnion();
	}

	private void printFloors() throws IOException {
		scad.beginUnion();
		for (int cellX = 0; cellX < cellWidth; cellX++) {
			for (int cellY = 0; cellY < cellHeight; cellY++) {
			
			/*	if (cellY > 1) continue;
				if (cellX > 1) continue;
*/
				printFloorWithHoleOneRoom(cellX, cellY);				
			}
		}
		scad.closeUnion();	
	}

	
	private void fillHolesInFloors() throws IOException {
		for (FloorShape hs: floors) {
			fillHoleInTheFloorOneRoom(hs);
		}
	}

	
	private void printInnerWalls() throws IOException {
		
		final double wallThickness = sizes.getInnerWallToCellRatio()/2;
		
		for (WallShape wall: walls) {
			printWallsOneRoom(wallThickness, wall);
			
		}
	}


	private void printOuterWalls() throws IOException {
		scad.beginUnion();
		for (int cellX = 0; cellX < cellWidth; cellX++) {
			for(SouthNorth snWall: SouthNorth.values()) {
				ArrayList<Point> p = new ArrayList<Point>();
				for(EastWest ew: EastWest.values()) {
					for(SouthNorth snEdge: SouthNorth.values()) {
						for(UpDown ud: UpDown.values()) {						
							p.add(maze3dMapper.mapCorner(cellX, ew, ud, snWall, snEdge));
						}
					}
				}
				scad.printPolyhedron(p, "outer wall "+cellX,  outerWallColor);
			}
		}
		scad.closeUnion();
	}


	private ArrayList<WallShape> walls;
	private ArrayList<FloorShape> floors;

	int cellHeight;
	int cellWidth;

	
}
