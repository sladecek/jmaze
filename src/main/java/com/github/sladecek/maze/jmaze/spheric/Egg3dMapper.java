package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.OrientationVector2D;
import com.github.sladecek.maze.jmaze.geometry.Point;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;
import com.github.sladecek.maze.jmaze.print.IMaze3DMapper;

public class Egg3dMapper implements IMaze3DMapper {

	public Egg3dMapper(EggGeometry egg, EggMaze maze) {
		super();
		this.egg = egg;
		this.maze = maze;
	}

	/**
	 * Map integer room coordinates to 3D coordinates on the egg.
	 * 
	 * @param y
	 *            Longitudal room number. Unit is 2*pi/#of rooms on the equator. The
	 *            rooms on the equator have consecutive numbers 0,1,2.... Following layers
	 *            may have less rooms and their numbers may skip some numbers 0,4,8....
	 *            
	 * @param x
	 *            Latitudal room number. Zero index is equator.
	 *            
	 *            
	 * @return 3D coordinate of south-western corner of the room + offset.
	 * 
	 */
	@Override	
	public Point mapPoint(int cellY, int cellX, double offsetY,
			double offsetX, double offsetZ) {
		double xx;
		final int cnt = maze.getEquatorCellCnt();

		if (cellX >= 0 ) {
			EggMazeHemisphere h = maze.getHemisphere(SouthNorth.north);
			xx = h.layerXPosition.elementAt(cellX);
					
		} else {
			EggMazeHemisphere h = maze.getHemisphere(SouthNorth.south);
			xx = -h.layerXPosition.elementAt(-cellX);
			
		}
		
		OrientationVector2D normal = egg.computeNormalVector(xx);
		OrientationVector2D tangent = normal.getOrthogonal();
		
		double xxx = xx + offsetX * tangent.getX() + offsetZ * normal.getX();
		double yyy = egg.computeY(xx) + offsetX * tangent.getY() + offsetZ * normal.getY();
		double angle = 2*Math.PI*cellY/cnt;
		
		double yyyy = yyy*Math.cos(angle) + offsetY*Math.sin(angle);
		double zzzz = -yyy*Math.sin(angle) + offsetY*Math.cos(angle);
		
		Point result = new Point(xxx, yyyy, zzzz);
		return result;			
	}
	
	
	@Override
	public Point mapCorner(int cellX, EastWest ew, UpDown ud,
			SouthNorth snWall, SouthNorth snEdge) {
		throw new IllegalArgumentException("Egg has no corners");
	}

	EggGeometry egg;
	EggMaze maze;
}
