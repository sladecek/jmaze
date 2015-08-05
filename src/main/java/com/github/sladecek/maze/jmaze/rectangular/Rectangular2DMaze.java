package com.github.sladecek.maze.jmaze.rectangular;	

import java.security.InvalidParameterException;
import java.util.Vector;

import com.github.sladecek.maze.jmaze.generator.IMazeSpace;
import com.github.sladecek.maze.jmaze.print.IPrintableMaze;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * 2D rectangular maze. Both rooms and walls are numbered first by rows, then by columns. East/west walls are numbered 
 * before south/north ones.
 */
public class Rectangular2DMaze extends RectangularMazeBase implements IMazeSpace, IPrintableMaze {
	private int eastWestWallCount;
	private int southNorthWallCount;
	
	public Rectangular2DMaze(int height, int width) {	
		super(height, width);
		eastWestWallCount = (width - 1) * height;
		southNorthWallCount = width * (height-1);
	
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
	public Iterable<IMazeShape> getShapes() {
		Vector<IMazeShape> result = new Vector<IMazeShape>();
		
		// outer walls
		final IMazeShape.ShapeType ow = IMazeShape.ShapeType.outerWall;
		result.add(new WallShape(ow, 0, 0, 0, width));
		result.add(new WallShape(ow, 0, 0, height, 0));
		result.add(new WallShape(ow, 0, width, height, width));
		result.add(new WallShape(ow, height, 0, height, width));
		
		final IMazeShape.ShapeType iw = IMazeShape.ShapeType.innerWall;

		// inner walls - east/west
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width-1; x++)
			{
				int wallId = x + y * (width-1);
			
				WallShape w = new WallShape(iw, y, x+1, y+1,  x+1);
				w.setWallId(wallId);
				result.add(w);
			
			}
		}
		
		// inner walls - south/north
		for (int y = 0; y < height-1; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int wallId = x + y * width + eastWestWallCount;
				WallShape w = new WallShape(iw, y+1, x, y+1,  x+1);
				w.setWallId(wallId);
				result.add(w);
			}
		}
		/*
		// solution
		final IMazeShape.ShapeType is = IMazeShape.ShapeType.solution;
		for (int i = 0; i < solution.size()-1; i++) {
			int room1 = solution.get(i);
			int room2 = solution.get(i+1);
			int y1 = room1/width;
			int x1 = room1%width;
			int y2 = room2/width;
			int x2 = room2%width;
			result.add(new WallShape(is, y1, x1, y2, x2));
		}
		*/
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
			result.add(y * (width - 1) + x-1);
		}

		// south
		if (y < height - 1) {
			result.add(eastWestWallCount+y*width+x);
		}
		// north
		if (y > 0) {
			result.add(eastWestWallCount+(y-1)*width+x);
		}
		return result;
	}

	@Override
	public int getOtherRoom(int room, int wall) {
		if (wall < eastWestWallCount)
		{
			final int y = wall / (width-1);
			final int x = wall % (width-1);
			final int westRoom = y*width+x;
			final int eastRoom = westRoom+1;
			if (!(room == westRoom || room == eastRoom))
			{
				throw new InvalidParameterException("Wall is not adjacent to room");
			}
			return room == westRoom ? eastRoom : westRoom;
		}
		else
		{
			final int y = (wall-eastWestWallCount) / width;
			final int x = (wall-eastWestWallCount) % width;
			final int northRoom = y*width+x;
			final int southRoom = northRoom+width;
			if (!(room == northRoom || room == southRoom))
			{
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
	public int getWallCnt() {
		return eastWestWallCount + southNorthWallCount;
		
	}

}
