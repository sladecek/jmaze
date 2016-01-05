package com.github.sladecek.maze.jmaze.triangular;

import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.generator.GenericMazeSpace;
import com.github.sladecek.maze.jmaze.generator.IMazeSpace;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.GenericShapeMaker;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IShapeMaker;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.WallShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;

public class Triangular2DMaze extends GenericMazeSpace implements IMazeSpace, IShapeMaker {



	private int size;
	
	private GenericShapeMaker shapeMaker;
	
	public Triangular2DMaze(int size) {
		super();
		this.size = size;
		buildMaze();
	}

	@Override
	public ShapeContainer makeShapes(MazeRealization realization) {
		ShapeContainer result = shapeMaker.makeShapes(realization, getStartRoom(), getTargetRoom(), 0, 50); 
		result.setPictureHeight(size);
		result.setPictureWidth(2*size);
		
		// outer walls
		final IMazeShape.ShapeType ow = IMazeShape.ShapeType.outerWall;
		result.add(new WallShape(ow, 0, size, size, 0));
		result.add(new WallShape(ow, size, 0, size, 2*size));
		result.add(new WallShape(ow, size, 2*size, 0, size));

		
		return result;
	}

	
	private void buildMaze() {
		shapeMaker = new GenericShapeMaker();
		
		int prevFirst = -1;
		int lastRoom = -1;
		int myFirst = -1;
		
		// for all rows
		for (int y = 0; y < size; y++) {
			int prevRoom = -1;
			final int rooms = 2*y+1;
			
			// make a row of rooms  and vertical walls among them
			for (int j = 0; j < rooms; j++) {
				int r = addRoom();
				if (j == 0) {
					prevFirst = myFirst;
					myFirst = r;
				}
				lastRoom = r;
				LOGGER.info("addRoom "+r+" y="+y+" j="+j+" prevRoom="+prevRoom+" myFirst="+myFirst+" prevFirst="+prevFirst+" lastRoom="+lastRoom);
				int x1 = size + j - y - 1;
				int x2 = size + j - y;
				int y1 = y;
				int y2 = y;
				if ( j % 2 == 0) {
					y1++;
				} else {
					y2++;
				}
				
				String floorId = "r" + Integer.toString(r);
				final FloorShape floor = new FloorShape(y, x2, false, floorId);
				shapeMaker.linkRoomToFloor(r, floor);
				shapeMaker.addShape(floor);
				
				if (prevRoom > 0) {
					addWallAndShape(prevRoom, r, x1, x2, y1, y2);
				}
				prevRoom = r;
			}
			
			// connect rooms to upper row by horizontal walls
			if (prevFirst >= 0) {
				int i = 0;
				for (int j = 1; j < rooms; j+=2) {
					int x = size + j - y - 1;
					addWallAndShape(prevFirst+i, myFirst+j, x, x+2, y, y);
					i+=2;
				}
			}
			
		}		
		
		setStartRoom(0);
		setTargetRoom(lastRoom);
	}

	private void addWallAndShape(int prevRoom, int room, int x1, int x2,
			int y1, int y2) {
		int id = addWall(prevRoom, room);
		WallShape ws = new WallShape(ShapeType.innerWall, y1, x1, y2, x2);
		LOGGER.info("addWallAndShape room1="+prevRoom+" room2="+room+" y1="+y1+" y2="+y2+" x1="+x1+" x2="+x2);
		shapeMaker.addShape(ws);
		shapeMaker.linkShapeToId(ws, id);
	}

	public int getSize() {
		return size;
	}
	
	private static final Logger LOGGER =  Logger.getLogger("maze.jmaze");
}
