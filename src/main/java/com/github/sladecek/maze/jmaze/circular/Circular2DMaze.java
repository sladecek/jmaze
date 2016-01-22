package com.github.sladecek.maze.jmaze.circular;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.generator.GenericMazeSpace;
import com.github.sladecek.maze.jmaze.generator.IMazeSpace;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.geometry.Point2D;
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
	private Vector<Integer> firstRoomInLayer;

	@Override
	public ShapeContainer makeShapes(MazeRealization realization) {
		ShapeContainer result = shapeMaker.makeShapes(realization,
				getStartRoom(), getTargetRoom(), 0, 50);

		result.setPictureHeight(2 * layerCount);
		result.setPictureWidth(2 * layerCount);
		result.setPolarCoordinates(true);
		// outer walls
		/*
		 * TODO
		 * final IMazeShape.ShapeType ow = IMazeShape.ShapeType.outerWall;
		 * result.add(new WallShape(ow, 0, 0, 0, width)); result.add(new
		 * WallShape(ow, 0, 0, layerCount, 0)); result.add(new WallShape(ow, 0,
		 * width, layerCount, width)); result.add(new WallShape(ow, layerCount,
		 * 0, layerCount, width));
		 */
		return result;
	}

	private void buildMaze() {
		shapeMaker = new GenericShapeMaker();
		roomCounts = computeRoomCounts();
		firstRoomInLayer = new Vector<Integer>();
		firstRoomInLayer.setSize(layerCount);
		generateRooms();
		generateConcentricWalls();
		generateRadialWalls();
		setStartAndTaretRooms();
	}

	private void setStartAndTaretRooms() {
		setStartRoom(0);
		setTargetRoom(getRoomCount() - 1);
	}

	private void generateRooms() {
		for (int r = 0; r < layerCount; r++) {
			final int cntThis = getRoomCntInLayer(r);
			int thisNextRatio = computeRoomRatio(r);
			generateRowOfRooms(r, cntThis, thisNextRatio);
		}
	}

	private int computeRoomRatio(int r) {
		final int cntThis = getRoomCntInLayer(r);
		final int cntNext = getRoomCntInNextLayer(r);
		int thisNextRatio = 1;
		if (cntNext > 0) {
			thisNextRatio = cntThis / cntNext;
		}

		LOGGER.log(Level.INFO, "computeRoomRatio r=" + r + " ctnThis="
				+ cntThis + " cntNext=" + cntNext);
		return thisNextRatio;
	}

	private void generateRowOfRooms(int r, int cntThis, int thisNextRatio) {
		final int cntMax = roomCntInOuterLayer();
		final int roomRatio = cntMax / cntThis;
		for (int phi = 0; phi < cntThis; phi++) {
			int room = 0;
			if (phi % thisNextRatio == 0) {
				room = addRoom();
				if (phi == 0) {
					firstRoomInLayer.set(r, room);
				}
			}

			String floorId = "r" + Integer.toString(room);
			final FloorShape floor = new FloorShape(layerCount - r - 1, mapPhi(phi
					* roomRatio), false, floorId);
			shapeMaker.linkRoomToFloor(room, floor);
			shapeMaker.addShape(floor);
		}

	}

	private Integer roomCntInOuterLayer() {
		return roomCounts.get(0);
	}

	private int mapPhi(int phi) {
		return (phi * Point2D.ANGLE_2PI)/roomCntInOuterLayer();
	}

	private void generateConcentricWalls() {

		for (int r = 1; r < layerCount; r++) {
			LOGGER.log(Level.INFO, "generateConcentricWalls r=" + r);

			// the next layer may have less rooms than this one
			final int roomCntThis = getRoomCntInLayer(r);
			final int roomCntNext = getRoomCntInNextLayer(r);
			final int gRoomThis = firstRoomInLayer.get(r - 1);
			final int gRoomNext = firstRoomInLayer.get(r);

			final int roomCntRatio = computeRoomRatio(r);
			for (int roomNext = 0; roomNext < roomCntNext; roomNext++) {
				for (int j = 0; j < roomCntRatio; j++) {
					int roomThis = roomNext * roomCntRatio + j;
					int id = addWall(gRoomThis + roomThis, gRoomNext + roomNext);
					addWallShape(roomCntThis, r, r, roomThis, roomThis + 1, id);
				}
			}
		}

	}

	private void addWallShape(int roomCntThisLayer, int r1, int r2, int phi1,
			int phi2, int id) {
		final int equatorCellCnt = roomCntInOuterLayer();
		final int roomMapRatio = equatorCellCnt / roomCntThisLayer;
		final int rphi1 = (phi1 * roomMapRatio) % equatorCellCnt;
		final int rphi2 = (phi2 * roomMapRatio) % equatorCellCnt;
		WallShape ws = new WallShape(ShapeType.innerWall, r1, mapPhi(rphi1), r2, mapPhi(rphi2));
		shapeMaker.addShape(ws);
		shapeMaker.linkShapeToId(ws, id);
	}

	public Vector<Integer> computeRoomCounts() {
		// number of rooms in the equator layer
		double circumference = 2 * Math.PI * (layerCount + 1);
		int cnt = 1;
		while (cnt * 2 < circumference) {
			cnt *= 2;
		}

		Vector<Integer> result = new Vector<Integer>();
		result.add(cnt);

		// all layers except the central layer
		for (int i = 1; i < layerCount - 1; i++) {
			double currentRoomSize = 2 * Math.PI * (layerCount - i) / cnt;
			if (currentRoomSize < 0.5) {
				// cell becoming too narrow, join two cells together
				if (cnt >= 4) {
					cnt /= 2;
				}
			}
			result.add(cnt);
		}

		// central layer
		result.add(1);

		return result;
	}

	private void generateRadialWalls() {
		for (int r = 0; r < layerCount; r++) {
			LOGGER.log(Level.INFO, "generateRadialWalls i=" + r);

			final int cnt = getRoomCntInNextLayer(r);
			if (cnt <= 1) {
				continue;
			}

			final int gr = firstRoomInLayer.get(r);
			for (int j = 0; j < cnt; j++) {
				int id = addWall(gr + j, gr + (j + 1) % cnt);
				// strange wall naming convention - wall 0 is between room 0 and
				// 1
				int phi = (j + 1) % cnt;
				addWallShape(cnt, layerCount - r - 1, layerCount - r, phi, phi,  id);
			}
		}
	}

	private int getRoomCntInLayer(int r) {
		return roomCounts.get(r);
	}

	private int getRoomCntInNextLayer(int r) {
		if (r + 1 >= roomCounts.size()) {
			return 0;
		}
		return roomCounts.get(r + 1);
	}

	private static final Logger LOGGER = Logger.getLogger("maze.jmaze");
}
