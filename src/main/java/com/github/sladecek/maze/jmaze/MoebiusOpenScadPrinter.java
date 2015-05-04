package com.github.sladecek.maze.jmaze;

import java.io.IOException;
import java.util.ArrayList;

public class MoebiusOpenScadPrinter implements IMazePrinter {

	public MoebiusOpenScadPrinter(Maze3DSizes sizes) {
		super();
		this.sizes = sizes;
	}

	@Override
	public void printMaze(IPrintableMaze maze, String fileName) {
		this.maze = maze;
		
		try (OpenScadWriter s = new OpenScadWriter(fileName)) {
			scad = s;
			cellHeight = maze.getPictureHeight();
			cellWidth = maze.getPictureWidth();
			this.gridMapper = new MoebiusGridMapper(sizes, cellHeight, cellWidth);
			deformator = new MoebiusDeformator(gridMapper.getLength_mm(), gridMapper.getHeight_mm());
			scad.beginDifference()
			scad.beginUnion();
			printBase();
			printOuterWalls();
			printInnerWalls();
	
			scad.closeUnion();
			scad.beginUnion();
			printHoles();
			scad.closeUnion();
			scad.closeDifference();
			
			

		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		
		
		
	}
	
	private void printInnerWalls() {
		for(IMazeShape shape: maze.getShapes()){
		printShape(shape);
	}
		
	}

	private void printShape(IMazeShape shape) {
		// TODO Auto-generated method stub
		
	}
	
	private void printPolyhedron(ArrayList<Point> gridPoints) throws IOException {
		ArrayList<Point> deformed = new ArrayList<Point>();
		for(Point p: gridPoints) {
			deformed.add(deformator.transform(p));
		}
		scad.printPolyhedron(deformed);
	}
	

	private void printOuterWalls() throws IOException {
		scad.beginUnion();
		for (int cellX = 0; cellX < cellWidth; cellX++) {
			for(SouthNorth snWall: SouthNorth.values()) {
				ArrayList<Point> p = new ArrayList<Point>();
				for(EastWest ew: EastWest.values()) {
					for(SouthNorth snEdge: SouthNorth.values()) {
						for(UpDown ud: UpDown.values()) {						
							p.add(gridMapper.getOuterPoint(cellX, ew, ud, snWall, snEdge));
						}
					}
				}
				printPolyhedron(p);
			}
		}
		scad.closeUnion();
	}

	private void printBase() throws IOException {
		scad.beginUnion();
		for (int cellX = 0; cellX < cellWidth; cellX++) {
			for (int cellY = 0; cellY < cellHeight; cellY++) {
				ArrayList<Point> p = new ArrayList<Point>();
				for(EastWest ew: EastWest.values()) {
					for(SouthNorth sn: SouthNorth.values()) {
						for(UpDown ud: UpDown.values()) {						
							p.add(gridMapper.getBasePoint(cellY, cellX, ud, sn, ew));
						}
					}
				}
				printPolyhedron(p);
			}
		}
		scad.closeUnion();	
	}

	IPrintableMaze maze;
	MoebiusGridMapper gridMapper;
	MoebiusDeformator deformator;
	Maze3DSizes sizes;
	OpenScadWriter scad;
	int cellHeight;
	int cellWidth;
	
	
}
