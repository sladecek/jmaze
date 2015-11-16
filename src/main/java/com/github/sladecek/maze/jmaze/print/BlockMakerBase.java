package com.github.sladecek.maze.jmaze.print;

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
 * Base class for 3D OpenScad Maze printers. Responsible for shared operations
 * such as wall and floor drawing for one room.
 */
public abstract class BlockMakerBase {

	public BlockMakerBase(Maze3DSizes sizes, MazeColors colors,
			MazeRealization realization, double approxRoomSizeInmm) {
		this.sizes = sizes;
		this.colors = colors;
		this.realization = realization;
		this.approxRoomSizeInmm = approxRoomSizeInmm;
	}

	protected final void printWallElements(final double wallThickness,
			WallShape wall) {

		LOG.log(Level.INFO, wall.toString());
		int y1 = wall.getY1();
		int y2 = wall.getY2();
		int x1 = wall.getX1();
		int x2 = wall.getX2();

		String id = wall.getId();
		// There is an overlap in the corner between walls. Overlaps are not
		// nice, they make
		// ramparts. Therefore the wall must be rendered from three parts - the
		// corners must be rendered separately.
		printInnerWallElement(y1, y1, x1, x1, wallThickness, wallThickness,
				null, "inner wall corner " + wall.toString(),
				colors.getInnerWallColor());
		printInnerWallElement(y2, y2, x2, x2, wallThickness, wallThickness,
				null, "inner wall corner " + wall.toString(),
				colors.getInnerWallColor());
		if (x1 == x2) {
			printInnerWallElement(y1, y2, x1, x2, -wallThickness,
					wallThickness, id, "inner wall along y " + wall.toString(),
					colors.getCornerColor());
		} else {
			printInnerWallElement(y1, y2, x1, x2, wallThickness,
					-wallThickness, id,
					"inner wall along x " + wall.toString(),
					colors.getCornerColor());
		}
	}

	private void printInnerWallElement(int y1, int y2, int x1, int x2,
			double wy, double wx, String id, String comment, Color color) {

		final double z = sizes.getWallHeightInmm();
		ArrayList<Point> p = new ArrayList<Point>();
		p.add(maze3dMapper.mapPoint(y1, x1, -wy, -wx, 0));
		p.add(maze3dMapper.mapPoint(y1, x1, -wy, -wx, z));
		p.add(maze3dMapper.mapPoint(y2, x1, wy, -wx, 0));
		p.add(maze3dMapper.mapPoint(y2, x1, wy, -wx, z));
		p.add(maze3dMapper.mapPoint(y1, x2, -wy, wx, 0));
		p.add(maze3dMapper.mapPoint(y1, x2, -wy, wx, z));
		p.add(maze3dMapper.mapPoint(y2, x2, wy, wx, 0));
		p.add(maze3dMapper.mapPoint(y2, x2, wy, wx, z));
		printPolyhedron(p, comment, color);

	}

	protected final void printFloorWithHoleOneRoom(int cellY, int cellX)  {
		ArrayList<Point> pe = makeFloorSegmentEast(cellY, cellX);				
		printPolyhedron(pe, "base e " + cellX + " " + cellY, colors.getBaseColor());

		ArrayList<Point> pw = makeFloorSegmentWest(cellY, cellX);
		printPolyhedron(pw, "base w" + cellX + " " + cellY, colors.getBaseColor());
		
		ArrayList<Point> pn = makeFloorSegmentNorth(pe, pw);
		printPolyhedron(pn, "base n " + cellX + " " + cellY, colors.getBaseColor());
		
		ArrayList<Point> ps = makeFloorSegmentSouth(pe, pw);
		printPolyhedron(ps, "base s " + cellX + " " + cellY, colors.getBaseColor());	
	}

	private ArrayList<Point> makeFloorSegmentWest(int cellY, int cellX) {
		ArrayList<Point> pw = new ArrayList<Point>();
		for (SouthNorth sn : SouthNorth.values()) {
			for (UpDown ud : UpDown.values()) {
				Point p = getHolePoint(cellY, cellX, EastWest.west, sn, ud);
				pw.add(p);
			}
		}
		for (SouthNorth sn : SouthNorth.values()) {
			for (UpDown ud : UpDown.values()) {
				Point p = getFloorPoint(cellY, cellX, ud, sn, EastWest.west);
				pw.add(p);
			}
		}
		return pw;
	}

