package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.*;
import com.github.sladecek.maze.jmaze.print3d.maze3dmodel.*;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.shapes.WallShape;
import com.github.sladecek.maze.jmaze.shapes.WallType;

import java.util.*;
import java.util.logging.Logger;

/**
 * Creates model from shapes
 */
public class ModelFromShapes {

    public ModelFromShapes(ShapeContainer shapes, IMaze3DMapper mapper, Maze3DSizes sizes, IPrintStyle style) {
        this.shapes = shapes;
        this.mapper = mapper;
        this.sizes = sizes;
        this.style = style;
    }

    static public Model3d make(ShapeContainer shapes, IMaze3DMapper mapper, Maze3DSizes sizes, IPrintStyle style) {
        ModelFromShapes mfs = new ModelFromShapes(shapes, mapper, sizes, style);
        mfs.makePlanarProjection();
        LOG.info("BEFORE" + mfs.m.toString()); // TODO smazat
        mfs.extrudeTo3D();
        LOG.info("AFTER" + mfs.m.toString());  // TODO smazat
        return mfs.m;
    }

    static public Model3d makeWithoutExtrusionForUnitTesting(ShapeContainer shapes, IMaze3DMapper mapper, Maze3DSizes sizes, IPrintStyle style) {
        ModelFromShapes mfs = new ModelFromShapes(shapes, mapper, sizes, style);
        mfs.makePlanarProjection();
        return mfs.m;
    }

    private void makePlanarProjection() {
        // create 2d floor projection of the maze using ExtrudablePoints
        makeRooms();
        collectWallsForPillars();
        makePillars();
        copyFloorPlanToModel();
    }

    private void extrudeTo3D() {
        if (false) {
            // compute and set altitudes
            computeAltitudes();

            // extrude blocks first - the model will be modified by adding vertical faces
            createBlocks();

            // add vertical faces
            addVerticalFacesOnEdgesWithAltitudeDifference();
        } else {

            createBlocks();

            VerticalFaceMaker maker = new VerticalFaceMaker(m, mapper);
            maker.stretchTelescopicPoints();
            maker.makeVerticalEdges();
            maker.makeVerticalEdgesAndFacesShapes();
            maker.addNewObjectsBackToModel();
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
                    addWallToPilar(makeWallEnd(wall, wallShape, end == 1));
                }
            }
        });
    }

    private WallEnd makeWallEnd(MWall wall, WallShape wallShape, boolean reversed) {
        return new WallEnd(wall, wallShape, reversed);
    }

    private void addWallToPilar(WallEnd end) {
        Set<WallEnd> s = wallsForPillars.get(end.getPillarPoint());
        if (s == null) {
            s = new TreeSet<WallEnd>();
            wallsForPillars.put(end.getPillarPoint(), s);
        }
        s.add(end);
    }


    private void makePillars() {
        for (Point2D center : wallsForPillars.keySet()) {
            Point3D c3d = new Point3D(center.getX(), center.getY(), 0);
            double wallWidthInMm = mapper.inverselyMapLengthAt(c3d,
                    sizes.getInnerWallToCellRatio() * sizes.getCellSizeInmm());
            List<WallEnd> walls = new LinkedList<>(wallsForPillars.get(center));
            PillarMaker pm = new PillarMaker(center, walls, wallWidthInMm);
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

    private void computeAltitudes() {

        // visit all edges
        for (MEdge e : m.getEdges()) {
            // edge endpoints
            TelescopicPoint fp1 = (TelescopicPoint) e.getP1();
            TelescopicPoint fp2 = (TelescopicPoint) e.getP2();

            // each edge separates two faces
            FloorFace leftFace = (FloorFace) e.getRightFace();
            FloorFace rightFace = (FloorFace) e.getLeftFace();

            // compare altitude of the faces along the edge
            int a1 = leftFace.getAltitude().getValue();
            int a2 = rightFace.getAltitude().getValue();
            int lowAltitude = a1;
            int highAltitude = a2;
            if (a1 > a2) {
                lowAltitude = a2;
                highAltitude = a1;
            }

            // set altitude of the left face to the base level
            fp1.setAltitudesUsingMapper(mapper, lowAltitude, highAltitude);
            fp2.setAltitudesUsingMapper(mapper, lowAltitude, highAltitude);

        }
    }

    private void addVerticalFacesOnEdgesWithAltitudeDifference() {
        List<MEdge> newEdges = new ArrayList<>();

        // visit all edges
        for (MEdge e : m.getEdges()) {
            // edge endpoints
            TelescopicPoint fp1 = (TelescopicPoint) e.getP1();
            TelescopicPoint fp2 = (TelescopicPoint) e.getP2();

            // each edge separates two faces
            FloorFace leftFace = (FloorFace) e.getRightFace();
            FloorFace rightFace = (FloorFace) e.getLeftFace();

            // compare altitude of the faces along the edge
            int a1 = leftFace.getAltitude().getValue();
            int a2 = rightFace.getAltitude().getValue();

            // if the altitude is the same in both faces, we are done

            // if the faces are on different altitudes, it is necessary to
            // add vertical face on the low altitude
            if (a1 != a2) {
                MPoint fp1low = fp1.getLowAltitudePoint();
                MPoint fp2low = fp2.getLowAltitudePoint();

                // new horizontal edge
                MEdge lowEdge = new MEdge(fp1low, fp2low);
                MFace lowFace = leftFace;
                if (a1 > a2) {
                    lowFace = rightFace;
                }
                lowFace.replaceEdge(e, lowEdge);

                // add new edge to the model
                newEdges.add(lowEdge);

                // add the new points to the model
                m.addPoint(fp1low);
                m.addPoint(fp2low);

                // create new vertical face
                MFace vf = new MFace();
                vf.addEdge(e);
                vf.addEdge(fp1.getRod());
                vf.addEdge(fp2.getRod());
                vf.addEdge(lowEdge);
                m.addFace(vf);

            }
        }
        m.addEdges(newEdges);
    }


    private void createBlocks() {
        for (MFace f : m.getFaces()) {
            assert f instanceof FloorFace;
            int alt = ((FloorFace) f).getAltitude().getValue();
            MBlock block = new MBlock();
            for (MPoint p : f.visitPointsAroundEdges()) {
                assert p instanceof TelescopicPoint;
                TelescopicPoint pp = (TelescopicPoint) p;
                Point3D pGround = pp.mapPoint(mapper, Altitude.GROUND.getValue());
                Point3D pCeiling = pp.mapPoint(mapper, alt);

                block.addCeilingPoint(pCeiling);
                block.addGroundPoint(pGround);
            }
            m.addBlock(block);
        }
    }

    private static final Logger LOG = Logger.getLogger("maze.jmaze");
    private ShapeContainer shapes;
    private Maze3DSizes sizes;
    private IPrintStyle style;
    private IMaze3DMapper mapper;
    private Model3d m;
    private TreeMap<Point2D, Set<WallEnd>> wallsForPillars;
    private ArrayList<MWall> walls = new ArrayList<>();
    private ArrayList<MPillar> pillars = new ArrayList<>();
    private TreeMap<Integer, MRoom> rooms = new TreeMap<>();

}
