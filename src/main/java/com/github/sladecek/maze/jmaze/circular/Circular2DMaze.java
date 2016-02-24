package com.github.sladecek.maze.jmaze.circular;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

public class Circular2DMaze extends Maze implements IMazeStructure {

	private int layerSize;

	public Circular2DMaze(int layerCount, int layerSize) {
		super();
		this.layerCount = layerCount;
		this.layerSize = layerSize;
		buildMaze();
	}


	private void buildMaze() {
		roomCounts = computeRoomCounts();
		firstRoomInLayer = new Vector<Integer>();
		firstRoomInLayer.setSize(layerCount);

		createContext();

		generateRooms();
		generateConcentricWalls();
		generateRadialWalls();
		setStartAndTargetRooms();
		// outer walls
		final IMazeShape.ShapeType ow = IMazeShape.ShapeType.outerWall;
		addShape(new WallShape(ow, layerCount * layerSize, 0, layerCount * layerSize, 0));
	}

	private void createContext() {
		final int height = 2 * layerCount * layerSize;
		final int width = 2 * layerCount * layerSize;
		final boolean isPolar = true;
		setContext(new ShapeContext(isPolar, height, width, 1, 0, 50));
	}

	private void setStartAndTargetRooms() {
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

		LOGGER.log(Level.INFO, "computeRoomRatio r=" + r + " ctnThis=" + cntThis + " cntNext=" + cntNext);
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

			final Point2D center = new Point2D(mapPhi(phi * roomRatio), (layerCount - r - 1) * layerSize);
			final FloorShape floor = new FloorShape(center, false);
			linkRoomToFloor(room, floor);
			addShape(floor);
		}

	}

	private Integer roomCntInOuterLayer() {
		return roomCounts.get(0);
	}

	private int mapPhi(int phi) {
		return (phi * Point2D.ANGLE_2PI) / roomCntInOuterLayer();
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
					addWallShape(roomCntThis, layerCount - r, layerCount - r, roomThis, roomThis + 1, id);
				}
			}
		}

	}

	private void addWallShape(int roomCntThisLayer, int r1, int r2, int phi1, int phi2, int id) {
		final int equatorCellCnt = roomCntInOuterLayer();
		final int roomMapRatio = equatorCellCnt / roomCntThisLayer;
		final int rphi1 = (phi1 * roomMapRatio) % equatorCellCnt;
		final int rphi2 = (phi2 * roomMapRatio) % equatorCellCnt;
		WallShape ws = new WallShape(ShapeType.innerWall, r1 * layerSize, mapPhi(rphi1), r2 * layerSize,
				mapPhi(rphi2));
		addShape(ws);
		linkShapeToId(ws, id);
	}

	private Vector<Integer> computeRoomCounts() {
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
				addWallShape(cnt, layerCount - r - 1, layerCount - r, phi, phi, id);
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
	private int layerCount;

	private Vector<Integer> roomCounts;
	private Vector<Integer> firstRoomInLayer;
}
