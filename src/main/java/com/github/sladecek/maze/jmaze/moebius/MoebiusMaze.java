package com.github.sladecek.maze.jmaze.moebius;

import java.security.InvalidParameterException;
import java.util.Vector;

import com.github.sladecek.maze.jmaze.generator.IMazeSpace;
import com.github.sladecek.maze.jmaze.print.IPrintableMaze;
import com.github.sladecek.maze.jmaze.rectangular.RectangularMazeBase;
import com.github.sladecek.maze.jmaze.shapes.HoleShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.LineShape;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;

/**
 * 2D rectangular maze on Moebius strip. Rooms and walls (including holes) are numbered first by rows, then by columns. East/west walls are numbered 
 * before south/north ones. Holes are numbered after south/north walls. 
 */

public class MoebiusMaze extends RectangularMazeBase implements IMazeSpace,
		IPrintableMaze {
	
	private int eastWestWallCount;
	private int southNorthWallCount;
	private int holeCount;

	public MoebiusMaze(int height, int width) {
		super(height, width);
		if (width %2 != 0) {
			throw new InvalidParameterException("Moebius maze must have even width");
		}
		if (height %2 != 0) {
			throw new InvalidParameterException("Moebius maze must have even height");
		}
		eastWestWallCount = width * height;
		southNorthWallCount = width * (height-1);
		holeCount = width * height / 2;
		allocateWalls(eastWestWallCount + southNorthWallCount + holeCount);		
	}

	@Override
	public Iterable<IMazeShape> getShapes() {
		Vector<IMazeShape> result = new Vector<IMazeShape>();
		
		// outer walls
		final IMazeShape.ShapeType ow = IMazeShape.ShapeType.outerWall;
		result.add(new LineShape(ow, 0, 0, 0, width));
		result.add(new LineShape(ow, height, 0, height, width));
		final IMazeShape.ShapeType aw = IMazeShape.ShapeType.auxiliaryWall;
		result.add(new LineShape(aw, 0, 0, height, 0));
		result.add(new LineShape(aw, 0, width, height, width));
		
		final IMazeShape.ShapeType iw = IMazeShape.ShapeType.innerWall;

		
		result.add(new MarkShape(IMazeShape.ShapeType.startRoom, getStartRoom()/width, getStartRoom()%width));
		result.add(new MarkShape(IMazeShape.ShapeType.targetRoom, getTargetRoom()/width, getTargetRoom()%width));
		
		// inner walls - east/west
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int wall = x + y * width;
				if (isWallClosed(wall))
				{
					result.add(new LineShape(iw, y, x+1, y+1,  x+1));
					if (x == width-1)
					{
						// repeat wrapped east border
						result.add(new LineShape(iw, y, 0, y+1,  0));
					}
				}
			}
		}
		
		// inner walls - south/north
		for (int y = 0; y < height-1; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int wall = x + y * width + eastWestWallCount;
				if (isWallClosed(wall))
				{
					result.add(new LineShape(iw, y+1, x, y+1,  x+1));
				}
			}
		}
		
		// holes and their dual (non holes) - up down
		// the user will use either holes or non-holes
		for (int y = 0; y < height/2; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int wall = x + y * width + eastWestWallCount + southNorthWallCount;
				final boolean isHole = !isWallClosed(wall);
				result.add(new HoleShape(y, x, isHole));
				int hy = getTheOtherSideOfHoleY(y,x);
				int hx = getTheOtherSideOfHoleX(y,x);
				result.add(new HoleShape(hy, hx, isHole));
			}
		}

	
		// solution
		final IMazeShape.ShapeType is = IMazeShape.ShapeType.solution;
		for (int i = 0; i < solution.size()-1; i++) {
			int room1 = solution.get(i);
			int room2 = solution.get(i+1);
			int y1 = room1/width;
			int x1 = room1%width;
			int y2 = room2/width;
			int x2 = room2%width;
			
			// detect wrap around vertical border
			boolean wrapAround = false;
			if (y1 == y2) {				
				if (x1 == 0 && x2 == width-1) {
					wrapAround = true;
				} else if (x2 == 0 && x1 == width-1) {
					wrapAround = true;
					// swap
					x1 = 0;
					x2 = width-1;
				}							
			}
			
			if (wrapAround) {
				result.add(new LineShape(is, y1, -1, y2, 0));
				result.add(new LineShape(is, y1, width-1, y2, width));
			} else {
				result.add(new LineShape(is, y1, x1, y2, x2));
			}
			
		}
		return result;	}


	@Override
	public Iterable<Integer> getWalls(int room) {

		int y = room / width;
		int x = room % width;
		
		Vector<Integer> result = new Vector<Integer>();

		// east
		result.add(room);

		// west
		result.add(y * width + (x+width-1) % width) ;
	
		// south
		if (y < height - 1) {
			result.add(eastWestWallCount+y*width+x);
		}
		// north
		if (y > 0) {
			result.add(eastWestWallCount+(y-1)*width+x);
		}
		
		// hole
		int h = room;
		if (y >= height/2) 
		{
			h = getTheOtherSideOfHole(room);
		}
		result.add(eastWestWallCount + southNorthWallCount+h);
		
		return result;	
	}

	protected int getTheOtherSideOfHole(int room) {
		int y = room / width;
		int x = room % width;
		int hy = getTheOtherSideOfHoleY(y,x);
		int hx = getTheOtherSideOfHoleX(y,x);
		return hy*width+hx;
	}

	protected int getTheOtherSideOfHoleY(int y, int x) {
		return height - 1 - y;
	}
	
	protected int getTheOtherSideOfHoleX(int y, int x) {
		return (x + width/2) % width;
	}
	
	@Override
	public int getTargetRoom() {
		return width / 4 + (height-1) * width;		
	}

	@Override
	public double getRoomDistance(int r1, int r2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOtherRoom(int room, int wall) {
		if (wall < eastWestWallCount)
		{
			final int y = wall / width;
			final int x = wall % width;
			final int westRoom = wall;
			final int eastRoom = y*width+(x+1)%width;
			if (!(room == westRoom || room == eastRoom))
			{
				throw new InvalidParameterException("Wall is not adjacent to room");
			}
			return room == westRoom ? eastRoom : westRoom;
		}
		else if (wall < eastWestWallCount+southNorthWallCount)
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
		else
		{
			// hole
			return getTheOtherSideOfHole(room);
			
		}
		
	
	}

	@Override
	public int getWallProbabilityWeight(int wall) {
		if (wall < eastWestWallCount)
		{
			// horizontal
			return 30;
		}
		else if (wall < eastWestWallCount+southNorthWallCount)
		{
			// vertical
			return 3;
		}
		else
		{
			// hole
			return 1;
			
		}
	}

}
