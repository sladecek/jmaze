package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class MarkShape implements IMazeShape {

    public MarkShape(ShapeType type, int y, int x) {
        this.shapeType = type;
        this.center = new Point2D(x, y);
        LOG.log(Level.INFO, "MarkShape  " + type + " center=" + center);
    }

    public ShapeType getShapeType() {
        return this.shapeType;
    }

    public int getX() {
        return center.getX();
    }

    public int getY() {
        return center.getY();
    }

    @Override
    public String toString() {
        return "MarkShape [shapeType=" + shapeType + ", x=" + getX() + ", y=" + getY() + "]";
    }

    public void setOffsetXPercent(int offsetXPercent) {
        this.offsetXPercent = offsetXPercent;
    }

    public void setOffsetYPercent(int offsetYPercent) {
        this.offsetYPercent = offsetYPercent;
    }

    @Override
    public void print2D(I2DDocument doc, IPrintStyle printStyle) {

        switch (shapeType) {
            case startRoom:
                doc.printMark(center, printStyle.getStartMarkColor().toSvg(), printStyle.getStartTargetMarkWidth(),
                        offsetXPercent, offsetYPercent);
                break;
            case targetRoom:
                doc.printMark(center, printStyle.getTargetMarkColor().toSvg(), printStyle.getStartTargetMarkWidth(),
                        offsetXPercent, offsetYPercent);
                break;
            case solution:
                doc.printMark(center, printStyle.getSolutionMarkColor().toSvg(), printStyle.getSolutionMarkWidth(),
                        offsetXPercent, offsetYPercent);
                break;
            default:
                break;
        }
    }

    private ShapeType shapeType;

    private Point2D center;

    private int offsetXPercent = 50;
    private int offsetYPercent = 50;

    /**
     * Logger facility.
     */
    private static final Logger LOG = Logger.getLogger("maze.jmaze");

}
