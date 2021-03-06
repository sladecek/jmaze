package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.maze.MazeGenerationException;
import com.github.sladecek.maze.jmaze.print.PrintStyle;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.ModelFromShapes;
import com.github.sladecek.maze.jmaze.print3d.PlanarMapper;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MFace;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.output.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.output.StlMazePrinter;
import com.github.sladecek.maze.jmaze.shapes.Shapes;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Tests ModelFromShapes class - the case with extrusion.
 */
public class ModelFromShapeTestExtruded extends ModelFromShapesTestBase {

    @Before
    public void setUp() throws Exception {
        Shapes sc = setUpInput();
        model3d = compute3dModel(sc);
        collectOutputs(model3d);
    }

    private Model3d compute3dModel(Shapes sc) {
        PrintStyle colors = new PrintStyle();
        IMaze3DMapper mapper = new PlanarMapper();
        return ModelFromShapes.make(sc, mapper, colors, 2);
    }

    @Test
    public void testElementCounts() {
//        printBlocks();
//        printEdges();
//        printFaces();
//        printPoints();

        assertEquals(80, model3d.getEdges().size());
        assertEquals(42, model3d.getFaces().size());
        assertEquals(40, model3d.getPoints().size());
        assertEquals(16, model3d.getBlocks().size());
    }

    @Test
    public void testBlocVertexCount() {
        //printBlocks();
        assertEquals(1, countBlocksByVertexCount(6));
        assertEquals(9, countBlocksByVertexCount(4));
        assertEquals(2, countBlocksByVertexCount(3));
        assertEquals(4, countBlocksByVertexCount(2));
    }

    private long countBlocksByVertexCount(int vertexCount) {
        return model3d.getBlocks().stream()
                .filter((b) -> b.getCeilingPoints().size() == vertexCount)
                .filter((b) -> b.getGroundPoints().size() == vertexCount)
                .count();
    }

    @Test
    public void testBlockAltitudes() {
        //printBlocks();
        assertEquals(1, countBlocksByCeilingAltitude(6, -1));
        assertEquals(4, countBlocksByCeilingAltitude(2, 2));
        assertEquals(2, countBlocksByCeilingAltitude(4, 1));
        assertEquals(7, countBlocksByCeilingAltitude(4, 2));
        assertEquals(2, countBlocksByCeilingAltitude(3, 2));
    }

    @Test
    public void testFaceEdgesHaveDifferentEndpoints() {
        for (MFace f : model3d.getFaces()) {
            for (MEdge e : f.getEdges()) {
                assertFalse(e.getP1() == e.getP2());
            }
        }
    }

    private long countBlocksByCeilingAltitude(int vertexCount, int expectedZ) {
        return model3d.getBlocks().stream()
                .filter((b) -> b.getCeilingPoints().size() == vertexCount)
                .filter((b) -> b.getGroundPoints().size() == vertexCount)
                .filter((b) -> b.getCeilingPoints().stream()
                        .filter((p) -> p.getZ() == expectedZ).count() == vertexCount)
                .count();
    }

    @Test
    public void testOpenScadPrint() throws IOException, MazeGenerationException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OpenScad3DPrinter printer = new OpenScad3DPrinter(model3d);
        printer.print(stream);
        stream.close();
        String s = stream.toString();
        assertEquals(4507, s.length());
    }


    @Test
    public void testStlPrint() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        StlMazePrinter printer = new StlMazePrinter(model3d);
        printer.print(stream);
        stream.close();
        String s = stream.toString();
        assertEquals(11757, s.length());
    }

}
