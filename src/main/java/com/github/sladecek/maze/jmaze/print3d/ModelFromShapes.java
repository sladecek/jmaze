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
        return mfs.make();
    }

    private Model3d make() {
        // create 2d floor projection of the maze using ExtrudablePoints
        makeRooms();
        collectWallsForPillars();
        makePillars();
        copyFloorPlanToModel();

        // extrude the floor plan to 3D
        extrudeEdges();
        extrudeBlocks();
        return m;
    }

    private void makeRooms() {
        shapes.getShapes().forEach((shape) -> {
            if (shape instanceof FloorShape) {
                FloorShape floorShape = (FloorShape) shape;
                int altitude = FloorFace.FLOOR_ALTITUDE;
                if (floorShape.isHole()) {
                    altitude = FloorFace.GROUND_ALTITUDE;
                }
                makeRoom(floorShape.getRoomId(), altitude);
            }
        });
    }

    private MRoom makeRoom(int floorId, int altitude) {
        MRoom room = rooms.get(floorId);
        if (room == null) {
            room = new MRoom();
            room.setAltitude(altitude);
            rooms.put(floorId, room);
        }
        return room;
    }

    private void collectWallsForPillars() {
        wallsForPillars = new HashMap<>();
        shapes.getShapes().forEach((shape) -> {
            if (shape instanceof WallShape) {
                WallShape wallShape = (WallShape) shape;
                MWall wall = new MWall();
                int altitude = FloorFace.CEILING_ALTITUDE;
                if (wallShape.getWallType() == WallType.noWall) {
                    altitude = FloorFace.FLOOR_ALTITUDE;
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
        Set<WallEnd> s = wallsForPillars.get(end.getNonPillarPoint());
        if (s == null) {
            s = new HashSet<WallEnd>();
            wallsForPillars.put(end.getNonPillarPoint(), s);
        }
        s.add(end);
    }


    private void makePillars() {
        for (Point2D center : wallsForPillars.keySet()) {
            Point3D c3d = new Point3D(center.getX(), center.getY(), 0);
            double wallWidthInMm = mapper.inverselyMapLengthAt(c3d,
                    sizes.getInnerWallToCellRatio() * sizes.getCellSizeInmm());
            PillarMaker pm = new PillarMaker(center, wallsForPillars.get(center), wallWidthInMm, mapper);
            pm.makePillar();
            pillars.add(pm.getBase());
            takeRoomCornersFromPillar(pm.getCorners());
        }
    }

    private void takeRoomCornersFromPillar(Collection<RoomCorner> corners) {
        for (RoomCorner c : corners) {
            int floorId = c.getFloorId();
            MRoom room = makeRoom(floorId, FloorFace.FRAME_ALTITUDE);
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

    private void extrudeEdges() {

        // visit all edges
        for (MEdge e : m.getEdges()) {
            // edge endpoints
            ProjectedPoint fp1 = (ProjectedPoint) e.getP1();
            ProjectedPoint fp2 = (ProjectedPoint) e.getP2();

            // each edge separates two faces
            FloorFace leftFace = (FloorFace) e.getLeftFace();
            FloorFace rightFace = (FloorFace) e.getRightFace();

            // compare altitude of the faces along the edge
            int a1 = leftFace.getAltitude();
            int a2 = rightFace.getAltitude();
            int lowAltitude = a1;
            int highAltitude = a2;
            if (a1 > a2) {
                lowAltitude = a2;
                highAltitude = a1;
            }

            // set altitude of the left face to the base level
            fp1.setAltitudes(mapper, lowAltitude, highAltitude);
            fp2.setAltitudes(mapper, lowAltitude, highAltitude);

            // if the altitude is the same in both faces, we are done

            // if the faces are on different altitudes, it is necessary to
            // add vertical face on the low altitude
            if (a1 != a2) {
                MPoint fp1low = fp1.getLowAltitudePoint();
                MPoint fp2low = fp1.getLowAltitudePoint();

                // new horizontal edge
                MEdge lowEdge = new MEdge(fp1low, fp2low);
                rightFace.replaceEdge(e, lowEdge);

                // add new edge to the model
                m.addEdge(lowEdge);

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
    }

    private void extrudeBlocks() {
        for (MFace f : m.getFaces()) {
            MBlock block = new MBlock();
            for (MEdge e : f.getEdges()) {
                ProjectedPoint p = (ProjectedPoint) e.getP1();
                block.addCeilingPoint(p.getCoord());
                Point3D pGround = p.mapPoint(mapper, FloorFace.GROUND_ALTITUDE);
                block.addGroundPoint(pGround);
            }
            m.addBlock(block);
        }
    }

    private ShapeContainer shapes;
    private Maze3DSizes sizes;
    private IPrintStyle style;
    private IMaze3DMapper mapper;
    private Model3d m;
    private HashMap<Point2D, Set<WallEnd>> wallsForPillars;
    private ArrayList<MWall> walls = new ArrayList<>();
    private ArrayList<MPillar> pillars = new ArrayList<>();
    private TreeMap<Integer, MRoom> rooms = new TreeMap<>();
}
