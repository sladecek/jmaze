package com.github.sladecek.maze.jmaze.print3dn;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D;
import com.github.sladecek.maze.jmaze.shapes.MarkType;
import com.github.sladecek.maze.jmaze.shapes.WallType;

import java.util.EnumMap;
import java.util.EnumSet;


/**
 * Represents a room in 3D maze.
 */
/* TODO tohle nebude 2D  */
public class Room3D implements IMazeShape2D {
    public Room3D(Point2D position, int roomId) {
        this.position = position;
        this.roomId = roomId;
        for (WallDirection wd: WallDirection.values()) {
            wallType.put(wd, WallType.noWall);
            wallId.put(wd, -1);
        }
    }

    public EnumMap<WallDirection, Integer> getWallId() {
        return wallId;
    }

    public void setWallId(WallDirection wd, int  wallId) {
        this.wallId.put(wd, wallId);
    }

    public EnumMap<WallDirection, WallType> getWallType() {
        return wallType;
    }

    public void setWallType(WallDirection wd, WallType wallType) {
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
        for(WallDirection wd: WallDirection.values()) {
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
    private EnumMap<WallDirection, Integer> wallId = new EnumMap<WallDirection, Integer>(WallDirection.class);
    private EnumMap<WallDirection, WallType> wallType = new EnumMap<WallDirection, WallType>(WallDirection.class);
    private int roomId;
    private MarkType markType = MarkType.none;
}
