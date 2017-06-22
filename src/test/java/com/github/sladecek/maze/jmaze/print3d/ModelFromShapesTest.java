package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.*;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.*;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static org.junit.Assert.*;

/**
 * Tests ModelFromShapes class.
 */
public class ModelFromShapesTest {


    @Before
    public void setUp() throws Exception {
        ShapeContainer sc = setUpInput();
        model3d = compute3dModel(sc);
        collectOutputs(model3d);
    }

    private ShapeContainer setUpInput() {
        ShapeContainer sc = new ShapeContainer(new ShapeContext(false, 100, 100));
        sc.add(new FloorShape(0, new Point2D(55, 15)));
        sc.add(new FloorShape(1, new Point2D(65, 15)));

        Point2D p0 = new Point2D(50, 10);
        Point2D p1 = new Point2D(60, 10);
        Point2D p2 = new Point2D(70, 10);
        Point2D p3 = new Point2D(70, 20);
        Point2D p4 = new Point2D(60, 20);
        Point2D p5 = new Point2D(50, 20);

        ws01 = WallShape.newOuterWall(p0, p1, 0, -1);
        sc.add(ws01);

        ws12 = WallShape.newOuterWall(p1, p2, 1, -1);
        sc.add(ws12);

        ws23 = WallShape.newOuterWall(p2, p3, 1, -1);
        sc.add(ws23);

        ws34 = WallShape.newOuterWall(p3, p4, 1, -1);
        sc.add(ws34);

        ws45 = WallShape.newOuterWall(p4, p5, 0, -1);
        sc.add(ws45);

        ws50 = WallShape.newOuterWall(p5, p0, 0, -1);
        sc.add(ws50);

        ws14 = WallShape.newInnerWall(3, p1, p4, 0, 1);
        sc.add(ws14);
        return sc;
    }

    private Model3d compute3dModel(ShapeContainer sc) {
        Maze3DSizes sizes = new Maze3DSizes();
        sizes.setCellSizeInmm(2);
        IPrintStyle colors = new DefaultPrintStyle();
        IMaze3DMapper mapper = new PlanarMapper();
        return ModelFromShapes.makeWithoutExtrusionForUnitTesting(sc, mapper, sizes, colors);
    }

    private void collectOutputs(Model3d r) {
        pillars = r.getFaces()
                .stream()
                .filter((f) -> (f instanceof MPillar))
                .map((f) -> (MPillar) f)
                .sorted(Comparator
                        .comparingInt((MPillar po) -> po.getCenter().getY())
                        .thenComparingInt((MPillar po) -> po.getCenter().getX()))
                .collect(Collectors.toList());

        walls = r.getFaces()
                .stream()
                .filter((f) -> (f instanceof MWall))
                .map((f) -> (MWall) f)
                .sorted(Comparator
                        .comparingDouble((MWall wa) -> wa.getKeyForSortingInTests()))
                .collect(Collectors.toList());

        rooms = r.getFaces()
                .stream()
                .filter((f) -> (f instanceof MRoom))
                .map((f) -> (MRoom) f)
                .sorted(Comparator
                        .comparingInt((MRoom ro) -> ro.getFloorId()))
                .collect(Collectors.toList());

        corners = rooms.stream()
                .flatMap((ro) -> ro.getCorners().stream())
                .collect(Collectors.toList());
    }


