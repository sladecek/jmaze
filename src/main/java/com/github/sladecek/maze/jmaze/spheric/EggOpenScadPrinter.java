package com.github.sladecek.maze.jmaze.spheric;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print.IMazePrinter;
import com.github.sladecek.maze.jmaze.print.IPrintableMaze;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print.MazeColors;
import com.github.sladecek.maze.jmaze.print.OpenScadMazePrinter;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

public final class EggOpenScadPrinter extends OpenScadMazePrinter implements IMazePrinter 	
{
	private static final Logger LOGGER = Logger.getLogger("LOG"); 

	private EggGeometry egg;
	
	public EggOpenScadPrinter(final Maze3DSizes sizes, final MazeColors colors, final EggGeometry egg, final int equatorCellCnt) {
		super(sizes, colors, egg.computeBaseRoomSize_mm(equatorCellCnt));
		this.egg = egg;
	}

	protected void prepareShapes(final IPrintableMaze maze) {
		walls = new ArrayList<WallShape>();
		floor = new ArrayList<FloorShape>();
		for (IMazeShape shape: maze.getShapes()) {
			if (shape.getShapeType() == ShapeType.nonHole) {
				floor.add((FloorShape) shape);
			} else {
				if (shape.getShapeType() == ShapeType.innerWall) {
					walls.add((WallShape) shape);
				}
			}
		}
	}

	@Override
	protected void printShapes(final IPrintableMaze maze, final MazeRealization real) throws IOException {
 
		maze3dMapper = new Egg3dMapper(egg, (EggMaze) maze);
		
		scad.beginUnion();
		printFloors();
		printWalls(real);
		scad.closeUnion();
	}
	
	private void printFloors() throws IOException {
		scad.beginUnion();
		for (FloorShape hs: floor) {
				LOGGER.log(Level.INFO, hs.toString());
				LOGGER.log(Level.INFO, "room");
				printFloorWithHoleOneRoom(hs.getY(), hs.getX());
				LOGGER.log(Level.INFO, "hole");
			fillHoleInTheFloorOneRoom(hs);	
			LOGGER.log(Level.INFO, "end");
		}
		scad.closeUnion();	
	}

	private void printWalls(final MazeRealization real) throws IOException {
		
		final double wt = sizes.getInnerWallToCellRatio() / 2;
		
		for (WallShape wall: walls) {
			if (!wall.isOpen(real)) {
				LOGGER.log(Level.INFO, "wall " + wall + " is closed");
				printWallElements(wt, wall);
				
			}
		}
	}
	
	private ArrayList<WallShape> walls;
	private ArrayList<FloorShape> floor;

}
