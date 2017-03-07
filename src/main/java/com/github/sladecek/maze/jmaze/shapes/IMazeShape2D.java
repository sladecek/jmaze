package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.print2d.I2DDocument;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;

/**
 *
 */
public interface IMazeShape2D extends IMazeShape {

    ;

    // WallType getShapeType();

    void print2D(I2DDocument doc, IPrintStyle printStyle);

}
