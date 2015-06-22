package com.github.sladecek.maze.jmaze.spheric;

import java.util.Vector;

import com.github.sladecek.maze.jmaze.generator.GenericMazeSpace;
import com.github.sladecek.maze.jmaze.generator.IMazeSpace;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.print.IPrintableMaze;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

/**
 * 
 * Represent and generate rooms and walls of a maze on an egg-like shape.
 *
 */
public class EggMaze extends GenericMazeSpace implements IMazeSpace,
		IPrintableMaze {

	private EggGeometry egg;
	private int equatorCellCnt;

	private EggMazeHemisphere north = new EggMazeHemisphere();
	private EggMazeHemisphere south = new EggMazeHemisphere();
	private Vector<IMazeShape> shapes = new Vector<IMazeShape>();
	private double baseRoomSize_mm;

	public EggMazeHemisphere getHemisphere(SouthNorth sn) {
		return sn == SouthNorth.north ? north : south;
	}

	public EggMaze(EggGeometry egg, int equatorCellCnt) {
		this.egg = egg;
		this.equatorCellCnt = equatorCellCnt;
		this.baseRoomSize_mm = egg.computeBaseRoomSize_mm(equatorCellCnt);

		if (this.equatorCellCnt < 4) {
			throw new IllegalArgumentException(
					"Maze must have at least 4 cells.");
		}

		if (!isPowerOfTwo(this.equatorCellCnt)) {
			throw new IllegalArgumentException(
					"Cell number must be power of 2.");
		}

		// generate both hemispheres
		for (SouthNorth sn : SouthNorth.values()) {
			divideSpace(sn);
			generateRooms(sn);

			generateParallelWalls(sn);
			generateMeridianWalls(sn);
		}
		generateParallelWallsOnEquator();
		assignStartAndTragetRooms();

	}

	private void assignStartAndTragetRooms() {
		setStartRoom(north.getGreenwichRoom(north.getLayerCnt()-1));
		setTargetRoom(south.getGreenwichRoom(south.getLayerCnt()-1));
		
	}

	private void divideSpace(SouthNorth sn) {

		final EggMazeHemisphere h = getHemisphere(sn);
		Vector<Double> xPos = egg.divideMeridian(baseRoomSize_mm, sn);

		Vector<Integer> cellCnt = computeRoomCounts(xPos, equatorCellCnt,
				baseRoomSize_mm);
		h.setLayerXPosition(new Vector<Double>(xPos.subList(0, cellCnt.size())));
		h.setLayerRoomCnt(cellCnt);

	}

	/**
	 * Generate rooms of either north or south hemisphere.
	 * 
	 */
	private void generateRooms(SouthNorth sn) {

		final EggMazeHemisphere h = getHemisphere(sn);

		final int cnt = h.getLayerCnt();
		for (int ix = 0; ix < cnt; ix++) {
			int x = ix;
			if (sn == SouthNorth.south) {
				x = -1 - ix;
			}
			// if (ix == cnt-3) {
			final int cntThis = h.getGeometricalRoomCntInLayer(ix);
			final int cntNext = h.getGeometricalRoomCntInNextLayer(ix);
			generateRowOfRooms(h, x, cntThis, cntNext, h.isPolarLayer(ix));
			// }
		}
	}

	private void generateRowOfRooms(EggMazeHemisphere hemisphere, int ix,
			int cntThis, int cntNext, boolean isPolarLayer) {
		final int thisNextRatio = cntThis / cntNext;
		final int roomMapRatio = equatorCellCnt / cntThis;
		for (int iy = 0; iy < cntThis; iy++) {
			if (iy == 0) {
				int r = addRoom();
				hemisphere.addGreenwichRoom(r);
			} else if (!isPolarLayer && iy % thisNextRatio == 0) {
				// polar layers have only one room
				addRoom();
			}
			shapes.add(new FloorShape(iy * roomMapRatio, ix, false));
		}

	}

	private void generateParallelWalls(SouthNorth sn) {
		EggMazeHemisphere h = getHemisphere(sn);
		for (int i = 0; i < h.getLayerCnt() - 1; i++) {
			System.out.println("para layer="+i);

			// the next layer may have less rooms than this one
			final int roomCntThis = h.getLogicalRoomCntInLayer(i);
			final int roomCntNext = h.getLogicalRoomCntInLayer(i + 1);
			final int gRoomThis = h.getGreenwichRoom(i);
			final int gRoomNext = h.getGreenwichRoom(i + 1);

			int x = sn == SouthNorth.south ? -1 - i : i + 1;

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

	private void generateParallelWallsOnEquator() {
		final int gRoomNorth = north.getGreenwichRoom(0);
		final int gRoomSouth = south.getGreenwichRoom(0);

		for (int i = 0; i < equatorCellCnt; i++) {
			int id = addWall(gRoomNorth + i, gRoomSouth + i);
			addWallShape(equatorCellCnt, i, i + 1, 0, 0, id);
		}

	}

	public int getEquatorCellCnt() {
		return equatorCellCnt;
	}

	private void addWallShape(int roomCntThisLayer, int yr1, int yr2, int x1,
			int x2, int id) {
		final int roomMapRatio = equatorCellCnt / roomCntThisLayer;
		final int y1 = (yr1 * roomMapRatio) % equatorCellCnt;
		final int y2 = (yr2 * roomMapRatio) % equatorCellCnt;
		WallShape ws = new WallShape(ShapeType.innerWall, y1, x1, y2, x2);
		ws.setWall(id);
		shapes.add(ws);
	}

	public Vector<Integer> computeRoomCounts(Vector<Double> layerXPosition,
			int layerRoomCnt, double baseRoomSize_mm) {

		final int layerCnt = layerXPosition.size();
		Vector<Integer> result = new Vector<Integer>();
		// equator
		result.add(layerRoomCnt);
		int roomCnt = layerRoomCnt;
		for (int i = 1; i < layerCnt - 1; i++) {
			final double x = layerXPosition.elementAt(i);
			final double y = egg.computeY(x);

			double currentRoomSize_mm = 2 * Math.PI * y / roomCnt;

			if (currentRoomSize_mm < baseRoomSize_mm / 2) {
				// egg is becoming too narrow, join two cells together
				roomCnt /= 2;
				if (roomCnt < 4) {
					break;
				}
			}
			result.add(roomCnt);
		}
		// pole
		return result;
	}

	private void generateMeridianWalls(SouthNorth sn) {
		EggMazeHemisphere h = getHemisphere(sn);
		for (int i = 0; i < h.getLayerCnt(); i++) {
			if (!h.isPolarLayer(i)) {
				final int cnt = h.getGeometricalRoomCntInLayer(i);

				final int gr = h.getGreenwichRoom(i);
				for (int j = 0; j < cnt; j++) {
					int id = addWall(gr + j, gr + (j + 1) % cnt);
					if (sn == SouthNorth.north) {
						addWallShape(cnt, j, j, i, i + 1, id);
					} else {
						addWallShape(cnt, j, j, -i - 1, -i, id);
					}
				}
			}
		}

	}

	private boolean isPowerOfTwo(int n) {
		for (;;) {
			if (n == 1 || n == 0) {
				return true;
			}
			if (n % 2 == 1) {
				return false;
			}
			n /= 2;
		}
	}

	@Override
	public Iterable<IMazeShape> getShapes() {
		return shapes;
	}

	@Override
	public int getPictureHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPictureWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getBaseRoomSize_mm() {
		return this.baseRoomSize_mm;
	}

}
