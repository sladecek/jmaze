package com.github.sladecek.maze.jmaze.spheric;

import java.awt.List;
import java.util.Arrays;
import java.util.Vector;

import com.github.sladecek.maze.jmaze.generator.GenericMazeSpace;
import com.github.sladecek.maze.jmaze.generator.IMazeSpace;
import com.github.sladecek.maze.jmaze.geometry.Point;
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

	public EggMazeHemisphere getHemisphere(SouthNorth sn) {
		return sn == SouthNorth.north ? north : south;
	}
	
	public EggMaze(EggGeometry egg, int equatorCellCnt) {
		this.egg = egg;
		this.equatorCellCnt = equatorCellCnt;

		if (this.equatorCellCnt < 4) {
			throw new IllegalArgumentException(
					"Maze must have at least 4 cells.");
		}

		if (!isPowerOfTwo(this.equatorCellCnt)) {
			throw new IllegalArgumentException(
					"Cell number must be power of 2.");
		}

		generateRooms(0, 1, north);
		generateParallelWalls(north);
		generateMeridianWalls(north);

		generateRooms(0, -1, south);
		generateParallelWalls(south);
		generateMeridianWalls(south);

		generateParallelWallsOnEquator();

	}

	private void generateParallelWallsOnEquator() {
		final int gRoomNorth = north.greenwichRoom.elementAt(0);
		final int gRoomSouth = south.greenwichRoom.elementAt(0);

		for (int i = 0; i < equatorCellCnt; i++) {
			addWall(gRoomNorth+i, gRoomSouth+i);
			shapes.add(new WallShape(ShapeType.innerWall, i, 0, i+1, 0));		
		}

	}


	private void generateParallelWalls(EggMazeHemisphere h) {
		for (int i = 0; i < h.layerXPosition.size()-1; i++) {
 
			final int cellCntThis = h.layerRoomCnt.elementAt(i);
			final int cellCntNext = h.layerRoomCnt.elementAt(i+1);
			final int cellCntRatio = cellCntThis/cellCntNext;
			
			final int gRoomThis = h.greenwichRoom.elementAt(i);
			final int gRoomNext = h.greenwichRoom.elementAt(i+1);
					
			
			generateParallelWallLayer(i, cellCntThis, cellCntRatio, gRoomThis, gRoomNext);
		}
		
	}

	private void generateParallelWallLayer(int x, int roomCnt, int cellCntRatio, int gRoomThis, int gRoomNext) {
		for (int roomThis = 0; roomThis< roomCnt; roomThis++ ) {
			for(int j=0; j < cellCntRatio; j++) {
				int roomNext = roomThis * cellCntRatio + j;
				addWall(gRoomThis+roomThis, gRoomNext + roomNext);
				shapes.add(new WallShape(ShapeType.innerWall, roomThis, x, roomThis+1, x));								
			}
		}
		
	}



	/**
	 * Generate rooms of either north or south hemisphere.
	 * 
	 * @param x0
	 * @param dix
	 * @param half
	 *            which hemisphere.
	 */
	private void generateRooms(final double x0, final int dix,
			final EggMazeHemisphere half) {

		final double baseRoomSize_mm = egg.getBaseRoomSize_mm();

		half.layerXPosition = egg.divideMeridianEquidistantly(baseRoomSize_mm, dix);
		half.layerRoomCnt = computeRoomCounts(half.layerXPosition, equatorCellCnt, baseRoomSize_mm);

		for (int ix =0; ix < half.layerRoomCnt.size(); ix++) {
			createLayerOfCells(half, ix, half.layerRoomCnt.elementAt(ix));
		}
	}

	private Vector<Integer> computeRoomCounts(Vector<Double> layerXPosition,
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

	private void createLayerOfCells(EggMazeHemisphere hemisphere, int ix,
			 int layerRoomCnt) {
		for (int i = 0; i < layerRoomCnt; i++) {
			int r = addRoom();
			if (i == 0) {
				hemisphere.greenwichRoom.add(r);
			}
			shapes.add(new FloorShape(i, ix, false));
		}

	}

	private void generateMeridianWalls(EggMazeHemisphere h) {
		for (int i = 0; i < h.layerRoomCnt.size(); i++) {
			final int cnt = h.layerRoomCnt.elementAt(i);
			final int gr = h.greenwichRoom.elementAt(i);
			for (int j = 0; j < cnt; j++) {
				addWall(gr + j, gr + (j + 1) % cnt);
				shapes.add(new WallShape(ShapeType.innerWall, j, i, j, i + 1));
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

}
