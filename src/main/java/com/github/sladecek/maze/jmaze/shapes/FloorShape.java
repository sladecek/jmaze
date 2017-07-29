package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;

import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;


/**
 * Floor in 3D maze.
 */
/* TODO tohle nebude 2D  */
public class FloorShape implements IMazeShape2D {
    public FloorShape(int roomId, Point2DInt position) {
        this.position = position;
        this.roomId = roomId;
        this.isHole = false;
/*        for (Direction wd: Direction.values()) {
            wallType.put(wd, WallType.innerWall);
            wallId.put(wd, -1);
        }
 */
    }

    /*
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
    */
    public Point2DInt getPosition() {
        return position;
    }


    public int getRoomId() {
        return roomId;
    }

    public MarkType getMarkType() {
        return markType;
    }

    public boolean isHole() {
        return isHole;
    }

    /* TODO implementovat */
    @Override
    public void applyRealization(MazeRealization mr) {
        markType = MarkType.markRoomFromRealization(roomId, mr);
  /*      for(Direction wd: Direction.values()) {
           int id = wallId.get(wd);
           if (id >= 0 && wallType.get(wd) != WallType.outerWall) {
               if (mr.isWallClosed(id)) {
                   wallType.put(wd, WallType.innerWall);
               } else {
                   wallType.put(wd, WallType.noWall);

               }
           }
        }
    */

        // TODO isHole = !mr.isWallClosed(roomId);

    }

    /* TODO odstranit */
    @Override
    public void print2D(I2DDocument doc, IPrintStyle printStyle) {

    }

    private Point2DInt position;
    /*
    private EnumMap<Direction, Integer> wallId = new EnumMap<Direction, Integer>(Direction.class);
    private EnumMap<Direction, WallType> wallType = new EnumMap<Direction, WallType>(Direction.class);
    */
    private int roomId;
    private MarkType markType = MarkType.none;
    private boolean isHole;
}
