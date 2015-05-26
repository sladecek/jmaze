package com.github.sladecek.maze.jmaze.moebius;

import java.io.IOException;
import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.EastWest;
import com.github.sladecek.maze.jmaze.I3DShapeConsumer;
import com.github.sladecek.maze.jmaze.IMazePrinter;
import com.github.sladecek.maze.jmaze.IMazeShape;
import com.github.sladecek.maze.jmaze.IPrintableMaze;
import com.github.sladecek.maze.jmaze.Point;
import com.github.sladecek.maze.jmaze.SouthNorth;
import com.github.sladecek.maze.jmaze.UpDown;
import com.github.sladecek.maze.jmaze.print.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print.OpenScadWriter;
import com.github.sladecek.maze.jmaze.shapes.HoleShape;
import com.github.sladecek.maze.jmaze.shapes.LineShape;

/**
 *  Print rooms of 3D Moebius maze as OpenScad file.
 *
 */
public class MoebiusOpenScadPrinter implements IMazePrinter, I3DShapeConsumer  {
	public MoebiusOpenScadPrinter(Maze3DSizes sizes) {
		super();
		this.sizes = sizes;
	}

	@Override
	public void printMaze(IPrintableMaze maze, String fileName) {
		this.maze = maze;
		
		walls = new ArrayList<LineShape>();
		nonHoles = new ArrayList<HoleShape>();
		for(IMazeShape shape: maze.getShapes()) {
			shape.produce3DShapes(this);
		}
		
		try (OpenScadWriter s = new OpenScadWriter(fileName)) {
			scad = s;
			cellHeight = maze.getPictureHeight();
			cellWidth = maze.getPictureWidth();
			this.gridMapper = new MoebiusGridMapper(sizes, cellHeight, cellWidth);
			deformator = new MoebiusDeformator(gridMapper.getLength_mm());
	
			scad.beginUnion();
			printBaseWithoutHole();
			printNonHoles();
			printOuterWalls();
			printInnerWalls();
			scad.closeUnion();
	
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private Point getHolePoint(int y, int x, EastWest ew, SouthNorth sn, UpDown ud) {
		final double c = 0.5;
		final double wt = (1-sizes.getInnerWallToCellRatio())/2;
		final double z = sizes.getBaseThickness_mm();

		return gridMapper.getBasePointWithOffsetAndZ(y, x, ud, 
				c + (sn == SouthNorth.south ? -wt: wt),
				c + (ew == EastWest.east ? -wt: wt));
	
	}
	
	private void printNonHoles() throws IOException {
		for (HoleShape hs: nonHoles) {
			ArrayList<Point> p = new ArrayList<Point>();
			for(EastWest ew: EastWest.values()) {
				for(SouthNorth sn: SouthNorth.values()) {
					for(UpDown ud: UpDown.values()) {												
						p.add(getHolePoint(hs.getY(), hs.getX(), ew, sn, ud));
					}
				}
			}
			printPolyhedron(p, "hole", holeColor);
		}
	}


	private void printBaseWithoutHole() throws IOException {
		scad.beginUnion();
		for (int cellX = 0; cellX < cellWidth; cellX++) {
			for (int cellY = 0; cellY < cellHeight; cellY++) {
			
			/*	if (cellY > 1) continue;
				if (cellX > 1) continue;
*/
				ArrayList<Point> pe = new ArrayList<Point>();
				for(SouthNorth sn: SouthNorth.values()) {
					for(UpDown ud: UpDown.values()) {						
						Point p = gridMapper.getBasePoint(cellY, cellX, ud, sn, EastWest.east);
						pe.add(p);
					}
				}
				for(SouthNorth sn: SouthNorth.values()) {
					for(UpDown ud: UpDown.values()) {						
						Point p = getHolePoint(cellY, cellX, EastWest.east, sn, ud);
						pe.add(p);
					}
				}				
				printPolyhedron(pe, "base e "+cellX+" "+cellY, baseColor);

				ArrayList<Point> pw = new ArrayList<Point>();
				for(SouthNorth sn: SouthNorth.values()) {
					for(UpDown ud: UpDown.values()) {
						Point p = getHolePoint(cellY, cellX, EastWest.west, sn, ud);
						pw.add(p);
					}
				}
				for(SouthNorth sn: SouthNorth.values()) {
					for(UpDown ud: UpDown.values()) {
						Point p = gridMapper.getBasePoint(cellY, cellX, ud, sn, EastWest.west);
						pw.add(p);
					}
				}
				printPolyhedron(pw, "base w"+cellX+" "+cellY, baseColor);
				
				ArrayList<Point> pn = new ArrayList<Point>();
				pn.add(pw.get(0));
				pn.add(pw.get(1));
				pn.add(pw.get(4));
				pn.add(pw.get(5));				
				pn.add(pe.get(4));
				pn.add(pe.get(5));
				pn.add(pe.get(0));
				pn.add(pe.get(1));
				printPolyhedron(pn, "base n "+cellX+" "+cellY, baseColor);
				
				ArrayList<Point> ps = new ArrayList<Point>();
				ps.add(pw.get(6));
				ps.add(pw.get(7));				
				ps.add(pw.get(2));
				ps.add(pw.get(3));
				ps.add(pe.get(2));
				ps.add(pe.get(3));
				ps.add(pe.get(6));
				ps.add(pe.get(7));
				printPolyhedron(ps, "base s "+cellX+" "+cellY, baseColor);
					
			}
		}
		scad.closeUnion();	
	}

	
	private void printInnerWalls() throws IOException {
		
		final double wt = sizes.getInnerWallToCellRatio()/2;
		
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




	private static final String baseColor = "[0.7,0.7,0.7]";
	private static final String outerWallColor = "[0.7,0.7,0.7]";
	private static final String innerWallColor = "[0.5,0.5,0]";
	private static final String cornerColor = "[0.8,0.8,0]";
	private static final String holeColor = "[1,1,1]";

	
	private ArrayList<LineShape> walls;
	private ArrayList<HoleShape> nonHoles;

	IPrintableMaze maze;
	MoebiusGridMapper gridMapper;
	MoebiusDeformator deformator;
	Maze3DSizes sizes;
	OpenScadWriter scad;
	int cellHeight;
	int cellWidth;
	@Override
	public void consumeHole(HoleShape hs) {
		if (!hs.isHole()) {
			nonHoles.add(hs);
		}		
	}

	@Override
	public void consumeWall(LineShape ls) {
		walls.add(ls);
		
	}
	
	
}
