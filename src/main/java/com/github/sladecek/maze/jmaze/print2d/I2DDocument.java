package com.github.sladecek.maze.jmaze.print2d;
//REV1
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;


/**
 * Interface representing 2D documents (such as SVG document).
 */
public interface I2DDocument {

    int getCanvasWidth();

    int getCanvasHeight();

    void printLine(Point2DInt p1, Point2DInt p2, String style);

    void printArcSegment(Point2DInt p1, Point2DInt p2, String style);

    void printMark(Point2DInt center, String fill, int sizePercent);

    void printCircle(Point2DInt center, String fill, int perimeter, boolean isPerimeterAbsolute,
                     String style);


    ShapeContext getContext();
}