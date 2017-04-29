package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.*;
import org.junit.Test;
import org.springframework.ui.Model;

import static org.junit.Assert.*;

/**
 * Tests ModelFromShapes class.
 */
public class ModelFromShapesTest {
    //@Test
    public void make() throws Exception {


        ShapeContainer sc = new ShapeContainer(new ShapeContext(false, 100, 100));
        sc.add(new FloorShape(0, new Point2D(55, 15)));
        sc.add(new FloorShape(1, new Point2D(65, 15)));

        Point2D p0 = new Point2D(50, 10);
        Point2D p1 = new Point2D(60, 10);
        Point2D p2 = new Point2D(70, 10);
        Point2D p3 = new Point2D(70, 20);
        Point2D p4 = new Point2D(60, 20);
        Point2D p5 = new Point2D(50, 20);


        sc.add(WallShape.newOuterWall(p0, p1, 0, -1));
        sc.add(WallShape.newOuterWall(p1, p2, 1, -1));
        sc.add(WallShape.newOuterWall(p2, p3, 1, -1));
        sc.add(WallShape.newOuterWall(p3, p4, 1, -1));
        sc.add(WallShape.newOuterWall(p4, p5, 0, -1));
        sc.add(WallShape.newOuterWall(p5, p0, 0, -1));

        sc.add(WallShape.newInnerWall(3, p1, p4, 0, 1));

        Maze3DSizes sizes = new Maze3DSizes();
        sizes.setCellSizeInmm(2);

        IPrintStyle colors = new DefaultPrintStyle();

        IMaze3DMapper mapper = new PlanarMapper();
        Model3d r = ModelFromShapes.make(sc, mapper, sizes, colors);

        assertEquals(15, r.getBlocks().size());
        assertEquals(0, r.getEdges());
        assertEquals(0, r.getFaces());
        assertEquals(0, r.getPoints());

    }

}