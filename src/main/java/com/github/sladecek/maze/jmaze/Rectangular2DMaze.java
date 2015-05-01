package com.github.sladecek.maze.jmaze;

import java.util.BitSet;
import java.util.Vector;

/**
 * 2D rectangular maze. Both rooms and walls are numbered first by rows, then by columns. East/west walls are numbered 
 * before south/north ones.
 */
public class Rectangular2DMaze implements IMazeable, IPrintableMaze {

	private int eastWestWallCount;
	private int southNorthWallCount;
	private int height;
	private BitSet isWallClosed;
	private int width;
	
	public Rectangular2DMaze(int height, int width) {
		this.width = width;
		this.height = height;
		eastWestWallCount = (width - 1) * height;
		southNorthWallCount = width * (height-1);
		isWallClosed = new BitSet(eastWestWallCount + southNorthWallCount);
		isWallClosed.set(0, isWallClosed.size(), true);
	}

	public int getRoomCount() {
		return width * height;
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
		result.add(new LineShape(ow, 0, 0, 0, width));
		result.add(new LineShape(ow, 0, 0, height, 0));
		result.add(new LineShape(ow, 0, width, height, width));
		result.add(new LineShape(ow, height, 0, height, width));
		
		final IMazeShape.ShapeType iw = IMazeShape.ShapeType.innerWall;

		// inner walls - east/west
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width-1; x++)
			{
				int wall = x + y * (width-1);
				if (isWallClosed(wall))
				{
					result.add(new LineShape(iw, y, x+1, y+1,  x+1));
				}
			}
		}
		
		// inner walls - south/north
		for (int y = 0; y < height-1; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int wall = x + y * (width-1) + eastWestWallCount;
				if (isWallClosed(wall))
				{
					result.add(new LineShape(iw, y+1, x, y+1,  x+1));
				}
			}
		}
		return result;
	}

	public int getStartRoom() {
		return 0;
	}

	public int getTargetRoom() {
		return getRoomCount() - 1;
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

	public boolean isWallClosed(int wall) {
		return isWallClosed.get(wall);
	}

	public void setWallClosed(int wall, boolean value) {
		isWallClosed.set(wall, value);
	}

	@Override
	public int getPictureHeight() {
		return height;
	}

	@Override
	public int getPictureWidth() {
		return width;
	}

}
