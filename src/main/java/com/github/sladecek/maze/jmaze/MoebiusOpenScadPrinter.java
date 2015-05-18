package com.github.sladecek.maze.jmaze;

import java.io.IOException;
import java.util.ArrayList;

public class MoebiusOpenScadPrinter implements IMazePrinter, I3DShapeConsumer  {

	/*
	private static final String baseColor = "[0,1,1]";
	private static final String outerWallColor = "[0,0,1]";
	private static final String innerWallColor = "[0.5,0.5,0]";
	private static final String cornerColor = "[0.8,0.8,0]";
	private static final String holeColor = "[1,1,1]";
	*/

	private static final String baseColor = "[0.7,0.7,0.7]";
	private static final String outerWallColor = "[0.7,0.7,0.7]";
	private static final String innerWallColor = "[0.5,0.5,0]";
	private static final String cornerColor = "[0.8,0.8,0]";
	private static final String holeColor = "[1,1,1]";

	
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
		final double c = 0.5;
		final double wt = (1-sizes.innerWallToCellRatio)/2;
		final double z = sizes.getBaseThickness_mm();
		for (HoleShape hs: holes) {
			ArrayList<Point> p = new ArrayList<Point>();
						
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), c-wt, c-wt, -z));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), c-wt, c-wt, 2*z));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), c+wt, c-wt, -z));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), c+wt, c-wt, 2*z));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), c-wt, c+wt, -z));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), c-wt, c+wt, 2*z));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), c+wt, c+wt, -z));
				p.add(gridMapper.getBasePointWithOffset(hs.getY(), hs.getX(), c+wt, c+wt, 2*z));
			printPolyhedron(p, "hole", holeColor);
		}
	}

	private void printInnerWalls() throws IOException {
		
		final double wt = sizes.innerWallToCellRatio/2;
		
		for (LineShape wall: walls) {
			int y1 = wall.getY1();
			int y2 = wall.getY2();
			int x1 = wall.getX1();
			int x2 = wall.getX2();
/*
			if (y1 > 2) continue;
			if (x2 > 2) continue;
			if (y1 > 2) continue;
			if (y2 > 2) continue;
	*/					
			// There is an overlap in the corner between walls. Overlaps are not nice, they make
			// ramparts. Therefore the wall must be rendered from three parts - the corners must be rendered separately.
			printInnerWallElement(y1, y1, x1, x1, wt, wt, "inner wall corner " + wall.toString(), innerWallColor);
			printInnerWallElement(y2, y2, x2, x2, wt, wt, "inner wall corner " + wall.toString(), innerWallColor);
			if (x1 == x2) {
				printInnerWallElement(y1, y2, x1, x2, -wt, wt, "inner wall along y " + wall.toString(), cornerColor);
			} else {
				printInnerWallElement(y1, y2, x1, x2, wt, -wt, "inner wall along x " + wall.toString(), cornerColor);
			}
			
		}
	}

	private void printInnerWallElement(int y1, int y2, int x1, int x2, double wy, double wx, 
			String comment, String color) throws IOException {
		
		
		final double z = sizes.getWallHeight_mm();
		ArrayList<Point> p = new ArrayList<Point>();
		p.add(gridMapper.getBasePointWithOffset(y1, x1, -wy, -wx, 0));
		p.add(gridMapper.getBasePointWithOffset(y1, x1, -wy, -wx, z));
		p.add(gridMapper.getBasePointWithOffset(y2, x1,  wy, -wx, 0));
		p.add(gridMapper.getBasePointWithOffset(y2, x1,  wy, -wx, z));
		p.add(gridMapper.getBasePointWithOffset(y1, x2, -wy, wx, 0));
		p.add(gridMapper.getBasePointWithOffset(y1, x2, -wy, wx, z));
		p.add(gridMapper.getBasePointWithOffset(y2, x2,  wy, wx, 0));
		p.add(gridMapper.getBasePointWithOffset(y2, x2,  wy, wx, z));
		printPolyhedron(p, comment, color);	
	}
		
	
	
	private void printPolyhedron(ArrayList<Point> gridPoints, String comment, String color) throws IOException {
		ArrayList<Point> deformed = new ArrayList<Point>();
		for(Point p: gridPoints) {
			deformed.add(deformator.transform(p));
		}
		scad.printPolyhedron(deformed, comment, color);
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
				printPolyhedron(p, "outer wall "+cellX,  outerWallColor);
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
				printPolyhedron(p, "base "+cellX+" "+cellY, baseColor);
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
