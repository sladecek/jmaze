package com.github.sladecek.maze.jmaze.triangular;

import com.github.sladecek.maze.jmaze.generator.GenericMazeSpace;
import com.github.sladecek.maze.jmaze.generator.IMazeSpace;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.GenericShapeMaker;
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
		return shapeMaker.makeShapes(realization, getStartRoom(), getTargetRoom());
	}

	private void buildMaze() {
		shapeMaker = new GenericShapeMaker();
		
		int prevFirst = -1;
		int lastRoom = -1;
		
		// for all rows
		for (int y = 0; y < size; y++) {
			int myFirst = -1;
			int prevRoom = -1;
			final int rooms = 2*y+1;
			
			// make a row of rooms  and vertical walls among them
			for (int j = 0; j < rooms; j++) {
				int r = addRoom();
				int x1 = size + 2*j - rooms;
				int x2 = size + 2*j - rooms - 1;
				int y1 = y;
				int y2 = y;
				if ( j % 2 == 0) {
					y2++;
				} else {
					y1++;
				}
				
				String floorId = "r" + Integer.toString(r);
				final FloorShape floor = new FloorShape(y, x2, false, floorId);
				shapeMaker.linkRoomToFloor(r, floor);
				shapeMaker.addShape(floor);

				
				if (prevRoom > 0) {
					addWallAndShape(prevRoom, r, x1, x2, y1, y2);
				}
				prevRoom = r;
				if (j == 0) {
					myFirst = r;
				}
			}
			
			// connect rooms to upper row by horizontal walls
			if (prevFirst >= 0) {
				int i = 0;
				for (int j = 1; j < rooms; j+=2) {
					int x = size + 2*j - rooms - 1;
					addWallAndShape(prevFirst+i, myFirst+j, x, x+2, y, y);
				}
			}
			prevFirst = myFirst;
		}		
		
		setStartRoom(0);
		setTargetRoom(lastRoom);
	}

	private void addWallAndShape(int prevRoom, int room, int x1, int x2,
			int y1, int y2) {
		int id = addWall(prevRoom, room);
		WallShape ws = new WallShape(ShapeType.innerWall, y1, x1, y2, x2);
		shapeMaker.addShape(ws);
		shapeMaker.linkShapeToId(ws, id);
	}

	public int getSize() {
		return size;
	}
	
}
