package com.github.sladecek.maze.jmaze.spheric;

import java.io.IOException;
import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.print.IMazePrinter;
import com.github.sladecek.maze.jmaze.print.IPrintableMaze;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print.OpenScadMazePrinter;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.LineShape;

public class EggOpenScadPrinter extends OpenScadMazePrinter implements IMazePrinter 	
{

	private EggGeometry egg;
	
	public EggOpenScadPrinter(Maze3DSizes sizes, EggGeometry egg) {
		super(sizes);
		this.egg = egg;
	}


	protected void collectMazeShapes(IPrintableMaze maze) {
		walls = new ArrayList<LineShape>();
		floor = new ArrayList<FloorShape>();
		for(IMazeShape shape: maze.getShapes()) {
			if (shape.getShapeType() == ShapeType.hole && !((FloorShape)shape).isHole()) {
				floor.add((FloorShape)shape);
			} else {
				if (shape.getShapeType() == ShapeType.innerWall) {
					walls.add((LineShape)shape);
				}
			}
		}
	}

	@Override
	protected void printShapes(IPrintableMaze maze) throws IOException {
 
		maze3dMapper = new Egg3dMapper(egg, (EggMaze)maze);
	
		scad.beginUnion();
		printFloors();
		printWalls();
		scad.closeUnion();
	}
	
	private void printFloors() throws IOException {
		scad.beginUnion();
		for (FloorShape hs: floor) {
			printFloorWithHoleOneRoom(hs.getY(), hs.getX());			
			fillHoleInTheFloorOneRoom(hs);
		}
		scad.closeUnion();	
	}

	
	
	private void printWalls() throws IOException {
		
		final double wt = sizes.getInnerWallToCellRatio()/2;
		
		for (LineShape wall: walls) {
			printWallsOneRoom(wt, wall);
			
		}
	}

	
	
	private ArrayList<LineShape> walls;
	private ArrayList<FloorShape> floor;




}
