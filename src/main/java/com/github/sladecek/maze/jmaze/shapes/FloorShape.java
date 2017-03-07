package com.github.sladecek.maze.jmaze.shapes;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;

/// Represents hole in the ground of the maze or the lack of thereof.
/// The user usually prints either holes or non-holes depending on output
/// type.
public final class FloorShape implements IMazeShape2D {

    public FloorShape(final Point2D center, boolean isHole, int offsetXPercent, int offsetYPercent) {
        super();
        this.center = center;

        this.isHole = isHole;
        LOG.log(Level.INFO, "FloorShape  center=" + center);
    }

    @Override
    public ShapeType getShapeType() {
        if (isHole) {
            return ShapeType.hole;
        } else {
            return ShapeType.nonHole;
        }
    }

    public int getY() {
        return center.getY();
    }

    public int getX() {
        return center.getX();
    }

    public boolean isHole() {
        return isHole;
    }

    public MarkShape createMarkInThisRoom(ShapeType type) {
        MarkShape ms = new MarkShape(type, getY(), getX());
        return ms;
    }

    @Override
    public void print2D(I2DDocument doc, IPrintStyle printStyle) {
    }

    @Override
    public String toString() {
        return "FloorShape [y=" + getY() + ", x=" + getX() + ", isHole=" + isHole + "]";
    }

    @Override
    public void applyRealization(MazeRealization mr) {

    }


    private Point2D center;
    private boolean isHole;
/**
     * Logger facility.
     */
    private static final Logger LOG = Logger.getLogger("maze.jmaze");

}
