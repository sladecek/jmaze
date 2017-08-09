package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.print3d.*;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.printstyle.PrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.Shapes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests ModelFromShapes class.
 */
public class ModelFromShapeTestFlat extends ModelFromShapesTestBase {


    @Before
    public void setUp() throws Exception {
        Shapes sc = setUpInput();
        model3d = compute3dModel(sc);
        collectOutputs(model3d);
    }


    protected Model3d compute3dModel(Shapes sc) {
        IPrintStyle colors = new PrintStyle();
        IMaze3DMapper mapper = new PlanarMapper();
        return ModelFromShapes.makeWithoutExtrusionForUnitTesting(sc, mapper, colors, 0.4);
    }


    @Test
    public void testPillars() throws Exception {
        assertEquals(6, pillars.size());
        checkPillar(pillars.get(0), 2, new double[][]{{50.2, 10.2}, {49.8, 9.8}});
        checkPillar(pillars.get(1), 2, new double[][]{{60.0, 9.8}, {60.2, 10.2}, {59.8, 10.2}});
        checkPillar(pillars.get(2), 2, new double[][]{{70.2, 9.8}, {69.8, 10.2}});
        checkPillar(pillars.get(3), 2, new double[][]{{50.2, 19.8}, {49.8, 20.2}});
        checkPillar(pillars.get(4), 2, new double[][]{{60.2, 19.8}, {60.0, 20.2}, {59.8, 19.8}});
        checkPillar(pillars.get(5), 2, new double[][]{{70.2, 20.2}, {69.8, 19.8}});
    }

    @Test
    public void testRooms() {
        assertEquals(3, rooms.size());
        chechRoom(rooms.get(0))
                .checkFloorId(-1)
                .checkCornerCount(6)
//               .printCorners()
                .checkCorner(0, -1, ws50, ws01, walls.get(2), walls.get(0))
                .checkCorner(1, -1, ws01, ws12, walls.get(0), walls.get(1))
                .checkCorner(2, -1, ws12, ws23, walls.get(1), walls.get(4))
                .checkCorner(4, -1, ws34, ws45, walls.get(6), walls.get(5))
                .checkCorner(3, -1, ws45, ws50, walls.get(5), walls.get(2))
                .checkCorner(5, -1, ws23, ws34, walls.get(4), walls.get(6))
                .checkAltitude(-1)
                .checkEdgeCount(6)

        ;

        chechRoom(rooms.get(1))
                .checkFloorId(0)
                .checkCornerCount(4)
                //   .printCorners()
                .checkCorner(0, 0, ws01, ws50, walls.get(0), walls.get(2))
                .checkCorner(1, 0, ws14, ws01, walls.get(3), walls.get(0))
                .checkCorner(2, 0, ws50, ws45, walls.get(2), walls.get(5))
                .checkCorner(3, 0, ws45, ws14, walls.get(5), walls.get(3))
                .checkAltitude(1)
                .checkEdgeCount(4)
        ;

        chechRoom(rooms.get(2))
                .checkFloorId(1)
                .checkCornerCount(4)
                //   .printCorners()
                .checkCorner(0, 1, ws12, ws14, walls.get(1), walls.get(3))
                .checkCorner(1, 1, ws23, ws12, walls.get(4), walls.get(1))
                .checkCorner(2, 1, ws14, ws34, walls.get(3), walls.get(6))
                .checkCorner(3, 1, ws34, ws23, walls.get(6), walls.get(4))
                .checkAltitude(1)
                .checkEdgeCount(4)

        ;

    }

    @Test
    public void testWalls() {
        //printWalls();
        //printCorners();
        assertEquals(7, walls.size());
        checkWall(walls.get(0), ws01, 2, new double[]{49.8, 9.8, 50.2, 10.2, 59.8, 10.2, 60.0, 9.8});
        checkWall(walls.get(1), ws12, 2, new double[]{60.0, 9.8, 60.2, 10.2, 69.8, 10.2, 70.2, 9.8});
        checkWall(walls.get(2), ws50, 2, new double[]{49.8, 20.2, 50.2, 19.8, 50.2, 10.2, 49.8, 9.8});
        checkWall(walls.get(3), ws14, 2, new double[]{60.2, 10.2, 59.8, 10.2, 59.8, 19.8, 60.2, 19.8});
        checkWall(walls.get(4), ws23, 2, new double[]{70.2, 9.8, 69.8, 10.2, 69.8, 19.8, 70.2, 20.2});
        checkWall(walls.get(5), ws45, 2, new double[]{60.0, 20.2, 59.8, 19.8, 50.2, 19.8, 49.8, 20.2});
        checkWall(walls.get(6), ws34, 2, new double[]{70.2, 20.2, 69.8, 19.8, 60.2, 19.8, 60.0, 20.2});
    }


    @Test
    public void testElementCounts() {

        //printBlocks();
        printEdges();
        //printFaces();
        //printPoints();
        assertEquals(0, model3d.getBlocks().size());

        assertEquals(28, model3d.getEdges().size());
        assertEquals(16, model3d.getFaces().size());
        assertEquals(14, model3d.getPoints().size());
    }

    @Test
    public void checkAllEdgesOfFacePointToFace() {
        model3d.getFaces().forEach((f) -> checkEdgesOfFacePointToTheFace(f));
    }

    @Test
    public void checkAllEdgesAreCyclic() {
        model3d.getFaces().forEach((f) -> checkEdgesAreCyclic(f));
    }


}
