package com.github.sladecek.maze.jmaze.maze;

import java.util.HashMap;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.MarkShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;


/**
 * Base class for all mazes. 
 */
public class Maze extends GenericMazeTopology  {

	public Maze() {
	    shapes =  new ShapeContainer(null);
		shape2id = new HashMap<IMazeShape, Integer>();
		room2floor = new HashMap<Integer, FloorShape>();		
	}	
	
    public ShapeContainer applyRealization(MazeRealization realization) {
        ShapeContainer result = new ShapeContainer(context);
      
        // copy only closed walls to the result
        for (IMazeShape s: shapes.getShapes()) {
            final boolean debug = false; // TODO
            if (debug || !shape2id.containsKey(s) || realization.isWallClosed(shape2id.get(s))) {
                result.add(s);
            }
        }
        
        // start/stop
        FloorShape startFloor = room2floor.get(getStartRoom());
        if (startFloor != null) {
            final MarkShape mark = startFloor.CreateMarkInThisRoom(IMazeShape.ShapeType.startRoom);
            mark.setOffsetXPercent(context.getMarkOffsetXPercent());
            mark.setOffsetYPercent(context.getMarkOffsetYPercent());
            result.add(mark);           
        }
        FloorShape targetFloor = room2floor.get(getTargetRoom());
        if (targetFloor != null) {
            final MarkShape mark = targetFloor.CreateMarkInThisRoom(IMazeShape.ShapeType.targetRoom);
            mark.setOffsetXPercent(context.getMarkOffsetXPercent());
            mark.setOffsetYPercent(context.getMarkOffsetYPercent());
            result.add(mark);
            
        }
        
        // solution
        for (int i: realization.getSolution()) {
            if (i == getStartRoom()) {
                continue;
            }
            if (i == getTargetRoom()) {
                continue;
            }
            final MarkShape mark = room2floor.get(i).CreateMarkInThisRoom(IMazeShape.ShapeType.solution);
            mark.setOffsetXPercent(context.getMarkOffsetXPercent());
            mark.setOffsetYPercent(context.getMarkOffsetYPercent());
            result.add(mark);
            
        }   
        return result;

    }

    private ShapeContext context;
    
	private ShapeContainer shapes;
	private HashMap<IMazeShape, Integer> shape2id;
	private HashMap<Integer, FloorShape> room2floor;
	
	
	protected void addShape(IMazeShape floor) {
		shapes.add(floor);		
	}

	protected void linkRoomToFloor(int r, FloorShape floor) {
		room2floor.put(r, floor);		
	}

	protected void linkShapeToId(IMazeShape ws, int id) {
		shape2id.put(ws, id);		
	}

	
	protected IMazeShape getFloorFromRoom(int room) {
	    return room2floor.get(room);
	}
	
    public ShapeContext getContext() {
        return context;
    }

    protected void setContext(ShapeContext context) {
        this.context = context;
    }
	

}