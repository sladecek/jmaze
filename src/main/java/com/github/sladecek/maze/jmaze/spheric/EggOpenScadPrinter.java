package com.github.sladecek.maze.jmaze.spheric;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print.IMazePrinter;
import com.github.sladecek.maze.jmaze.print.IPrintableMaze;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print.OpenScadMazePrinter;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

public class EggOpenScadPrinter extends OpenScadMazePrinter implements IMazePrinter 	
{
	private final static Logger log = Logger.getLogger("LOG"); 

	private EggGeometry egg;
	
	public EggOpenScadPrinter(Maze3DSizes sizes, EggGeometry egg, int equatorCellCnt) {
		super(sizes,egg.computeBaseRoomSize_mm(equatorCellCnt));
		this.egg = egg;
	}

	protected void prepareShapes(IPrintableMaze maze) {
		walls = new ArrayList<WallShape>();
		floor = new ArrayList<FloorShape>();
		for(IMazeShape shape: maze.getShapes()) {
			if (shape.getShapeType() == ShapeType.nonHole ) {
				floor.add((FloorShape)shape);
			} else {
				if (shape.getShapeType() == ShapeType.innerWall) {
					walls.add((WallShape)shape);
				}
			}
		}
	}

	@Override
	protected void printShapes(IPrintableMaze maze, MazeRealization real) throws IOException {
 
		maze3dMapper = new Egg3dMapper(egg, (EggMaze)maze);
		
		scad.beginUnion();
		printFloors();
		printWalls(real);
		scad.closeUnion();
	}
	
	private void printFloors() throws IOException {
		scad.beginUnion();
		for (FloorShape hs: floor) {
				log.log(Level.INFO,hs.toString());
				log.log(Level.INFO, "room");
				printFloorWithHoleOneRoom(hs.getY(), hs.getX());
				log.log(Level.INFO, "hole");
			fillHoleInTheFloorOneRoom(hs);	
			log.log(Level.INFO, "end");
			
			
		}
		scad.closeUnion();	
	}

	private void printWalls(MazeRealization real) throws IOException {
		
		final double wt = sizes.getInnerWallToCellRatio()/2;
		
		for (WallShape wall: walls) {
			if (!wall.isOpen(real)) {
				log.log(Level.INFO, "wall "+wall+" is closed");
				printWallElements(wt, wall);
				
			}
		}
	}

	
	
	private ArrayList<WallShape> walls;
	private ArrayList<FloorShape> floor;





}
