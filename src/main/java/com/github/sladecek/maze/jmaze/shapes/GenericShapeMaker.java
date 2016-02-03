package com.github.sladecek.maze.jmaze.shapes;

import java.util.HashMap;

import com.github.sladecek.maze.jmaze.topology.MazeRealization;


/**
 * 
 * Implements methods needed to implement generic shape maker (generic method makeShapes).
 *
 */
public class GenericShapeMaker  {

	public GenericShapeMaker() {
		shapes = new ShapeContainer();
		shape2id = new HashMap<IMazeShape, Integer>();
		room2floor = new HashMap<Integer, FloorShape>();		
	}	
	
	private ShapeContainer shapes;
	private HashMap<IMazeShape, Integer> shape2id;
	private HashMap<Integer, FloorShape> room2floor;
	
	public ShapeContainer makeShapes(MazeRealization realization, int startRoom, int targetRoom, int offsetXPercent, int offsetYPercent) {
		
		ShapeContainer result = new ShapeContainer();
		result.setPictureHeight(shapes.getPictureHeight());
		result.setPictureWidth(shapes.getPictureWidth());
		
		// copy only closed walls to the result
		for (IMazeShape s: shapes.getShapes()) {
			final boolean debug = false; // TODO
			if (debug || !shape2id.containsKey(s) || realization.isWallClosed(shape2id.get(s))) {
				result.add(s);
			}
		}
		
		// start/stop
		FloorShape startFloor = room2floor.get(startRoom);
		if (startFloor != null) {
			final MarkShape mark = startFloor.CreateMarkInThisRoom(IMazeShape.ShapeType.startRoom);
			mark.setOffsetXPercent(offsetXPercent);
			mark.setOffsetYPercent(offsetYPercent);
			result.add(mark);			
		}
		FloorShape targetFloor = room2floor.get(targetRoom);
		if (targetFloor != null) {
			final MarkShape mark = targetFloor.CreateMarkInThisRoom(IMazeShape.ShapeType.targetRoom);
			mark.setOffsetXPercent(offsetXPercent);
			mark.setOffsetYPercent(offsetYPercent);
			result.add(mark);
			
		}
		
		// solution
		for (int i: realization.getSolution()) {
			if (i == startRoom) {
				continue;
			}
			if (i == targetRoom) {
				continue;
			}
			final MarkShape mark = room2floor.get(i).CreateMarkInThisRoom(IMazeShape.ShapeType.solution);
			mark.setOffsetXPercent(offsetXPercent);
			mark.setOffsetYPercent(offsetYPercent);
			result.add(mark);
			
		}	
		return result;
	}

	public void addShape(IMazeShape floor) {
		shapes.add(floor);		
	}

	public void linkRoomToFloor(int r, FloorShape floor) {
		room2floor.put(r, floor);		
	}

	public void linkShapeToId(IMazeShape ws, int id) {
		shape2id.put(ws, id);		
	}

}