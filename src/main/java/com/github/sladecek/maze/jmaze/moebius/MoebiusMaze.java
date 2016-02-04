package com.github.sladecek.maze.jmaze.moebius;

import java.security.InvalidParameterException;

import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.maze.IMazeTopology;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * 2D rectangular maze on Moebius strip. Rooms and walls (including holes) are
 * numbered first by rows, then by columns. East/west walls are numbered before
 * south/north ones. Holes are numbered after south/north walls.
 */
public final class MoebiusMaze extends Maze implements IMazeTopology {

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

		buildMaze();
	}

	
	public void buildMaze() {
        final boolean isPolar = false;
        setContext(new ShapeContext(isPolar, height, width, 1, 0, 0));
	       
		ShapeContainer result = new ShapeContainer(context);

		setStartRoom(0);
		setTargetRoom(width / 4 + (height - 1) * width);
		
		// outer walls
		final IMazeShape.ShapeType ow = IMazeShape.ShapeType.outerWall;
		result.add(new WallShape(ow, 0, 0, 0, width));
		result.add(new WallShape(ow, height, 0, height, width));
		final IMazeShape.ShapeType aw = IMazeShape.ShapeType.auxiliaryWall;
		result.add(new WallShape(aw, 0, 0, height, 0));
		result.add(new WallShape(aw, 0, width, height, width));

		final IMazeShape.ShapeType iw = IMazeShape.ShapeType.innerWall;


        // holes and their dual (non holes) - up down
        // the user will use either holes or non-holes
        for (int y = 0; y < height / 2; y++) {
            for (int x = 0; x < width; x++) {
                int wall1 = mapXYToRoomId(y, x);
  
                int id1 = addRoom();
                assert id1 == wall1 : "Inconsistent room numbering";
                

                String floorId1 = "f" + Integer.toString(wall1);
                final boolean isHole = true; 
                FloorShape fs1 = new FloorShape(y, x, isHole, floorId1);

                linkRoomToFloor(id1, fs1);
                addShape(fs1);
                    int hy = getTheOtherSideOfHoleY(y, x);
                    int hx = getTheOtherSideOfHoleX(y, x);
                    int wall2 = mapXYToRoomId(hy, hx);

                    int id2 = addRoom();
                    assert id2 == wall2 : "Inconsistent room numbering";
                
                    String floorId2 = "f" + Integer.toString(wall2);
                    FloorShape fs2 = new FloorShape(hy, hx, isHole, floorId2);
                    result.add(fs2);
                    linkRoomToFloor(id2, fs1);

                    int idWall = addWall(id1, id2);
                    
                    
                    linkShapeToId(fs1, idWall);
                    
                }
            }
        
		
		// inner walls - east/west
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				
				
		          int roomEast = mapXYToRoomId(y, x);
	                int roomWest = mapXYToRoomId(y, x+1);
	                int id = addWall(roomEast, roomWest);
	                
	                WallShape w = new WallShape(iw, y, x + 1, y + 1, x + 1);
	                addShape(w);
	                linkShapeToId(w, id);
	                
					if (x == width - 1) {
						// repeat wrapped east border
						WallShape ws2 = new WallShape(iw, y, 0, y + 1, 0);
						result.add(ws2);
					}
				}
			}

		// inner walls - south/north
		for (int y = 0; y < height - 1; y++) {
			for (int x = 0; x < width; x++) {
                int roomNorth = mapXYToRoomId(y, x);
                int roomSouth = mapXYToRoomId(y+1, x);
                int id = addWall(roomNorth, roomSouth);
                WallShape w = new WallShape(iw, y + 1, x, y + 1, x + 1);
                addShape(w);
                linkShapeToId(w, id);

                
			}
		}
	}


    private int mapXYToRoomId(int hy, int hx) {
        return hx + hy * width + eastWestWallCount
                + southNorthWallCount;
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


	private int height;
	private int width;
	private ShapeContext context;

	private int eastWestWallCount;
	private int southNorthWallCount;

}
