package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.*;
import com.github.sladecek.maze.jmaze.shapes.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Tests ModelFromShapes class.
 */
public class ModelFromShapesTestBase {

    protected Shapes setUpInput() {
        Shapes sc = new Shapes(new ShapeContext(false, 100, 100));
        sc.add(new FloorShape(0, new Point2DInt(55, 15)));
        sc.add(new FloorShape(1, new Point2DInt(65, 15)));

        Point2DInt p0 = new Point2DInt(50, 10);
        Point2DInt p1 = new Point2DInt(60, 10);
        Point2DInt p2 = new Point2DInt(70, 10);
        Point2DInt p3 = new Point2DInt(70, 20);
        Point2DInt p4 = new Point2DInt(60, 20);
        Point2DInt p5 = new Point2DInt(50, 20);

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


    protected void collectOutputs(Model3d r) {
        pillars = r.getFaces()
                .stream()
                .filter((f) -> (f instanceof MPillar))
                .map((f) -> (MPillar) f)
                .sorted(Comparator
                        .comparingDouble((MPillar po) -> po.getCenter().getY())
                        .thenComparingDouble((MPillar po) -> po.getCenter().getX()))
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



    protected void checkWall(MWall w, WallShape ws, int altitude, double[] expected) {

        double[] points = new double[8];
        assertEquals(altitude, w.getAltitude().getValue());
        TelescopicPoint pr11 = ((TelescopicPoint) w.getE1().getP1());
        points[0] = pr11.getPlanarX();
        points[1] = pr11.getPlanarY();
        TelescopicPoint pr12 = ((TelescopicPoint) w.getE1().getP2());
        points[2] = pr12.getPlanarX();
        points[3] = pr12.getPlanarY();
        TelescopicPoint pr31 = ((TelescopicPoint) w.getE3().getP1());
        points[4] = pr31.getPlanarX();
        points[5] = pr31.getPlanarY();
        TelescopicPoint pr32 = ((TelescopicPoint) w.getE3().getP2());
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

    protected void printPointArray(double[] points) {
        for (double d : points) {
            System.out.print(d + ", ");
        }
        System.out.println();
    }


    protected void checkPillar(MPillar p, int altitude, double[][] expectedIntersections) {
        assertEquals(altitude, p.getAltitude().getValue());
        assertEquals(expectedIntersections.length, p.getIntersections().size());
        for (int i = 0; i < expectedIntersections.length; i++) {
            final TelescopicPoint intersect = (TelescopicPoint) p.getIntersections().get(i);
            assertEquals(expectedIntersections[i][0], intersect.getPlanarX(), epsilon);
            assertEquals(expectedIntersections[i][1], intersect.getPlanarY(), epsilon);
        }
    }


    protected RoomAuditor chechRoom(MRoom r) {
        return new RoomAuditor(r);
    }

    protected void printWalls() {
        System.out.println("--WALLS----------");
        for (MWall mw : walls) {
            System.out.println(mw);
        }
    }

    protected void printCorners() {
        System.out.println("--CORNERS----------");
        for (RoomCorner co : corners) {
            System.out.println(co);
        }
    }

    protected void printPoints() {
        System.out.println("--POINTS----------");
        for (MPoint p : model3d.getPoints()) {
            System.out.println(p);
        }
    }

    protected void printFaces() {
        System.out.println("--FACES----------");
        for (MFace f : model3d.getFaces()) {
            System.out.println(f);
        }
    }

    protected void printEdges() {
        System.out.println("--EDGES----------");
        for (MEdge e : model3d.getEdges()) {
            System.out.println(e);
        }
    }

    protected void printBlocks() {
        System.out.println("--BLOCKS----------");
        for (MBlock b : model3d.getBlocks()) {
            System.out.println(b);
        }
    }

    protected void checkEdgesOfFacePointToTheFace(MFace testedFace) {
        for (MEdge e : testedFace.getEdges()) {
            assert(e.getLeftFace() == testedFace || e.getRightFace() == testedFace);
        }
    }

    protected void checkEdgesAreCyclic(MFace testedFace) {

        Stack<MEdge> remainingEdges = new Stack<>();
        remainingEdges.addAll(testedFace.getEdges());
        if (!remainingEdges.isEmpty()) {
            MEdge first = remainingEdges.pop();
            MEdge current = first;
            while (!remainingEdges.isEmpty()) {
                boolean found = false;
                for (MEdge b : remainingEdges) {
                    if (b.getBackwardPoint(testedFace) == current.getForwardPoint(testedFace)) {
                        current = b;
                        found = true;
                        break;
                    }
                }
                assert (found);
                remainingEdges.remove(current);
            }
            assert (first.getBackwardPoint(testedFace) == current.getForwardPoint(testedFace));
        }
    }

    protected class RoomAuditor {
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

        public RoomAuditor checkAltitude(int expectedAltitude) {
            assertEquals(expectedAltitude, r.getAltitude().getValue());
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

        public RoomAuditor checkEdgeCount(int expectedEdgeCount) {
            assertEquals(expectedEdgeCount, r.getEdges().size());
            return this;
        }

        MRoom r;
    }
    final double epsilon = 1e-7;


    protected WallShape ws01;
    protected WallShape ws12;
    protected WallShape ws23;

    protected WallShape ws34;
    protected WallShape ws45;
    protected WallShape ws50;
    protected WallShape ws14;


    protected Model3d model3d;
    protected List<MPillar> pillars;
    protected List<MWall> walls;
    protected List<MRoom> rooms;
    protected List<RoomCorner> corners;

}