	private ArrayList<Point> makeFloorSegmentEast(int cellY, int cellX) {
		ArrayList<Point> pe = new ArrayList<Point>();
		for (SouthNorth sn : SouthNorth.values()) {
			for (UpDown ud : UpDown.values()) {
				Point p = getFloorPoint(cellY, cellX, ud, sn, EastWest.east);
				pe.add(p);
			}
		}
		for (SouthNorth sn : SouthNorth.values()) {
			for (UpDown ud : UpDown.values()) {
				Point p = getHolePoint(cellY, cellX, EastWest.east, sn, ud);
				pe.add(p);
			}
		}
		return pe;
	}

	private ArrayList<Point> makeFloorSegmentSouth(ArrayList<Point> pe,
			ArrayList<Point> pw) {
		ArrayList<Point> ps = new ArrayList<Point>();
		ps.add(pw.get(6));
		ps.add(pw.get(7));
		ps.add(pw.get(2));
		ps.add(pw.get(3));
		ps.add(pe.get(2));
		ps.add(pe.get(3));
		ps.add(pe.get(6));
		ps.add(pe.get(7));
		return ps;
	}

	private ArrayList<Point> makeFloorSegmentNorth(ArrayList<Point> pe,
			ArrayList<Point> pw) {
		ArrayList<Point> pn = new ArrayList<Point>();
		pn.add(pw.get(0));
		pn.add(pw.get(1));
		pn.add(pw.get(4));
		pn.add(pw.get(5));
		pn.add(pe.get(4));
		pn.add(pe.get(5));
		pn.add(pe.get(0));
		pn.add(pe.get(1));
		return pn;
	}

	protected final void fillHoleInTheFloorOneRoom(FloorShape hs) {
		ArrayList<Point> p = new ArrayList<Point>();
		for (EastWest ew : EastWest.values()) {
			for (SouthNorth sn : SouthNorth.values()) {
				for (UpDown ud : UpDown.values()) {
					p.add(getHolePoint(hs.getY(), hs.getX(), ew, sn, ud));
				}
			}
		}
		printPolyhedron(p, "hole", colors.getHoleColor());

	}

	/*
	 * protected void printText(Point p, String text, Color color) {
	 * scad.printTranslate(p); final double size = 0.05; scad.printText(text,
	 * color, size); }
	 */
	protected final Point getHolePoint(int y, int x, EastWest ew,
			SouthNorth sn, UpDown ud) {

		final double wt = sizes.getInnerWallToCellRatio() / 2
				* approxRoomSizeInmm;		
		final int dY = (sn == SouthNorth.south) ? 0 : maze3dMapper.getStepY(y,
				x);
		final int dX = (ew == EastWest.east) ? 0 : 1;

		return mapPointWithZ(y + dY, x + dX, ud, 0, (ew == EastWest.east ? wt
				: -wt));

	}

	final Point mapPointWithZ(int cellY, int cellX, UpDown ud, double offsetY,
			double offsetX) {
		double z = ud == UpDown.down ? 0 : sizes.getBaseThicknessInmm();
		return maze3dMapper.mapPoint(cellY, cellX, offsetY, offsetX, z);
	}

	final Point getFloorPoint(int cellY, int cellX, UpDown ud, SouthNorth sn,
			EastWest ew) {
		final int dY = (sn == SouthNorth.south) ? 0 : maze3dMapper.getStepY(
				cellY, cellX);
		final int dX = (ew == EastWest.east) ? 0 : 1;
		return mapPointWithZ(cellY + dY, cellX + dX, ud, 0, 0);
	}

	private static final Logger LOG = Logger.getLogger("LOG");

	protected Maze3DSizes sizes;
	protected MazeColors colors;
	protected OpenScadComposer scad;
	protected IMaze3DMapper maze3dMapper;
	protected MazeRealization realization;
	private double approxRoomSizeInmm;

	protected ArrayList<Block> blocks;

	public final Iterable<Block> getBlocks() {
		return blocks;
	}

	public final void printPolyhedron(final ArrayList<Point> polyhedron,
			final String comment, final Color color) {
		LOG.log(Level.INFO, "printPolyhedron " + comment);
		Block b = new Block(polyhedron, comment, color);
		blocks.add(b);

	}

}