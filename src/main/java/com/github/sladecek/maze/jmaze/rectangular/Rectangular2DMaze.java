package com.github.sladecek.maze.jmaze.rectangular;	

import java.security.InvalidParameterException;
import java.util.Vector;

import com.github.sladecek.maze.jmaze.generator.IMazeSpace;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IShapeMaker;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * 2D rectangular maze. Both rooms and walls are numbered first by rows, then by columns. East/west walls are numbered 
 * before south/north ones.
 */
public final class Rectangular2DMaze  implements IMazeSpace, IShapeMaker {
	public Rectangular2DMaze(int height, int width) {	
		this.width = width;
		this.height = height;

		eastWestWallCount = (width - 1) * height;
		southNorthWallCount = width * (height - 1);	
	}

	public double getRoomDistance(int r1, int r2) {
		int y1 = r1 / width;
		int x1 = r1 % width;
		int y2 = r2 / width;
		int x2 = r2 % width;
		// Manhattan (l1) distance
		return Math.abs(y1 - y2) + Math.abs(x1 - x2);
	}

	/**
	 * Get all geometric shapes needed to print this maze.
	 */
	public ShapeContainer makeShapes(MazeRealization realization) {
		ShapeContainer result = new ShapeContainer();
		result.setPictureHeight(height);
		result.setPictureWidth(width);	
		
		// outer walls
		final IMazeShape.ShapeType ow = IMazeShape.ShapeType.outerWall;
		result.add(new WallShape(ow, 0, 0, 0, width));
		result.add(new WallShape(ow, 0, 0, height, 0));
		result.add(new WallShape(ow, 0, width, height, width));
		result.add(new WallShape(ow, height, 0, height, width));
		
		final IMazeShape.ShapeType iw = IMazeShape.ShapeType.innerWall;

		// inner walls - east/west
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width - 1; x++) {
				int wallId = x + y * (width - 1);
			
				if (realization.isWallClosed(wallId)) {
					WallShape w = new WallShape(iw, y, x + 1, y + 1, x + 1);
// TODO smazat					w.setWallId(wallId);
					result.add(w);
				}			
			}
		}
		
		// inner walls - south/north
		for (int y = 0; y < height - 1; y++) {
			for (int x = 0; x < width; x++) {
				int wallId = x + y * width + eastWestWallCount;
				if (realization.isWallClosed(wallId)) {
				WallShape w = new WallShape(iw, y + 1, x, y + 1, x + 1);
// TODO smazat				w.setWallId(wallId);
				result.add(w);
				}
			}
		}
				
		// start stop rooms
		final int start = getStartRoom();
		final int target = getTargetRoom();
		
		result.add(new MarkShape(IMazeShape.ShapeType.startRoom, start / width, start % width, "start"));		
		result.add(new MarkShape(IMazeShape.ShapeType.targetRoom, target / width, target % width, "stop"));
		
		// solution
		for (int i: realization.getSolution()) {
			result.add(new MarkShape(IMazeShape.ShapeType.solution, i / width, i % width, "start"));
		}
		
		return result;
	}

	public Iterable<Integer> getWalls(int room) {

		int y = room / width;
		int x = room % width;

		Vector<Integer> result = new Vector<Integer>();
	
		// east
		if (x < width - 1) {
			result.add(y * (width - 1) + x);
		}
		// west
		if (x > 0) {
			result.add(y * (width - 1) + x - 1);
		}

		// south
		if (y < height - 1) {
			result.add(eastWestWallCount + y * width + x);
		}
		// north
		if (y > 0) {
			result.add(eastWestWallCount + (y - 1) * width + x);
		}
		return result;
	}

	@Override
	public int getRoomBehindWall(int room, int wall) {
		if (wall < eastWestWallCount) {		
			final int y = wall / (width - 1);
			final int x = wall % (width - 1);
			final int westRoom = y * width + x;
			final int eastRoom = westRoom + 1;
			if (!(room == westRoom || room == eastRoom)) {
				throw new InvalidParameterException("Wall is not adjacent to room");
			}
			return room == westRoom ? eastRoom : westRoom;
		} else {
			final int y = (wall - eastWestWallCount) / width;
			final int x = (wall - eastWestWallCount) % width;
			final int northRoom = y * width + x;
			final int southRoom = northRoom + width;
			if (!(room == northRoom || room == southRoom)) {
				throw new InvalidParameterException("Wall is not adjacent to room");
			}
			return room == northRoom ? southRoom : northRoom;
		}
		
	}

	@Override
	public int getWallProbabilityWeight(int wall) {
		return 1;
	}

	@Override
	public int getWallCount() {
		return eastWestWallCount + southNorthWallCount;
		
	}

	public int getRoomCount() {
		return width * height;
	}

	public int getStartRoom() {
		return 0;
	}

	public int getTargetRoom() {
		return getRoomCount() - 1;
	}

	private int height;
	private int width;

	private int eastWestWallCount;
	private int southNorthWallCount;

}
