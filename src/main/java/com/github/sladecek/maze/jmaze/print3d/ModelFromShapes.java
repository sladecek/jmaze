package com.github.sladecek.maze.jmaze.print3d;
//REV1
import com.github.sladecek.maze.jmaze.geometry.Point2DDbl;
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print.PrintStyle;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MBlock;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MFace;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MPoint;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.*;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.Shapes;
import com.github.sladecek.maze.jmaze.shapes.WallShape;
import com.github.sladecek.maze.jmaze.shapes.WallType;

import java.util.*;

/**
 * Creates 3D model from 2D shapes.
 */
@SuppressWarnings("SameParameterValue")
public class ModelFromShapes {

    private ModelFromShapes(Shapes shapes, IMaze3DMapper mapper, double wallSize) {
        this.shapes = shapes;
        this.mapper = mapper;
        this.wallSize = wallSize;
    }

    static public Model3d make(Shapes shapes, IMaze3DMapper mapper, PrintStyle style, double wallSize) {
        ModelFromShapes mfs = new ModelFromShapes(shapes, mapper, wallSize);
        mfs.makePlanarProjection();
        mfs.extrudeTo3D();
        return mfs.m;
    }

    static public Model3d makeWithoutExtrusionForUnitTesting(Shapes shapes, IMaze3DMapper mapper, PrintStyle style, double
            wallSize) {
        ModelFromShapes mfs = new ModelFromShapes(shapes, mapper, wallSize);
        mfs.makePlanarProjection();
        return mfs.m;
    }

    public static MBlock createOneBlockFromFace(MFace f, IMaze3DMapper mapper) {
        assert f instanceof FloorFace;
        Altitude alt = ((FloorFace) f).getAltitude();
        MBlock block = new MBlock();
        for (MPoint p : f.visitPointsCounterclockwise()) {
            assert p instanceof TelescopicPoint;
            TelescopicPoint pp = (TelescopicPoint) p;
            Point3D pGround = pp.mapPoint(mapper, Altitude.GROUND);
            Point3D pCeiling = pp.mapPoint(mapper, alt);

            block.addCeilingPoint(pCeiling);
            block.addGroundPoint(pGround);
        }
        return block;
    }

    private void makePlanarProjection() {
        // create 2d floor projection of the maze using TelescopicPoints
        makeRooms();
        collectWallsForPillars();
        makePillars();
        copyFloorPlanToModel();
    }

    private void extrudeTo3D() {
        setFaceVisibilityDependingOnAltitude();

        VerticalFaceMaker maker = new VerticalFaceMaker(m, mapper);
        maker.stretchTelescopicPoints();
        maker.setTelescopicPointAltitude();

        // blocks must be created after the telescopic point coordinates were established
        // but before adding any new edges to the model
        createBlocks();

        maker.makeVerticalEdges();
        maker.makeVerticalFaces();
        maker.addNewObjectsBackToModel();
    }

    private void setFaceVisibilityDependingOnAltitude() {
        for (MFace f : m.getFaces()) {
            if (f instanceof FloorFace) {
                FloorFace ff = (FloorFace) f;
                f.setVisible(mapper.isAltitudeVisible(ff.getAltitude()));
            }
        }
    }

    private void makeRooms() {
        shapes.getShapes().forEach((shape) -> {
            if (shape instanceof FloorShape) {
                FloorShape floorShape = (FloorShape) shape;
                Altitude altitude = Altitude.FLOOR;
                if (floorShape.isHole()) {
                    altitude = Altitude.GROUND;
                }
                makeRoom(floorShape.getRoomId(), altitude);
            }
        });
    }

    private MRoom makeRoom(int floorId, Altitude altitude) {
        MRoom room = rooms.get(floorId);
        if (room == null) {
            room = new MRoom(floorId);
            room.setAltitude(altitude);
            rooms.put(floorId, room);
        }
        return room;
    }

    private void collectWallsForPillars() {
        wallsForPillars = new TreeMap<>();
        shapes.getShapes().forEach((shape) -> {
            if (shape instanceof WallShape) {
                WallShape wallShape = (WallShape) shape;
                MWall wall = new MWall();
                Altitude altitude = Altitude.CEILING;
                if (wallShape.getWallType() == WallType.noWall) {
                    altitude = Altitude.FLOOR;
                }
                wall.setAltitude(altitude);
                walls.add(wall);
                for (int end = 0; end < 2; end++) {
                    addWallToPillar(makeWallEnd(wall, wallShape, end == 1));
                }
            }
        });
    }

    private WallEnd makeWallEnd(MWall wall, WallShape wallShape, boolean reversed) {
        return new WallEnd(wall, wallShape, reversed);
    }

    private void addWallToPillar(WallEnd end) {
        Set<WallEnd> s = wallsForPillars.computeIfAbsent(end.getPillarPoint(), k -> new TreeSet<>());
        s.add(end);
    }

    private void makePillars() {
        for (Point2DInt center : wallsForPillars.keySet()) {
            Point2DDbl c = new Point2DDbl(center.getX(), center.getY());
            double wallWidthInMm = mapper.inverselyMapLengthAt(c, wallSize);

            List<WallEnd> walls = new LinkedList<>(wallsForPillars.get(center));
            PillarMaker pm = new PillarMaker(mapper.createLocalCoordinateSystem(center), center, walls, wallWidthInMm);
            pm.makePillar();
            pillars.add(pm.getBase());
            takeRoomCornersFromPillar(pm.getCorners());
        }
    }

    private void takeRoomCornersFromPillar(Collection<RoomCorner> corners) {
        for (RoomCorner c : corners) {
            int floorId = c.getFloorId();
            MRoom room = makeRoom(floorId, Altitude.FRAME);
            room.addCorner(c);
        }
    }

    private void copyFloorPlanToModel() {
        m = new Model3d();
        for (MPillar p : pillars) {
            m.addFace(p);
            m.addEdges(p.getEdges());
            m.addPoints(p.getIntersections());
        }

        for (MWall w : walls) {
            m.addFace(w);
            w.finishEdges();
            m.addEdge(w.getE2());
            m.addEdge(w.getE4());
            // points and edges e1, e3 have already been provided by pillars
        }

        for (MRoom r : rooms.values()) {
            m.addFace(r);
            r.finishEdges();
            // no new points or edges
        }
    }

    private void createBlocks() {
        for (MFace f : m.getFaces()) {
            // do not create blocks from invisible faces
            if (f.isVisible()) {
                MBlock block = createOneBlockFromFace(f, mapper);
                m.addBlock(block);
            }
        }
    }

    private final double wallSize;
    private final Shapes shapes;

    private final IMaze3DMapper mapper;
    private final ArrayList<MWall> walls = new ArrayList<>();
    private final ArrayList<MPillar> pillars = new ArrayList<>();
    private final TreeMap<Integer, MRoom> rooms = new TreeMap<>();
    private Model3d m;
    private TreeMap<Point2DInt, Set<WallEnd>> wallsForPillars;

}
