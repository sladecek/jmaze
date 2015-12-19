package com.github.sladecek.maze.jmaze.generator;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic maze space implementation without any particular properties. There is a list
 * of rooms and walls. The rooms and wall must be added to lists one by one
 * during maze construction.
 */
public class GenericMazeSpace  implements IMazeSpace {
	
	public final int addRoom() {
		int id = rooms.size();
		rooms.add(new Vector<Integer>());
		LOGGER.log(Level.INFO, "add room id=" + id);
		return id;		
	}
	
	public final int addWall(final int room1, final int room2) {
		if (rooms.size() <= room1 || rooms.size() <= room2) {
			LOGGER.log(Level.SEVERE, "cannot add wall room1=" + room1 + " room2=" + room2);
			return 0; 
		}
		int id = wallRoom1.size();	
		wallRoom1.add(room1);
		rooms.elementAt(room1).add(id);
		wallRoom2.add(room2);		
		rooms.elementAt(room2).add(id);
		LOGGER.log(Level.INFO, "add wall id=" + id + " room1=" + room1 + " room2=" + room2);
		return id;
	}

	@Override
	public final int getRoomCount() {
		return rooms.size();
	}

	@Override
	public final int getWallCount() {
		return wallRoom1.size();
	}
		
	@Override
	public final Iterable<Integer> getWalls(final int room) {
		return rooms.elementAt(room);
	}

	@Override
	public final int getStartRoom() {
		return startRoom;
	}

	@Override  
	public final int getTargetRoom() {		
		return targetRoom;
	}

	public final void setStartRoom(final int startRoom) {
		this.startRoom = startRoom;
	}

	public final void setTargetRoom(final int targetRoom) {
		this.targetRoom = targetRoom;
	}

	@Override
	public final double getRoomDistance(final int r1, final int r2) {
		return 0;
	}

	@Override
	public final int getRoomBehindWall(final int room, final int wall) {
		int r1 = wallRoom1.elementAt(wall);
		int r2 = wallRoom2.elementAt(wall);
		if (r1 == room) {
			return r2;
		} else {
			return r1;
		}
	}

	@Override
	public final int getWallProbabilityWeight(int wall) {
		return 1;
	}

	private static final Logger LOGGER =  Logger.getLogger("maze.jmaze");

	private int startRoom;
	private int targetRoom;
	
	private Vector<Vector<Integer>> rooms = new Vector<Vector<Integer>>();
	private Vector<Integer> wallRoom1  = new Vector<Integer>();
	private Vector<Integer> wallRoom2  = new Vector<Integer>();
	
}
