package com.github.sladecek.maze.jmaze.spheric;

import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.geometry.Point;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;

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
	 *            Longitudeal room number.
	 * @param x
	 *            Latitudeal room number. Zero index is north equator layer.
	 *            Minus one layer is south equator layer.
	 * @return 3D coordinate of south-western corner of the room.
	 */
	public Point mapPoint(int y, int x) {
		double xx;
		int cnt;
		
		if (x >= 0 ) {
			EggMazeHemisphere h = maze.getHemisphere(SouthNorth.north);
			xx = h.layerXPosition.elementAt(x);
			cnt = h.layerRoomCnt.elementAt(x);
		} else {
			EggMazeHemisphere h = maze.getHemisphere(SouthNorth.south);
			xx = -h.layerXPosition.elementAt(-x);
			cnt = h.layerRoomCnt.elementAt(-x);
		}
		double r = egg.computeY(xx);
		double angle = 2*Math.PI*(double)y/(double)cnt;
		Point result = new Point(xx, r*Math.cos(angle), r*Math.sin(angle));
		return result;			
	}
	
	@Override
	public Point mapPoint(int cellY, int cellX, double offsetY,
			double offsetX, double z) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Point getOuterPoint(int cellX, EastWest ew, UpDown ud,
			SouthNorth snWall, SouthNorth snEdge) {
		throw new IllegalArgumentException("Egg has no corners");
	}

	EggGeometry egg;
	EggMaze maze;
}
