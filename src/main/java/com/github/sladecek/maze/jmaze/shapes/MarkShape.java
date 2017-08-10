package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.generator.MazePath;
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.PrintStyle;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class MarkShape implements IPrintableMazeShape2D {



    public MarkShape(int roomId, Point2DInt position) {
        this.roomId = roomId;
        this.center = position;
        this.markType = MarkType.none;
        LOG.log(Level.FINE, "MarkShape  " + markType + " center=" + center);
    }

    public int getX() {
        return center.getX();
    }

    public int getY() {
        return center.getY();
    }

    public void setMarkType(MarkType markType) {
        this.markType = markType;
    }

    @Override
    public String toString() {
        return "MarkShape [markType=" + markType + ", x=" + getX() + ", y=" + getY() + "]";
    }

    @Override
    public void print2D(I2DDocument doc, PrintStyle printStyle) {
        switch (markType) {
            case startRoom:
                doc.printMark(center, printStyle.getStartMarkColor().toSvg(), printStyle.getStartMarkWidth());
                break;
            case targetRoom:
                doc.printMark(center, printStyle.getTargetMarkColor().toSvg(), printStyle.getTargetMarkWidth());
                break;
            case solution:
                if (printStyle.isPrintSolution()) {
                    doc.printMark(center, printStyle.getSolutionMarkColor().toSvg(), printStyle.getSolutionMarkWidth());
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void applyPath(MazePath mr) {
        if (mr.getStartRoom() == roomId) {
            markType = MarkType.startRoom;
        } else if (mr.getTargetRoom() == roomId) {
            markType = MarkType.targetRoom;
        } else if (mr.getSolution().contains(roomId)) {
            markType = MarkType.solution;
        } else {
            markType = MarkType.none;
        }
    }


    private MarkType markType;
    private final int roomId;

    private final Point2DInt center;

    public MarkType getMarkType() {
        return markType;
    }

    /**
     * Logger facility.
     */
    private static final Logger LOG = Logger.getLogger("maze");

}
