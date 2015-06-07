package com.github.sladecek.maze.jmaze.print;

import java.io.IOException;
import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.geometry.Point;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.LineShape;

/**
 * Base class for 3D OpenScad Maze printers. Responsible for shared operations such as
 * wall and floor drawing for one room.
 */
public abstract class OpenScadMazePrinter {
	
	public OpenScadMazePrinter(Maze3DSizes sizes) {
		this.sizes = sizes;
	}
	

	public void printMaze(IPrintableMaze maze, String fileName) {	
		collectMazeShapes(maze);
		
		try (OpenScadWriter s = new OpenScadWriter(fileName)) {
			scad = s;
			printShapes(maze);
	
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	
	// virtual method
	protected abstract void collectMazeShapes(IPrintableMaze maze);

	// virtual method
	protected abstract void printShapes(IPrintableMaze maze) throws IOException;
		
	

	protected void printWallsOneRoom(final double wallThickness, LineShape wall)
			throws IOException {
		int y1 = wall.getY1();
		int y2 = wall.getY2();
		int x1 = wall.getX1();
		int x2 = wall.getX2();
			
		// There is an overlap in the corner between walls. Overlaps are not nice, they make
		// ramparts. Therefore the wall must be rendered from three parts - the corners must be rendered separately.
		printInnerWallElement(y1, y1, x1, x1, wallThickness, wallThickness, "inner wall corner " + wall.toString(), innerWallColor);
		printInnerWallElement(y2, y2, x2, x2, wallThickness, wallThickness, "inner wall corner " + wall.toString(), innerWallColor);
		if (x1 == x2) {
			printInnerWallElement(y1, y2, x1, x2, -wallThickness, wallThickness, "inner wall along y " + wall.toString(), cornerColor);
		} else {
			printInnerWallElement(y1, y2, x1, x2, wallThickness, -wallThickness, "inner wall along x " + wall.toString(), cornerColor);
		}
	}

	private void printInnerWallElement(int y1, int y2, int x1, int x2, double wy, double wx, 
			String comment, String color) throws IOException {
		
		
		final double z = sizes.getWallHeight_mm();
		ArrayList<Point> p = new ArrayList<Point>();
		p.add(maze3dMapper.mapPoint(y1, x1, -wy, -wx, 0));
		p.add(maze3dMapper.mapPoint(y1, x1, -wy, -wx, z));
		p.add(maze3dMapper.mapPoint(y2, x1,  wy, -wx, 0));
		p.add(maze3dMapper.mapPoint(y2, x1,  wy, -wx, z));
		p.add(maze3dMapper.mapPoint(y1, x2, -wy, wx, 0));
		p.add(maze3dMapper.mapPoint(y1, x2, -wy, wx, z));
		p.add(maze3dMapper.mapPoint(y2, x2,  wy, wx, 0));
		p.add(maze3dMapper.mapPoint(y2, x2,  wy, wx, z));
		scad.printPolyhedron(p, comment, color);	
	}
		

	protected void printFloorWithHoleOneRoom(int cellX, int cellY) throws IOException {
		ArrayList<Point> pe = new ArrayList<Point>();
		for(SouthNorth sn: SouthNorth.values()) {
			for(UpDown ud: UpDown.values()) {						
				Point p = getBasePoint(cellY, cellX, ud, sn, EastWest.east);
				pe.add(p);
			}
		}
		for(SouthNorth sn: SouthNorth.values()) {
			for(UpDown ud: UpDown.values()) {						
				Point p = getHolePoint(cellY, cellX, EastWest.east, sn, ud);
				pe.add(p);
			}
		}				
		scad.printPolyhedron(pe, "base e "+cellX+" "+cellY, baseColor);

		ArrayList<Point> pw = new ArrayList<Point>();
		for(SouthNorth sn: SouthNorth.values()) {
			for(UpDown ud: UpDown.values()) {
				Point p = getHolePoint(cellY, cellX, EastWest.west, sn, ud);
				pw.add(p);
			}
		}
		for(SouthNorth sn: SouthNorth.values()) {
			for(UpDown ud: UpDown.values()) {
				Point p = getBasePoint(cellY, cellX, ud, sn, EastWest.west);
				pw.add(p);
			}
		}
		scad.printPolyhedron(pw, "base w"+cellX+" "+cellY, baseColor);
		
		ArrayList<Point> pn = new ArrayList<Point>();
		pn.add(pw.get(0));
		pn.add(pw.get(1));
		pn.add(pw.get(4));
		pn.add(pw.get(5));				
		pn.add(pe.get(4));
		pn.add(pe.get(5));
		pn.add(pe.get(0));
		pn.add(pe.get(1));
		scad.printPolyhedron(pn, "base n "+cellX+" "+cellY, baseColor);
		
		ArrayList<Point> ps = new ArrayList<Point>();
		ps.add(pw.get(6));
		ps.add(pw.get(7));				
		ps.add(pw.get(2));
		ps.add(pw.get(3));
		ps.add(pe.get(2));
		ps.add(pe.get(3));
		ps.add(pe.get(6));
		ps.add(pe.get(7));
		scad.printPolyhedron(ps, "base s "+cellX+" "+cellY, baseColor);
	}

	protected void fillHoleInTheFloorOneRoom(FloorShape hs) throws IOException {
		ArrayList<Point> p = new ArrayList<Point>();
		for(EastWest ew: EastWest.values()) {
			for(SouthNorth sn: SouthNorth.values()) {
				for(UpDown ud: UpDown.values()) {												
					p.add(getHolePoint(hs.getY(), hs.getX(), ew, sn, ud));
				}
			}
		}
		scad.printPolyhedron(p, "hole", holeColor);
	}

	protected Point getHolePoint(int y, int x, EastWest ew, SouthNorth sn, UpDown ud) {
		final double c = 0.5;
		final double wt = (1-sizes.getInnerWallToCellRatio())/2;
		final double z = sizes.getBaseThickness_mm();

		return mapPointWithZ(y, x, ud, 
				c + (sn == SouthNorth.south ? -wt: wt),
				c + (ew == EastWest.east ? -wt: wt));
	
	}
	
	
	Point mapPointWithZ(int cellY, int cellX, UpDown ud, double offsetY, double offsetX) {
		double z = ud == UpDown.down ? 0 : sizes.getBaseThickness_mm();
		return maze3dMapper.mapPoint(cellY, cellX,  offsetY, offsetX, z);
	}
	
	
	Point getBasePoint(int cellY, int cellX, UpDown ud, SouthNorth sn, EastWest ew) {
		double offsetY = (sn == SouthNorth.south) ? 0 : 1;
		double offsetX = (ew == EastWest.east) ? 0 : 1;		
		return mapPointWithZ(cellY, cellX,  ud, offsetY, offsetX);
		
	}
	
	
	protected static final String baseColor = "[0.7,0.7,0.7]";
	protected static final String outerWallColor = "[0.7,0.7,0.7]";
	protected static final String innerWallColor = "[0.5,0.5,0]";
	protected static final String cornerColor = "[0.8,0.8,0]";
	protected static final String holeColor = "[1,1,1]";
	protected Maze3DSizes sizes;
	protected OpenScadWriter scad;
	protected IMaze3DMapper maze3dMapper;

}