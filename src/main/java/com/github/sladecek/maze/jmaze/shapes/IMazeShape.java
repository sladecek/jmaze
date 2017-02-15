package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;

/**
 * Shapes for maze drawing.
 */
public interface IMazeShape {
    enum ShapeType {
        innerWall, outerWall, hole, nonHole, solution, auxiliaryWall, startRoom, targetRoom
    }

    ;

    ShapeType getShapeType();

    void print2D(I2DDocument doc, IPrintStyle printStyle);

}
