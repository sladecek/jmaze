package com.github.sladecek.maze.jmaze.print2d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;

public interface I2DDocument {

    void printLine(Point2D p1, Point2D p2, String style, boolean center);

    void printArcSegment(Point2D p1, Point2D p2, String style);

    void printMark(Point2D center, String fill, int sizePercent,
            int offsXPercent, int offsYPercent);

    void printCircle(Point2D center, String fill, int offsXPercent,
            int offsYPercent, int perimeter, boolean isPerimeterAbsolute,
            String style);

    int getCanvasWidth();

    int getCanvasHeight();

    ShapeContext getContext();

}