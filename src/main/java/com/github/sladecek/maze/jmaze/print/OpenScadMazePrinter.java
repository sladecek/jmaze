package com.github.sladecek.maze.jmaze.print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.Point;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * Base class for 3D OpenScad Maze printers. Responsible for shared operations such as
 * wall and floor drawing for one room.
 */
public abstract class OpenScadMazePrinter {
	
	private double approxRoomSize_mm;
	
	private final static Logger log = Logger.getLogger("LOG");
	
	
	public OpenScadMazePrinter(Maze3DSizes sizes, double approxRoomSize_mm) {
		this.sizes = sizes;
		this.approxRoomSize_mm = approxRoomSize_mm;
	}
	

	public void printMaze(IPrintableMaze maze, MazeRealization real, String fileName) {	
		
		prepareShapes(maze);
		
		try (OpenScadWriter s = new OpenScadWriter(fileName)) {
			scad = s;
			printShapes(maze,real);
	
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	

	// virtual method
	protected abstract void prepareShapes(IPrintableMaze maze);

	// virtual method
	protected abstract void printShapes(IPrintableMaze maze, MazeRealization real) throws IOException;
		
	

	protected void printWallElements(final double wallThickness, WallShape wall)
			throws IOException {
		
		log.log(Level.INFO,wall.toString());
		int y1 = wall.getY1();
		int y2 = wall.getY2();
		int x1 = wall.getX1();
		int x2 = wall.getX2();
			
		String id = wall.getId();
		// There is an overlap in the corner between walls. Overlaps are not nice, they make
		// ramparts. Therefore the wall must be rendered from three parts - the corners must be rendered separately.
		printInnerWallElement(y1, y1, x1, x1, wallThickness, wallThickness, null, "inner wall corner " + wall.toString(), innerWallColor);
		printInnerWallElement(y2, y2, x2, x2, wallThickness, wallThickness, null, "inner wall corner " + wall.toString(), innerWallColor);
		if (x1 == x2) {
			printInnerWallElement(y1, y2, x1, x2, -wallThickness, wallThickness, id, "inner wall along y " + wall.toString(), cornerColor);
		} else {
			printInnerWallElement(y1, y2, x1, x2, wallThickness, -wallThickness, id, "inner wall along x " + wall.toString(), cornerColor);
		}
	}

	private void printInnerWallElement(int y1, int y2, int x1, int x2, double wy, double wx, 
			String id, String comment, String color) throws IOException {
		
		
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
		
		if (id != null) {
			Point mp = Point.midpoint(p.get(1), p.get(7));
			printText(mp, id, debugWallColor);
		}
	}
		

	protected void printFloorWithHoleOneRoom(int cellY, int cellX) throws IOException {
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
		Point mp = Point.midpoint(p.get(0), p.get(6));
		printText(mp, hs.getId(), debugFloorColor);

	}
	
	protected void printText(Point p,  String text, String color) throws IOException {
		scad.printTranslate(p);
		final double size = 0.05;
		scad.printText(text, color, size);
	}

	protected Point getHolePoint(int y, int x, EastWest ew, SouthNorth sn, UpDown ud) {

		final double wt = sizes.getInnerWallToCellRatio()/2*approxRoomSize_mm;
		final double z = sizes.getBaseThickness_mm();
		final int dY = (sn == SouthNorth.south) ? 0 : maze3dMapper.getStepY(y,x);
		final int dX = (ew == EastWest.east) ? 0 : 1;	
		
		return mapPointWithZ(y+dY, x+dX, ud, 
				(sn == SouthNorth.south ? wt: -wt),
				(ew == EastWest.east ? wt: -wt));
	
	}
	
	
	Point mapPointWithZ(int cellY, int cellX, UpDown ud, double offsetY, double offsetX) {
		double z = ud == UpDown.down ? 0 : sizes.getBaseThickness_mm();
		return maze3dMapper.mapPoint(cellY, cellX,  offsetY, offsetX, z);
	}
	
	
	Point getBasePoint(int cellY, int cellX,  UpDown ud, SouthNorth sn, EastWest ew) {
		final int dY = (sn == SouthNorth.south) ? 0 : maze3dMapper.getStepY(cellY,cellX);
		final int dX = (ew == EastWest.east) ? 0 : 1;	
		return mapPointWithZ(cellY+dY, cellX+dX,  ud, 0, 0);
		
	}
	
	
	
	protected static final String baseColor = "[0.7,0.7,0.7]";
	protected static final String outerWallColor = "[0.7,0.7,0.7]";
	protected static final String innerWallColor = "[0.5,0.5,0]";
	protected static final String cornerColor = "[0.8,0.8,0]";
	protected static final String holeColor = "[1,1,1]";
	protected static final String debugWallColor = "[1,0,0]";
	protected static final String debugFloorColor = "[0,1,0]";
	protected Maze3DSizes sizes;
	protected OpenScadWriter scad;
	protected IMaze3DMapper maze3dMapper;

}