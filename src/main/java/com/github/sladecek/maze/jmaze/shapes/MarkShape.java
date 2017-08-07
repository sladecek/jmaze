package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.generator.MazePick;
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;


import java.util.logging.Level;
import java.util.logging.Logger;

public final class MarkShape implements IMazeShape2D {



    private MarkType markType;
    private int roomId;

    public MarkShape(int roomId, Point2DInt position) {
        this.roomId = roomId;
        this.center = position;
        this.markType = MarkType.none;
        LOG.log(Level.INFO, "MarkShape  " + markType + " center=" + center);
    }

    public int getX() {
        return center.getX();
    }

    public int getY() {
        return center.getY();
    }

    @Override
    public String toString() {
        return "MarkShape [markType=" + markType + ", x=" + getX() + ", y=" + getY() + "]";
    }

    @Override
    public void print2D(I2DDocument doc, IPrintStyle printStyle) {
        switch (markType) {
            case startRoom:
                doc.printMark(center, printStyle.getStartMarkColor().toSvg(), printStyle.getStartTargetMarkWidth());
                break;
            case targetRoom:
                doc.printMark(center, printStyle.getTargetMarkColor().toSvg(), printStyle.getStartTargetMarkWidth());
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
    public void applyRealization(MazePick mr) {
       markType = MarkType.markRoomFromRealization(roomId, mr);

    }


    private Point2DInt center;
/*
    private int offsetXPercent = 50;
    private int offsetYPercent = 50;
*/
    /**
     * Logger facility.
     */
    private static final Logger LOG = Logger.getLogger("maze");

}