    @Test
    public void testPillars() throws Exception {
        assertEquals(6, pillars.size());
        checkPillar(pillars.get(0), new double[][]{{50.2, 10.2}, {49.8, 9.8}});
        checkPillar(pillars.get(1), new double[][]{{60.0, 9.8}, {60.2, 10.2}, {59.8, 10.2}});
        checkPillar(pillars.get(2), new double[][]{{70.2, 9.8}, {69.8, 10.2}});
        checkPillar(pillars.get(3), new double[][]{{50.2, 19.8}, {49.8, 20.2}});
        checkPillar(pillars.get(4), new double[][]{{60.2, 19.8}, {60.0, 20.2}, {59.8, 19.8}});
        checkPillar(pillars.get(5), new double[][]{{70.2, 20.2}, {69.8, 19.8}});
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


        ;

        chechRoom(rooms.get(1))
                .checkFloorId(0)
                .checkCornerCount(4)
                //   .printCorners()
                .checkCorner(0, 0, ws01, ws50, walls.get(0), walls.get(2))
                .checkCorner(1, 0, ws14, ws01, walls.get(3), walls.get(0))
                .checkCorner(2, 0, ws50, ws45, walls.get(2), walls.get(5))
                .checkCorner(3, 0, ws45, ws14, walls.get(5), walls.get(3))
        ;

        chechRoom(rooms.get(2))
                .checkFloorId(1)
                .checkCornerCount(4)
                //   .printCorners()
                .checkCorner(0, 1, ws12, ws14, walls.get(1), walls.get(3))
                .checkCorner(1, 1, ws23, ws12, walls.get(4), walls.get(1))
                .checkCorner(2, 1, ws14, ws34, walls.get(3), walls.get(6))
                .checkCorner(3, 1, ws34, ws23, walls.get(6), walls.get(4))

        ;

    }

    @Test
    public void testWalls() {
        //printWalls();
        //printCorners();
        assertEquals(7, walls.size());
        checkWall(walls.get(0), ws01, new double[]{49.8, 9.8, 50.2, 10.2, 59.8, 10.2, 60.0, 9.8});
        checkWall(walls.get(1), ws12, new double[]{60.0, 9.8, 60.2, 10.2, 69.8, 10.2, 70.2, 9.8});
        checkWall(walls.get(2), ws50, new double[]{49.8, 20.2, 50.2, 19.8, 50.2, 10.2, 49.8, 9.8});
        checkWall(walls.get(3), ws14, new double[]{60.2, 10.2, 59.8, 10.2, 59.8, 19.8, 60.2, 19.8});
        checkWall(walls.get(4), ws23, new double[]{70.2, 9.8, 69.8, 10.2, 69.8, 19.8, 70.2, 20.2});
        checkWall(walls.get(5), ws45, new double[]{60.0, 20.2, 59.8, 19.8, 50.2, 19.8, 49.8, 20.2});
        checkWall(walls.get(6), ws34, new double[]{70.2, 20.2, 69.8, 19.8, 60.2, 19.8, 60.0, 20.2});
    }



/*


obecna kontrola ze edges of MFace jsou cyklicke, cyklus vycerpava vsechno a jedna ze stran (leve, prava) ma
testovanou mface jako levou pravou

*/

    @Test
    public void testElementCounts() {

        printBlocks();
        printEdges();
        printFaces();
        printPoints();
        assertEquals(0, model3d.getBlocks().size());

        assertEquals(28, model3d.getEdges().size());
        assertEquals(16, model3d.getFaces().size());
        assertEquals(14, model3d.getPoints().size());
    }

    private void checkWall(MWall w, WallShape ws, double[] expected) {

        double[] points = new double[8];
        ProjectedPoint pr11 = ((ProjectedPoint) w.getE1().getP1());
        points[0] = pr11.getPlanarX();
        points[1] = pr11.getPlanarY();
        ProjectedPoint pr12 = ((ProjectedPoint) w.getE1().getP2());
        points[2] = pr12.getPlanarX();
        points[3] = pr12.getPlanarY();
        ProjectedPoint pr31 = ((ProjectedPoint) w.getE3().getP1());
        points[4] = pr31.getPlanarX();
        points[5] = pr31.getPlanarY();
        ProjectedPoint pr32 = ((ProjectedPoint) w.getE3().getP2());
        points[6] = pr32.getPlanarX();
        points[7] = pr32.getPlanarY();
        // printPointArray(points);

        for (int i = 0; i < 8; i++) {
            assertEquals(expected[i], points[i], epsilon);
        }

        // check that MWall has been constructed from the provided wall shape
        final Set<WallEnd> s1 = corners
                .stream()
                .map((c) -> c.getWallEnd1())
                .filter((we) -> we.getMWall() == w)
                .collect(Collectors.toSet());
        assertEquals(2, s1.size());
        for (WallEnd we : s1) {
            assertEquals(ws, we.getWallShape());
        }

        final Set<WallEnd> s2 = corners
                .stream()
                .map((c) -> c.getWallEnd2())
                .filter((we) -> we.getMWall() == w)
                .collect(Collectors.toSet());
        assertEquals(2, s2.size());
        for (WallEnd we : s2) {
            assertEquals(ws, we.getWallShape());
        }

        // each wall has two wall ends linked to 4 corners
        Set<WallEnd> allWallEnds = new TreeSet<>();
        allWallEnds.addAll(s1);
        allWallEnds.addAll(s2);
        assertEquals(2, allWallEnds.size());
    }

