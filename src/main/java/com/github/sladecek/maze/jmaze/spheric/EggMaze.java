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
		for (SouthNorth sn: SouthNorth.values())
		{
			generateRooms(sn);			
			generateParallelWalls(sn);
			generateMeridianWalls(sn);
		}
		generateParallelWallsOnEquator();


		
	}

	private void generateParallelWallsOnEquator() {
		final int gRoomNorth = north.greenwichRoom.elementAt(0);
		final int gRoomSouth = south.greenwichRoom.elementAt(0);

		for (int i = 0; i < equatorCellCnt; i++) {
			addWall(gRoomNorth+i, gRoomSouth+i);			
			addWallShape(equatorCellCnt, i, i+1, 0,0);			
		}

	}


	public int getEquatorCellCnt() {
		return equatorCellCnt;
	}

	private void generateParallelWalls(SouthNorth sn) {
		EggMazeHemisphere h = getHemisphere(sn);
		for (int i = 0; i < h.layerXPosition.size()-2; i++) {
 
			// the next layer may have less rooms than this one
			final int roomCntThis = h.layerRoomCnt.elementAt(i);
			final int roomCntNext = h.layerRoomCnt.elementAt(i+1);
			final int gRoomThis = h.greenwichRoom.elementAt(i);
			final int gRoomNext = h.greenwichRoom.elementAt(i+1);
					
			int x = sn == SouthNorth.south ? -1-i : i+1;
			
			final int roomCntRatio = roomCntThis/roomCntNext;		
			for (int roomNext = 0; roomNext< roomCntNext; roomNext++ ) {
				for(int j=0; j < roomCntRatio; j++) {
					int roomThis = roomNext * roomCntRatio + j;
					addWall(gRoomThis+roomThis, gRoomNext + roomNext); 
					addWallShape(roomCntThis, roomThis, roomThis+1, x, x);					
				}
			}
		}
		
	}

	private void addWallShape(int roomCntThisLayer, int yr1, int yr2, int x1, int x2) {
		final int roomMapRatio = equatorCellCnt/roomCntThisLayer;
		final int y1 = (yr1 * roomMapRatio) % equatorCellCnt;
		final int y2 = (yr2 * roomMapRatio) % equatorCellCnt; 
		shapes.add(new WallShape(ShapeType.innerWall, y1, x1, y2, x2));
	}


	public Vector<Integer> computeRoomCounts(Vector<Double> layerXPosition,
			int layerRoomCnt, double baseRoomSize_mm) {
		
		final int layerCnt = layerXPosition.size();
		Vector<Integer> result = new Vector<Integer>();
		// equator
		result.add(layerRoomCnt);
		int roomCnt = layerRoomCnt;
		for (int i = 1; i < layerCnt-1; i++) {
			final double x = layerXPosition.elementAt(i);
			final double y = egg.computeY(x);
			
			double currentRoomSize_mm = 2*Math.PI*y	/ roomCnt;
			
			if (roomCnt >= 4*2 && currentRoomSize_mm < baseRoomSize_mm / 2) {
				// egg is becoming too narrow, join two cells together
				roomCnt /= 2;
			}
			result.add(roomCnt);
		}
		// pole
		result.add(1);
		return result;
	}

	/**
	 * Generate rooms of either north or south hemisphere.
	 * 
	 */
	private void generateRooms(SouthNorth sn) {

		final EggMazeHemisphere h = getHemisphere(sn);
		h.layerXPosition = egg.divideMeridian(baseRoomSize_mm, sn);
		h.layerRoomCnt = computeRoomCounts(h.layerXPosition, equatorCellCnt, baseRoomSize_mm);

		for (int ix = 0; ix < h.layerRoomCnt.size()-1; ix++) {
			int x = ix;
			if (sn == SouthNorth.south) {
				x = -1 -ix;
			}
			createLayerOfCells(h, x, h.layerRoomCnt.elementAt(ix));
		}
	}


	private void createLayerOfCells(EggMazeHemisphere hemisphere, int x,
			 int roomCnt) {
		final int roomMapRatio = equatorCellCnt/roomCnt;
		for (int y = 0; y < roomCnt; y++) {
			int r = addRoom();
			if (y == 0) {
				hemisphere.greenwichRoom.add(r);
			}
			shapes.add(new FloorShape(y*roomMapRatio, x, false));
		}

	}

	private void generateMeridianWalls(SouthNorth sn) {
		EggMazeHemisphere h = getHemisphere(sn);
		for (int i = 0; i < h.layerRoomCnt.size(); i++) {
			final int cnt = h.layerRoomCnt.elementAt(i);
			if (cnt > 1) {
				final int roomMapRatio = equatorCellCnt/cnt;
				final int gr = h.greenwichRoom.elementAt(i);
				for (int j = 0; j < cnt; j++) {
					addWall(gr + j, gr + (j + 1) % cnt);
					if (sn == SouthNorth.north) {
						addWallShape(cnt, j, j, i, i+1);
					} else {
						addWallShape(cnt, j, j, -i-1, -i);
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
