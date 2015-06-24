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
	
	private final static Logger log = Logger.getLogger("LOG");

	private int startRoom;
	private int targetRoom;
	
	private Vector< Vector<Integer> > rooms = new Vector< Vector<Integer>>();
	private Vector<Integer> wallRoom1  = new Vector<Integer>();
	private Vector<Integer> wallRoom2  = new Vector<Integer>();
	
	public int addRoom() {
		int id = rooms.size();
		rooms.add(new Vector<Integer>());
		log.log(Level.INFO, "add room id="+id);
		return id;
		
	}
	
	public int addWall(int room1, int room2) {
		if (rooms.size() <= room1 || rooms.size() <= room2){
			log.log(Level.SEVERE, "cannot add wall room1="+room1+" room2="+room2);
			return 0;
		}
		int id = wallRoom1.size();	
		wallRoom1.add(room1);
		rooms.elementAt(room1).add(id);
		wallRoom2.add(room2);		
		rooms.elementAt(room2).add(id);
		log.log(Level.INFO, "add wall id="+id+" room1="+room1+" room2="+room2);
		return id;
	}

	@Override
	public int getRoomCount() {
		return rooms.size();
	}

	@Override
	public Iterable<Integer> getWalls(int room) {
		return rooms.elementAt(room);
	}

	@Override
	public int getStartRoom() {
		return startRoom;
	}

	@Override
	public int getTargetRoom() {		
		return targetRoom;
	}

	public void setStartRoom(int startRoom) {
		this.startRoom = startRoom;
	}

	public void setTargetRoom(int targetRoom) {
		this.targetRoom = targetRoom;
	}


	@Override
	public double getRoomDistance(int r1, int r2) {
		return 0;
	}

	@Override
	public int getOtherRoom(int room, int wall) {
		int r1 = wallRoom1.elementAt(wall);
		int r2 = wallRoom2.elementAt(wall);
		return r1 == room ? r2 : r1;
	}


	@Override
	public int getWallProbabilityWeight(int wall) {
		return 1;
	}

	@Override
	public int getWallCnt() {
		return wallRoom1.size();
	}

	
}
