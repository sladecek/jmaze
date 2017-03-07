package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.shapes.*;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D.ShapeType;

import java.util.HashMap;

/**
 * Base class for all mazes.
 */
public class Maze extends GenericMazeStructure {

    public Maze() {
        shapes = new ShapeContainer(null);
        shape2id = new HashMap<IMazeShape2D, Integer>();
        room2floor = new HashMap<Integer, FloorShape>();
    }

    public ShapeContainer applyRealization(MazeRealization realization) {
        ShapeContainer result = new ShapeContainer(context);

        // copy only closed walls to the result
        for (IMazeShape2D s : shapes.getShapes()) {
            if (!shape2id.containsKey(s) || realization.isWallClosed(shape2id.get(s))) {
                result.add(s);
            } else if (debug) {
                WallShape ws = (WallShape) s;
                if (ws != null) {
                    WallShape nws = new WallShape(ShapeType.auxiliaryWall, ws.getY1(), ws.getX1(), ws.getY2(), ws.getX2());
                    result.add(nws);
                }
            }
        }

        // start/stop
        FloorShape startFloor = room2floor.get(getStartRoom());
        if (startFloor != null) {
            final MarkShape mark = startFloor.createMarkInThisRoom(IMazeShape2D.ShapeType.startRoom);
            result.add(mark);
        }

        FloorShape targetFloor = room2floor.get(getTargetRoom());
        if (targetFloor != null) {
            final MarkShape mark = targetFloor.createMarkInThisRoom(IMazeShape2D.ShapeType.targetRoom);
            result.add(mark);
        }

        // solution
        for (int i : realization.getSolution()) {
            if (i == getStartRoom()) {
                continue;
            }
            if (i == getTargetRoom()) {
                continue;
            }
            final MarkShape mark = room2floor.get(i).createMarkInThisRoom(IMazeShape2D.ShapeType.solution);
            result.add(mark);
        }
       return result;

    }

    public ShapeContainer getShapes() {
        return shapes;
    }

    protected void addShape(IMazeShape2D shape) {
        shapes.add(shape);
    }

    protected void linkRoomToFloor(int r, FloorShape floor) {
        room2floor.put(r, floor);
    }

    protected void linkShapeToId(IMazeShape2D shape, int id) {
        shape2id.put(shape, id);
    }

    protected IMazeShape2D getFloorFromRoom(int room) {
        return room2floor.get(room);
    }

    protected int getIdFromShape(IMazeShape2D shape) {
        return shape2id.get(shape);
    }

    public ShapeContext getContext() {
        return context;
    }

    protected void setContext(ShapeContext context) {
        this.context = context;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    private ShapeContext context;
    private ShapeContainer shapes;
    private HashMap<IMazeShape2D, Integer> shape2id;
    private HashMap<Integer, FloorShape> room2floor;
    private boolean debug;


}