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
    }

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


    @Override
    public void applyRealization(MazeRealization mr) {

        isHole = !mr.isWallClosed(roomId);

    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void print2D(I2DDocument doc, IPrintStyle printStyle) {
    }


    private Point2DInt position;
    private int roomId;
    private MarkType markType = MarkType.none;
    private boolean isHole;
}
