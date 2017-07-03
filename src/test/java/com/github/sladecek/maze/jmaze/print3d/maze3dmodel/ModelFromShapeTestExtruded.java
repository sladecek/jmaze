package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.print3d.*;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.output.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests ModelFromShapes class.
 */
public class ModelFromShapeTestExtruded extends ModelFromShapesTestBase {

    @Before
    public void setUp() throws Exception {
        ShapeContainer sc = setUpInput();
        model3d = compute3dModel(sc);
        collectOutputs(model3d);
    }

    protected Model3d compute3dModel(ShapeContainer sc) {
        Maze3DSizes sizes = new Maze3DSizes();
        sizes.setCellSizeInmm(2);
        IPrintStyle colors = new DefaultPrintStyle();
        IMaze3DMapper mapper = new PlanarMapper();
        return ModelFromShapes.make(sc, mapper, sizes, colors);
    }

    @Test
    public void testElementCounts() {

        printBlocks();
        printEdges();
        printFaces();
        printPoints();
// 15? 16?        assertEquals(0, model3d.getBlocks().size());

// 42?        assertEquals(28, model3d.getEdges().size());
// 30?        assertEquals(16, model3d.getFaces().size());
        // 42?    assertEquals(14, model3d.getPoints().size());
    }


    @Test
    public void testOpenScadPrint() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OpenScad3DPrinter printer = new OpenScad3DPrinter();
        printer.printModel(model3d, stream);
        stream.close();
        String s = stream.toString();
        assertEquals(834, s.length());
    }

}
