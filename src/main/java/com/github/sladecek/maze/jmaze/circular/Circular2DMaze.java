package com.github.sladecek.maze.jmaze.circular;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.generator.GenericMazeSpace;
import com.github.sladecek.maze.jmaze.generator.IMazeSpace;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.GenericShapeMaker;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.IShapeMaker;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

public class Circular2DMaze extends GenericMazeSpace implements IMazeSpace,
		IShapeMaker {

	public Circular2DMaze(int layerCount) {
		super();
		this.layerCount = layerCount;
		buildMaze();
	}

	private int layerCount;

	private GenericShapeMaker shapeMaker;

	private Vector<Integer> roomCounts;


	@Override
	public ShapeContainer makeShapes(MazeRealization realization) {
		ShapeContainer result = shapeMaker.makeShapes(realization,
				getStartRoom(), getTargetRoom(), 0, 50);
/* TODO		result.setPictureHeight(width);
		result.setPictureWidth(2 * width);
*/
		// outer walls
	/*	final IMazeShape.ShapeType ow = IMazeShape.ShapeType.outerWall;
		result.add(new WallShape(ow, 0, 0, 0, width));
		result.add(new WallShape(ow, 0, 0, layerCount, 0));
		result.add(new WallShape(ow, 0, width, layerCount, width));
		result.add(new WallShape(ow, layerCount, 0, layerCount, width));
*/
		return result;
	}

	private void buildMaze() {
		shapeMaker = new GenericShapeMaker();


		shapeMaker = new GenericShapeMaker();
			divideS4pace();
			generateRooms();

			generateParallelWalls();
			generateMeridianWalls();
		


		// TODO
/*		setStartRoom(0);
		setTargetRoom(roomCount - 1);*/
	}


	private void divideS4pace() {
		roomCounts = computeRoomCounts();		
	}

	private void generateRooms() {


		for (int ix = 0; ix < layerCount; ix++) {
			int x = ix;
			final int cntThis = getRoomCntBeforeCircle(ix);
			final int cntNext = getRoomCntAfterCircle(ix);
			LOGGER.log(Level.INFO, "generate row of rooms ix="+ix+" x="+x+" ctnThis="+cntThis+" cntNext="+cntNext);
			generateRowOfRooms(x, cntThis, cntNext, isPolarLayer(ix));
		}
	}

	private void generateRowOfRooms(int ix,
			int cntThis, int cntNext, boolean isPolarLayer) {
		final int thisNextRatio = cntThis / cntNext;
		final int equatorCellCnt = roomCounts.get(0); 
		final int roomMapRatio = equatorCellCnt / cntThis;
		for (int iy = 0; iy < cntThis; iy++) {
			int r = 0;
			if (iy == 0) {
				r = addRoom();
//				hemisphere.addGreenwichRoom(r);
			} else if (!isPolarLayer && iy % thisNextRatio == 0) {
				// polar layers have only one room
				r = addRoom();
			}
			String floorId = "r" + Integer.toString(r);
			final FloorShape floor = new FloorShape(iy * roomMapRatio, ix, false, floorId);
			shapeMaker.linkRoomToFloor(r, floor);
			shapeMaker.addShape(floor);
			
		}

	}

	private void generateParallelWalls() {

		for (int i = 1; i < layerCount; i++) {
			LOGGER.log(Level.INFO, "generateParallesWalls i="+i);
			
			// the next layer may have less rooms than this one
			final int roomCntThis = getRoomCntBeforeCircle(i);
			final int roomCntNext = getRoomCntAfterCircle(i);
			final int gRoomThis = getGreenwichRoom(i-1);
			final int gRoomNext = getGreenwichRoom(i);

			int x =  i;

			final int roomCntRatio = roomCntThis / roomCntNext;
			for (int roomNext = 0; roomNext < roomCntNext; roomNext++) {
				for (int j = 0; j < roomCntRatio; j++) {
					int roomThis = roomNext * roomCntRatio + j;
					int id = addWall(gRoomThis + roomThis, gRoomNext + roomNext);
					addWallShape(roomCntThis, roomThis, roomThis + 1, x, x, id);
				}
			}
		}

	}

	private void addWallShape(int roomCntThisLayer, int yr1, int yr2, int x1,
			int x2, int id) {
		final int equatorCellCnt = roomCounts.get(0);
		final int roomMapRatio = equatorCellCnt / roomCntThisLayer;
		final int y1 = (yr1 * roomMapRatio) % equatorCellCnt;
		final int y2 = (yr2 * roomMapRatio) % equatorCellCnt;
		WallShape ws = new WallShape(ShapeType.innerWall, y1, x1, y2, x2);
		shapeMaker.addShape(ws);
		shapeMaker.linkShapeToId(ws, id);
	}

	public Vector<Integer> computeRoomCounts() {
		// number of rooms in the equator layer
		double circumference = 2*Math.PI*(layerCount+1);
		int cnt = 1;
		while ( cnt * 2 < circumference) {
			cnt*= 2;
		}
		
		Vector<Integer> result = new Vector<Integer>();
		// equator
		result.add(cnt);
		
		
		// all layers except the polar layer
		for (int i = 1; i < layerCount - 1 ; i++) { 

			double currentRoomSizeInmm = 2 * Math.PI * (layerCount-i) / cnt;

			if (currentRoomSizeInmm < 1 / 2) {
				// cell becoming too narrow, join two cells together
				if (cnt >= 4) {
					cnt /= 2;
				}
			}
			result.add(cnt);
		}
		
		// polar layer
		result.add(1);
		
		return result;
	}

	private void generateMeridianWalls() {
		for (int i = 0; i < layerCount; i++) {
			LOGGER.log(Level.INFO, "generateMeridianWalls i=" + i);

			final int cnt = getRoomCntAfterCircle(i);
			if (cnt <= 1) {
				continue;
			}

			final int gr = getGreenwichRoom(i);
			for (int j = 0; j < cnt; j++) {
				int id = addWall(gr + j, gr + (j + 1) % cnt);
				// strange wall naming convention - wall 0 is between room 0 and 1
				int jj = (j + 1) % cnt; 
					addWallShape(cnt, jj, jj, i, i + 1, id);
		
			}

		}

	}


	private int getRoomCntBeforeCircle(int ix) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getRoomCntAfterCircle(int i) {
		// TODO Auto-generated method stub
		return 0;
	}


	private int getGreenwichRoom(int i) {
		// TODO Auto-generated method stub
		return 0;
	}


	private boolean isPolarLayer(int ix) {
		// TODO Auto-generated method stub
		return false;
	}

	
	private static final Logger LOGGER = Logger.getLogger("maze.jmaze");
}
