package com.github.sladecek.maze.jmaze.print3dn;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape2D;

import java.util.EnumSet;


/**
 * Represents a room in 3D maze.
 */
/* TODO tohle nebude 2D  */
public class Room3D implements IMazeShape2D {
    public Room3D(Point2D position) {
        this.position = position;
        this.hasWall = EnumSet.noneOf(WallDirection.class);
    }

    public Point2D getPosition() {
        return position;
    }

    public EnumSet<WallDirection> getHasWall() {
        return hasWall;
    }

    public void setWall(WallDirection wd) {
        hasWall.add(wd);
    }

    /* TODO implementovat */
    @Override
    public void applyRealization(MazeRealization mr) {

    }

    /* TODO odstranit */
    @Override
    public ShapeType getShapeType() {
        return null;
    }

    /* TODO odstranit */
    @Override
    public void print2D(I2DDocument doc, IPrintStyle printStyle) {

    }

    private Point2D position;
    private EnumSet<WallDirection> hasWall;
}
