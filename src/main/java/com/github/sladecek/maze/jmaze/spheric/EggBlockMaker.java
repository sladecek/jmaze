package com.github.sladecek.maze.jmaze.spheric;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
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
 * Create list of solid blocks to make a 3D Moebius maze.
 */
public final class EggBlockMaker extends BlockMakerBase implements IBlockMaker 	{
		
	public EggBlockMaker(EggMaze maze, MazeRealization realization, Maze3DSizes sizes, 
						 MazeColors colors,  final EggGeometry egg, final int equatorCellCnt) {
		super(sizes, colors, realization, egg.computeBaseRoomSizeInmm(equatorCellCnt));
		this.egg = egg;
		this.maze = maze;
	}

	@Override
	public void makeBlocks() {
	maze3dMapper = new Egg3dMapper(egg, (EggMaze) maze);
	prepareShapes(maze);
	blocks = new ArrayList<Block>();
		printFloors();
		printWalls(realization);
		
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
	
	private void printFloors()  {
		for (FloorShape hs: floor) {
				LOGGER.log(Level.INFO, hs.toString());
				LOGGER.log(Level.INFO, "room");
				printFloorWithHoleOneRoom(hs.getY(), hs.getX());
				LOGGER.log(Level.INFO, "hole");
			fillHoleInTheFloorOneRoom(hs);	
			LOGGER.log(Level.INFO, "end");
		}
	}

	private void printWalls(final MazeRealization realization)  {		
		final double wt = sizes.getInnerWallToCellRatio() / 2;		
		for (WallShape wall: walls) {
			if (!wall.isOpen(realization)) {
				LOGGER.log(Level.INFO, "wall " + wall + " is closed");
				printWallElements(wt, wall);				
			}
		}
	}
	
	private static final Logger LOGGER = Logger.getLogger("LOG");
	private EggGeometry egg;
	private EggMaze maze;
	private ArrayList<WallShape> walls;
	private ArrayList<FloorShape> floor;

}
