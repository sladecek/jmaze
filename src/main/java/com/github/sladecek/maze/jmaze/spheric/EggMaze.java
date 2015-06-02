package com.github.sladecek.maze.jmaze.spheric;
import com.github.sladecek.maze.jmaze.generator.GenericMazeSpace;
import com.github.sladecek.maze.jmaze.generator.IMazeSpace;
import com.github.sladecek.maze.jmaze.print.IPrintableMaze;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;

public class EggMaze extends GenericMazeSpace implements IMazeSpace, IPrintableMaze {

	private Egg egg;
	private int equator_cells;

	public EggMaze(Egg egg, int equator_cells)  {
		this.egg = egg;
		this.equator_cells = equator_cells;
		
		if (this.equator_cells < 8) {
			throw new IllegalArgumentException("Maze must have at least 8 cells.");
		}
		
		if (!isPowerOfTwo(this.equator_cells)) {
			throw new IllegalArgumentException("Cell number must be power of 2.");
		}
		generateNorthRooms();
		generateSouthRooms();
	}

	private void generateSouthRooms() {
		// TODO Auto-generated method stub
		
	}

	private void generateNorthRooms() {
		double x = 0;
		double y = egg.computeY(x);
		int layerCellCnt = equator_cells;
		for(;;) {
			// add layer of cells
			// link cells in layer
			// link cells to previous layer
			// compute next x, y, cellCnt		 
			
			// if x > egg.pole || cellCnt
			//   last layer add pole room and link it
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
		// TODO Auto-generated method stub
		return null;
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