    private void printPointArray(double[] points) {
        for (double d : points) {
            System.out.print(d + ", ");
        }
        System.out.println();
    }


    private void checkPillar(MPillar p, double[][] expectedIntersections) {
        assertEquals(FloorFace.CEILING_ALTITUDE, p.getAltitude());
        assertEquals(expectedIntersections.length, p.getIntersections().size());
        for (int i = 0; i < expectedIntersections.length; i++) {
            final ProjectedPoint intersect = (ProjectedPoint) p.getIntersections().get(i);
            assertEquals(expectedIntersections[i][0], intersect.getPlanarX(), epsilon);
            assertEquals(expectedIntersections[i][1], intersect.getPlanarY(), epsilon);
        }
    }

    private RoomAuditor chechRoom(MRoom r) {
        return new RoomAuditor(r);
    }

    class RoomAuditor {
        public RoomAuditor(MRoom r) {
            this.r = r;
        }

        public RoomAuditor checkFloorId(int floorId) {
            assertEquals(floorId, r.getFloorId());
            return this;
        }

        public RoomAuditor checkCornerCount(int cnt) {
            assertEquals(cnt, r.getCorners().size());
            return this;
        }

        public RoomAuditor checkCorner(int i, int floorId, WallShape e1, WallShape e2, MWall m1, MWall m2) {
            RoomCorner c = r.getCorners().get(i);
            assertEquals(floorId, c.getFloorId());
            assertEquals(e1, c.getWallEnd1().getWallShape());
            assertEquals(e2, c.getWallEnd2().getWallShape());
            assertEquals(m1, c.getWallEnd1().getMWall());
            assertEquals(m2, c.getWallEnd2().getMWall());
            return this;
        }

        public RoomAuditor printCorners() {
            System.out.println("--CORNERS for " + r + "----");
            for (RoomCorner c : r.getCorners()) {
                System.out.println(c);
            }
            System.out.println();
            return this;
        }

        MRoom r;
    }

    private void printWalls() {
        System.out.println("--WALLS----------");
        for (MWall mw : walls) {
            System.out.println(mw);
        }
    }

    private void printCorners() {
        System.out.println("--CORNERS----------");
        for (RoomCorner co : corners) {
            System.out.println(co);
        }
    }

    private void printPoints() {
        System.out.println("--POINTS----------");
        for (MPoint p: model3d.getPoints()) {
            System.out.println(p);
        }
    }

    private void printFaces() {
        System.out.println("--FACES----------");
        for (MFace f: model3d.getFaces()) {
            System.out.println(f);
        }
    }

    private void printEdges() {
        System.out.println("--EDGES----------");
        for (MEdge e: model3d.getEdges()) {
            System.out.println(e);
        }
    }

    private void printBlocks() {
        System.out.println("--BLOCKS----------");
        for (MBlock b: model3d.getBlocks()) {
            System.out.println(b);
        }
    }

    final double epsilon = 1e-7;


    private WallShape ws01;
    private WallShape ws12;
    private WallShape ws23;

    private WallShape ws34;
    private WallShape ws45;
    private WallShape ws50;
    private WallShape ws14;


    private Model3d model3d;
    private List<MPillar> pillars;
    private List<MWall> walls;
    private List<MRoom> rooms;
    private List<RoomCorner> corners;

}