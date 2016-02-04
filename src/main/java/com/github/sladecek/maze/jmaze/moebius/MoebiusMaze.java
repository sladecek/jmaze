package com.github.sladecek.maze.jmaze.moebius;

import java.security.InvalidParameterException;
import java.util.Vector;

import com.github.sladecek.maze.jmaze.generator.IMazeTopology;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IShapeMaker;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * 2D rectangular maze on Moebius strip. Rooms and walls (including holes) are
 * numbered first by rows, then by columns. East/west walls are numbered before
 * south/north ones. Holes are numbered after south/north walls.
 */
public final class MoebiusMaze implements IMazeTopology, IShapeMaker {

	public MoebiusMaze(final int height, final int width) {
		this.width = width;
		this.height = height;

		if (width % 2 != 0) {
			throw new InvalidParameterException(
					"Moebius maze must have even width");
		}
		if (height % 2 != 0) {
			throw new InvalidParameterException(
					"Moebius maze must have even height");
		}
		eastWestWallCount = width * height;
		southNorthWallCount = width * (height - 1);
		holeCount = width * height / 2;
	}

	@Override
	public ShapeContainer makeShapes(MazeRealization realization) {
        final boolean isPolar = false;
        this.context = new ShapeContext(isPolar, height, width, 1);
	       
		ShapeContainer result = new ShapeContainer(context);

		// outer walls
		final IMazeShape.ShapeType ow = IMazeShape.ShapeType.outerWall;
		result.add(new WallShape(ow, 0, 0, 0, width));
		result.add(new WallShape(ow, height, 0, height, width));
		final IMazeShape.ShapeType aw = IMazeShape.ShapeType.auxiliaryWall;
		result.add(new WallShape(aw, 0, 0, height, 0));
		result.add(new WallShape(aw, 0, width, height, width));

		final IMazeShape.ShapeType iw = IMazeShape.ShapeType.innerWall;

		// start stop rooms
		final int start = getStartRoom();
		final int target = getTargetRoom();		
		result.add(new MarkShape(IMazeShape.ShapeType.startRoom, start / width, start % width));		
		result.add(new MarkShape(IMazeShape.ShapeType.targetRoom, target / width, target % width));
		
		// inner walls - east/west
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int wall = x + y * width;
				if (realization.isWallClosed(wall)) {
					WallShape ws1 = new WallShape(iw, y, x + 1, y + 1, x + 1);
					result.add(ws1);
					if (x == width - 1) {
						// repeat wrapped east border
						WallShape ws2 = new WallShape(iw, y, 0, y + 1, 0);
						result.add(ws2);
					}
				}
			}
		}

		// inner walls - south/north
		for (int y = 0; y < height - 1; y++) {
			for (int x = 0; x < width; x++) {
				int wall = x + y * width + eastWestWallCount;
				if (realization.isWallClosed(wall)) {
					WallShape ws = new WallShape(iw, y + 1, x, y + 1, x + 1);
					result.add(ws);
				}
			}
		}

		// holes and their dual (non holes) - up down
		// the user will use either holes or non-holes
		for (int y = 0; y < height / 2; y++) {
			for (int x = 0; x < width; x++) {
				int wall = x + y * width + eastWestWallCount
						+ southNorthWallCount;
				if (realization.isWallClosed(wall)) {
					String floorId = "f" + Integer.toString(wall);
					final boolean isHole = true; // TODO !isWallClosed(wall);
					FloorShape fs1 = new FloorShape(y, x, isHole, floorId);
					result.add(fs1);
					int hy = getTheOtherSideOfHoleY(y, x);
					int hx = getTheOtherSideOfHoleX(y, x);
					FloorShape fs2 = new FloorShape(hy, hx, isHole, floorId);
					result.add(fs2);
				}
			}
		}

		// solution
		for (int i: realization.getSolution()) {
			result.add(new MarkShape(IMazeShape.ShapeType.solution, i / width, i % width));
		}

		return result;
	}

	@Override
	public Iterable<Integer> getWalls(int room) {

		int y = room / width;
		int x = room % width;

		Vector<Integer> result = new Vector<Integer>();

		// east
		result.add(room);

		// west
		result.add(y * width + (x + width - 1) % width);

		// south
		if (y < height - 1) {
			result.add(eastWestWallCount + y * width + x);
		}
		// north
		if (y > 0) {
			result.add(eastWestWallCount + (y - 1) * width + x);
		}

		// hole
		int h = room;
		if (y >= height / 2) {
			h = getTheOtherSideOfHole(room);
		}
		result.add(eastWestWallCount + southNorthWallCount + h);

		return result;
	}

	protected int getTheOtherSideOfHole(int room) {
		int y = room / width;
		int x = room % width;
		int hy = getTheOtherSideOfHoleY(y, x);
		int hx = getTheOtherSideOfHoleX(y, x);
		return hy * width + hx;
	}

	protected int getTheOtherSideOfHoleY(int y, int x) {
		return height - 1 - y;
	}

	protected int getTheOtherSideOfHoleX(int y, int x) {
		return (x + width / 2) % width;
	}

	@Override
	public int getTargetRoom() {
		return width / 4 + (height - 1) * width;
	}

	@Override
	public double getRoomDistance(int r1, int r2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRoomBehindWall(int room, int wall) {
		if (wall < eastWestWallCount) {
			final int y = wall / width;
			final int x = wall % width;
			final int westRoom = wall;
			final int eastRoom = y * width + (x + 1) % width;
			if (!(room == westRoom || room == eastRoom)) {
				throw new InvalidParameterException(
						"Wall is not adjacent to room");
			}
			return room == westRoom ? eastRoom : westRoom;
		} else if (wall < eastWestWallCount + southNorthWallCount) {
			final int y = (wall - eastWestWallCount) / width;
			final int x = (wall - eastWestWallCount) % width;
			final int northRoom = y * width + x;
			final int southRoom = northRoom + width;
			if (!(room == northRoom || room == southRoom)) {
				throw new InvalidParameterException(
						"Wall is not adjacent to room");
			}
			return room == northRoom ? southRoom : northRoom;
		} else {
			// hole
			return getTheOtherSideOfHole(room);
		}
	}

	@Override
	public int getWallProbabilityWeight(int wall) {
		if (wall < eastWestWallCount) {
			// horizontal
			return 30;
		} else if (wall < eastWestWallCount + southNorthWallCount) {
			// vertical
			return 3;
		} else {
			// hole
			return 1;

		}
	}

	@Override
	public int getWallCount() {
		return eastWestWallCount + southNorthWallCount + holeCount;
	}

	public int getRoomCount() {
		return width * height;
	}

	public int getStartRoom() {
		return 0;
	}


	private int height;
	private int width;
	private ShapeContext context;

	private int eastWestWallCount;
	private int southNorthWallCount;
	private int holeCount;

}
