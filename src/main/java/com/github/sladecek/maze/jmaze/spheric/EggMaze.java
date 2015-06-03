package com.github.sladecek.maze.jmaze.spheric;
import java.util.Vector;

import com.github.sladecek.maze.jmaze.generator.GenericMazeSpace;
import com.github.sladecek.maze.jmaze.generator.IMazeSpace;
import com.github.sladecek.maze.jmaze.print.IPrintableMaze;
import com.github.sladecek.maze.jmaze.shapes.HoleShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.LineShape;

/**
 * 
 * Generate rooms and walls of a maze on an egg-like shape.
 *
 */
public class EggMaze extends GenericMazeSpace implements IMazeSpace, IPrintableMaze {

	private EggGeometry egg;
	private int equatorCellCnt;
	
	private EggMazeHemisphere north = new EggMazeHemisphere();
	private EggMazeHemisphere south = new EggMazeHemisphere();
	private Vector<IMazeShape> shapes = new Vector<IMazeShape>();
	
	
	public EggMaze(EggGeometry egg, int equatorCellCnt)  {
		this.egg = egg;
		this.equatorCellCnt = equatorCellCnt;
		
		if (this.equatorCellCnt < 4) {
			throw new IllegalArgumentException("Maze must have at least 4 cells.");
		}
		
		if (!isPowerOfTwo(this.equatorCellCnt)) {
			throw new IllegalArgumentException("Cell number must be power of 2.");
		}
		
		generateRooms(0, 1, north);
		generateParallelWalls(north);
		generateMeridianWalls(north);
		generateNorthPole();
		
		generateRooms(0, -1, south);
		generateParallelWalls(south);
		generateMeridianWalls(south);
		generateSouthPole();
		
		generateParallelWallsOnEquator();
		
		
	}
	
	

	private void generateParallelWallsOnEquator() {
		// TODO Auto-generated method stub
		
	}

	private void generateSouthPole() {
		// TODO Auto-generated method stub
		
	}

	private void generateNorthPole() {
		// TODO Auto-generated method stub
		
	}

	private void generateParallelWalls(EggMazeHemisphere h) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Map integer room coordinates to 3D coordinates on the egg.
	 * @param y Longitudeal room number.
	 * @param x Latitudeal room number. Zero index is north equator layer. Minus one layer is south equator layer.
	 * @return 3D coordinate of south-western corner of the room.
	 */
	public Point mapPoint(int y, int x) {
		
	}


/**
 * Generate rooms of either north or south hemisphere.
 * @param x0
 * @param dix
 * @param half which hemisphere.
 */
	private void generateRooms(final double x0,
			 final int dix, final EggMazeHemisphere half) {

		final double baseRoomSize_mm = egg.getBaseRoomSize_mm();
		int ix = 0;
		double x = x0;
		int layerCellCnt = equatorCellCnt;
		
		for(;;) {
			double y = egg.computeY(x);
			createLayerOfCells(half, ix, x, layerCellCnt );
			ix += dix;
			x = egg.findNextX(x, baseRoomSize_mm * dix);
			double currentRoomSize_mm = egg.getCircumferenceAt_mm(x) / layerCellCnt;
			if (currentRoomSize_mm < baseRoomSize_mm/2) {
				// egg is becoming too narrow, join two cells together
				layerCellCnt /= 2;				
			}
			if (layerCellCnt < 4 )
			{
				break;
			}
		}
	}

	private void createLayerOfCells(EggMazeHemisphere hemisphere, int ix, double x,
			int layerCellCnt) {
		hemisphere.layerXPosition.add(x);
		hemisphere.layerCellCnt.add(layerCellCnt);
		for (int i = 0; i < layerCellCnt; i++) {
			int r = addRoom();
			if (i == 0) {
				hemisphere.greenwichWall.add(r);				
			}
			shapes.add(new HoleShape(i, ix, false));
		}
		
	}

	private void generateMeridianWalls(EggMazeHemisphere h) {
		for (int i = 0; i < h.layerCellCnt.size(); i++) {
			final int cnt = h.layerCellCnt.elementAt(i);
			final int gr = h.greenwichRoom.elementAt(i);
			for (int j = 0; j < cnt; j++) {
				addWall(gr+j, gr+(j+1)%cnt);
				shapes.add(new LineShape(ShapeType.innerWall, j,  i, j, i+1));
			}
		}
		
	}
	
	private boolean isPowerOfTwo(int n) {
		for(;;) {
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
