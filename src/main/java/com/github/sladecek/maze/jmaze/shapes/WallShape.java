package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;

public final class WallShape implements IMazeShape2D {


    public static WallShape newInnerWall(int wallId, Point2D p1, Point2D p2) {
        return new WallShape(wallId, WallType.innerWall, p1, p2, -1, -1);
    }

    public static WallShape newOuterWall(Point2D p1, Point2D p2) {
        return new WallShape(-1, WallType.outerWall, p1, p2, -1, -1);
    }

    public static WallShape newInnerWall(int wallId, Point2D p1, Point2D p2, int rightFaceId, int leftFaceId) {
        return new WallShape(wallId, WallType.innerWall, p1, p2, rightFaceId, leftFaceId);
    }

    public static WallShape newOuterWall(Point2D p1, Point2D p2, int rightFaceId, int leftFaceId) {
        return new WallShape(-1, WallType.outerWall, p1, p2, rightFaceId, leftFaceId);
    }

    public int getWallId() {
        return wallId;
    }

    protected WallShape(int wallId, WallType type, Point2D p1, Point2D p2, int rigthFaceId, int leftFaceId) {
        this.wallId = wallId;
        this.wallType = type;
        this.p1 = p1;
        this.p2 = p2;
        this.rigthFaceId = rigthFaceId;
        this.leftFaceId = leftFaceId;
    }

    public static void weldTwoOuterWalls(WallShape w1, WallShape w2, boolean swap) {
        w1.weld = new OuterWallWeld(w1, w2, swap);
        w2.weld = w1.weld;
    }


    @Override
    public void print2D(I2DDocument doc, IPrintStyle printStyle) {
        String style = "";

        switch (wallType) {
            case outerWall:
                style = "stroke:" + printStyle.getOuterWallColor().toSvg() + ";stroke-width:"
                        + printStyle.getOuterWallWidth();
                break;
            case innerWall:
                style = "stroke:" + printStyle.getInnerWallColor().toSvg() + ";stroke-width:"
                        + printStyle.getInnerWallWidth();
                break;

            case noWall:
                if (printStyle.isDebugPrintAllWalls()) {
                    style = "stroke:" + printStyle.getDebugWallColor().toSvg() + ";stroke-width:"
                            + printStyle.getInnerWallWidth();
                }
                break;
            default:
                break;

        }
        if (!style.isEmpty()) {
            if (doc.getContext().isPolarCoordinates() && p1.getY() == p2.getY()) {
                if (p1.getX() == 0 && p2.getX() == 0) {
                    doc.printCircle(new Point2D(0, 0), "none", p1.getY(), false, style);
                } else {
                    doc.printArcSegment(p1, p2, style);
                }
            } else {
                doc.printLine(p1, p2, style);
            }
        }
    }

    public int getX1() {
        return p1.getX();
    }

    public int getX2() {
        return p2.getX();
    }

    public int getY1() {
        return p1.getY();
    }

    public int getY2() {
        return p2.getY();
    }

    public int getRigthFaceId() {
        return rigthFaceId;
    }

    public int getLeftFaceId() {
        return leftFaceId;
    }

    @Override
    public String toString() {
        return "WallShape [ wallType=" + wallType + ", x1=" + getX1() + ", x2=" + getX2() + ", y1=" + getY1()
                + ", y2=" + getY2() + "]";
    }

    @Override
    public void applyRealization(MazeRealization mr) {
        if (wallType == WallType.innerWall) {
            if (!mr.isWallClosed(wallId)) {
                wallType = WallType.noWall;
            }
        }
    }

    public Point2D getP1() { return p1; }
    public Point2D getP2() { return p2; }


    private Point2D p1;
    private Point2D p2;

    public WallType getWallType() {
        return wallType;
    }

    private WallType wallType;
    private int wallId;
    private int rigthFaceId = -1;
    private int leftFaceId = -1;
    private OuterWallWeld weld;
}
