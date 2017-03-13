package com.github.sladecek.maze.jmaze.print3dn;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.geometry.Direction;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D;
import com.github.sladecek.maze.jmaze.shapes.MarkType;
import com.github.sladecek.maze.jmaze.shapes.WallType;

import java.util.EnumMap;


/**
 * Represents a room in 3D maze.
 */
/* TODO tohle nebude 2D  */
public class Room3DShape implements IMazeShape2D {
    public Room3DShape(int roomId, Point2D position) {
        this.position = position;
        this.roomId = roomId;
        for (Direction wd: Direction.values()) {
            wallType.put(wd, WallType.innerWall);
            wallId.put(wd, -1);
        }
    }

    public EnumMap<Direction, Integer> getWallId() {
        return wallId;
    }

    public void setWallId(Direction wd, int  wallId) {

        this.wallId.put(wd, wallId);
    }

    public WallType getWallType(Direction wd) {
        return wallType.get(wd);
    }

    public void setWallType(Direction wd, WallType wallType) {
        this.wallType.put(wd, wallType);
    }

    public Point2D getPosition() {
        return position;
    }


    public int getRoomId() {
        return roomId;
    }

    public MarkType getMarkType() {
        return markType;
    }

    /* TODO implementovat */
    @Override
    public void applyRealization(MazeRealization mr) {
        markType = MarkType.markRoomFromRealization(roomId, mr);
        for(Direction wd: Direction.values()) {
           int id = wallId.get(wd);
           if (id >= 0 && wallType.get(wd) != WallType.outerWall) {
               if (mr.isWallClosed(id)) {
                   wallType.put(wd, WallType.innerWall);
               } else {
                   wallType.put(wd, WallType.noWall);
               }
           }
        }
    }

    /* TODO odstranit */
    @Override
    public void print2D(I2DDocument doc, IPrintStyle printStyle) {

    }

    private Point2D position;
    private EnumMap<Direction, Integer> wallId = new EnumMap<Direction, Integer>(Direction.class);
    private EnumMap<Direction, WallType> wallType = new EnumMap<Direction, WallType>(Direction.class);
    private int roomId;
    private MarkType markType = MarkType.none;
}
