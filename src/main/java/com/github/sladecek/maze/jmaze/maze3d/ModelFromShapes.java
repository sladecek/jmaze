package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.model3d.MEdge;
import com.github.sladecek.maze.jmaze.model3d.MFace;
import com.github.sladecek.maze.jmaze.model3d.Model3d;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
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

        makeRooms();
        collectWallsForPillars();
        makePillars();

        copyFloorPlanToModel();

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
        Set<WallEnd> s = wallsForPillars.get(end.getCenterPoint());
        if (s == null) {
            s = new HashSet<WallEnd>();
            wallsForPillars.put(end.getCenterPoint(), s);
        }
        s.add(end);
    }


    private void makePillars() {
        for (Point2D center : wallsForPillars.keySet()) {
            double wallWidthInMm = mapper.inverselyMapLengthAt(center,
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
        for (MPillar p: pillars) {
            m.addFace(p);
            m.addEdges(p.getEdges());
            m.addPoints(p.getIntersections());
        }

        for (MWall w: walls) {
            m.addFace(w);
            w.finishEdges();
            m.addEdge(w.getE2());
            m.addEdge(w.getE4());
            // points and edges e1, e3 have already been provided by pillars
        }

        for (MRoom r: rooms.values()) {
            m.addFace(r);
            r.finishEdges();
            // no new points or edges
        }
    }

    private void extrudeBlocks() {
        // visit all edges
        for(MEdge e: m.getEdges()) {
            FloorFace leftFace = (FloorFace) e.getLeftFace();
            FloorFace rightFace = (FloorFace) e.getRightFace();

            // compare altitude of the faces along the edge
            int a1 = leftFace.getAltitude();
            int a2 = rightFace.getAltitude();

            FloorPoint fp1 = (FloorPoint) e.getP1();
            FloorPoint fp2 = (FloorPoint) e.getP2();

            // set altitude of the left face
            fp1.setAltitude(a1);
            fp2.setAltitude(a1);
            if (a1 != a2) {

                // split the edge into two edges on different altitudes
                MEdge e12 = fp1.makeRod(a1, a2);
                MEdge e21 = fp2.makeRod(a2, a1);

                FloorPoint fp1at2 = fp1.cloneAtAltitude(a2);
                FloorPoint fp2at2 = fp1.cloneAtAltitude(a2);

                MEdge e22 = new MEdge(fp1at2, fp2at2);
                rightFace.replaceEdge(e, e22);

                // add new edge to the model
                m.addEdge(e22);;

                // add the new points to the model
                m.addPoint(fp1at2);
                m.addPoint(fp2at2);

                // create new vertical face
                MFace vf = new MFace();
                vf.addEdge(e);
                vf.addEdge(e21);
                vf.addEdge(e12);
                vf.addEdge(e22);
                m.addFace(vf);

            }
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
