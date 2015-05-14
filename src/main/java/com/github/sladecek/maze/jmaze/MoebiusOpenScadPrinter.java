package com.github.sladecek.maze.jmaze;

import java.io.IOException;
import java.util.ArrayList;

public class MoebiusOpenScadPrinter implements IMazePrinter, I3DShapeConsumer  {

	private ArrayList<LineShape> walls;
	private ArrayList<HoleShape> holes;
	public MoebiusOpenScadPrinter(Maze3DSizes sizes) {
		super();
		this.sizes = sizes;
	}

	@Override
	public void printMaze(IPrintableMaze maze, String fileName) {
		this.maze = maze;
		
		walls = new ArrayList<LineShape>();
		holes = new ArrayList<HoleShape>();
		for(IMazeShape shape: maze.getShapes()) {
			shape.produce3DShapes(this);
		}
		
		try (OpenScadWriter s = new OpenScadWriter(fileName)) {
			scad = s;
			cellHeight = maze.getPictureHeight();
			cellWidth = maze.getPictureWidth();
			this.gridMapper = new MoebiusGridMapper(sizes, cellHeight, cellWidth);
			deformator = new MoebiusDeformator(gridMapper.getLength_mm(), gridMapper.getHeight_mm());
			scad.beginDifference();
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
	
	private void printHoles() throws IOException {
		final double wt = 0.3;
		final double z = sizes.getBaseThickness_mm();
		for (HoleShape hs: holes) {
			ArrayList<Point> p = new ArrayList<Point>();
						
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), -wt, -wt, 0));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), -wt, -wt, z));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), wt, -wt, 0));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), wt, -wt, z));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), -wt, wt, 0));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), -wt, wt, z));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), wt, wt, 0));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), wt, wt, z));
			printPolyhedron(p);
		}
	}

	private void printInnerWalls() throws IOException {
		final double wt = sizes.innerWallToCellRatio/2;
		final double z = sizes.getWallHeight_mm();
		for (LineShape wall: walls) {
			ArrayList<Point> p = new ArrayList<Point>();
						
				p.add(gridMapper.getBasePointWithOffset(wall.getY1(), wall.getX1(), -wt, -wt, 0));
				p.add(gridMapper.getBasePointWithOffset(wall.getY1(), wall.getX1(), -wt, -wt, z));
				p.add(gridMapper.getBasePointWithOffset(wall.getY2(), wall.getX1(),  wt, -wt, 0));
				p.add(gridMapper.getBasePointWithOffset(wall.getY2(), wall.getX1(),  wt, -wt, z));
				p.add(gridMapper.getBasePointWithOffset(wall.getY1(), wall.getX2(), -wt, wt, 0));
				p.add(gridMapper.getBasePointWithOffset(wall.getY1(), wall.getX2(), -wt, wt, z));
				p.add(gridMapper.getBasePointWithOffset(wall.getY2(), wall.getX2(),  wt, wt, 0));
				p.add(gridMapper.getBasePointWithOffset(wall.getY2(), wall.getX2(),  wt, wt, z));
			printPolyhedron(p);
		}
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
	@Override
	public void consumeHole(HoleShape hs) {
		holes.add(hs);
		
	}

	@Override
	public void consumeWall(LineShape ls) {
		walls.add(ls);
		
	}
	
	
}
