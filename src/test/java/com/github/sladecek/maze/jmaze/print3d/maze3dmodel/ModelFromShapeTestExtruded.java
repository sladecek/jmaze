package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.print3d.*;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MFace;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.output.OpenScad3DPrinter;
import com.github.sladecek.maze.jmaze.print3d.output.StlMazePrinter;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static junit.framework.TestCase.assertFalse;
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


// 42?        assertEquals(28, model3d.getEdges().size());
// 30        assertEquals(16, model3d.getFaces().size());
        // 42?    assertEquals(14, model3d.getPoints().size());
        //    assertEquals(16, model3d.getBlocks().size());
    }

    @Test
    public void testBlocVertexCount() {
        printBlocks();
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
/* TODO smazat
    @Test
    public void testPointAltitudes() {
        for (MFace mFace : model3d.getFaces()) {
            if (mFace instanceof FloorFace) {
                FloorFace f = (FloorFace) mFace;
                Altitude alt = f.getAltitude();
                for (MEdge e : f.getEdges()) {
                    if (e.getP1() instanceof TelescopicPoint) {
                        TelescopicPoint p = (TelescopicPoint) e.getP1();

                        assert (p.areAltitudesDefined());
                        assert (p.getHighAltitude() >= p.getLowAltitude());
                        assert (p.getHighAltitude() == alt.getValue() || p.getLowAltitude() == alt.getValue());
                    }
                }
            }
        }
    }
*/
    @Test
    public void testBlockAltitudes() {
        printBlocks();
        assertEquals(1, countBlocksByCeilingAltitude(6, -1));
        assertEquals(4, countBlocksByCeilingAltitude(2, 2));
        assertEquals(2, countBlocksByCeilingAltitude(4, 1));
        assertEquals(7, countBlocksByCeilingAltitude(4, 2));
        assertEquals(2, countBlocksByCeilingAltitude(3, 2));
    }

    @Test
    public void testFaceEdgesHaveDifferentEndpoints() {
        for (MFace f: model3d.getFaces()) {
            for (MEdge e: f.getEdges()) {
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
    public void testOpenScadPrint() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OpenScad3DPrinter printer = new OpenScad3DPrinter();
        printer.printModel(model3d, stream);
        stream.close();
        String s = stream.toString();
        assertEquals(4507, s.length());
    }


    @Test
    public void testStlPrint() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        StlMazePrinter printer = new StlMazePrinter();
        printer.printModel(model3d, stream);
        stream.close();
        String s = stream.toString();
        assertEquals(11757, s.length());
    }

}
