package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.model3d.MEdge;
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
        m = new Model3d();
        makeRooms();
        collectWallsForPillars();
        makePillars();

        mWalls.forEach(MWall::finishEdges);
        rooms.values().forEach(RoomFace::finishEdges);

        makeFloorPlan();
        assignAltitude();
        extrudeBlocks();
        return m;
    }

    private void makeRooms() {
        shapes.getShapes().forEach((shape)-> {
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


    private void collectWallsForPillars() {
        wallsForPillars = new HashMap<>();
        shapes.getShapes().forEach((shape)-> {
            if (shape instanceof  WallShape) {
                WallShape wallShape = (WallShape) shape;
                MWall wall = new MWall();
                int altitude = FloorFace.CEILING_ALTITUDE;
                if (wallShape.getWallType() == WallType.noWall) {
                    altitude = FloorFace.FLOOR_ALTITUDE;
                }
                wall.setAltitude(altitude);
                mWalls.add(wall);
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
        for(Point2D center: wallsForPillars.keySet()) {
            double wallWidthInMm = mapper.inverselyMapLengthAt(center,
                    sizes.getInnerWallToCellRatio()*sizes.getCellSizeInmm());
            PillarMaker pm = new PillarMaker(center, wallsForPillars.get(center), wallWidthInMm, mapper);
            pm.makePillar();
            pillars.add(pm.getBase());
            takeRoomCornersFromPillar(pm.getCorners());
        }
    }

    private void takeRoomCornersFromPillar(Collection<RoomCorner> corners) {
        for(RoomCorner c: corners) {
            int floorId = c.getFloorId();
            RoomFace room = makeRoom(floorId, FloorFace.FRAME_ALTITUDE);
            room.addCorner(c);
        }
    }

    private RoomFace makeRoom(int floorId, int altitude) {
        RoomFace room = rooms.get(floorId);
        if (room == null) {
            room = new RoomFace();
            room.setAltitude(altitude);
            rooms.put(floorId, room);
        }
        return room;
    }

    private void makeFloorPlan() {
    }

    private void assignAltitude() {
    }

    private void extrudeBlocks() {
    }

    private ShapeContainer shapes;
    private Maze3DSizes sizes;
    private IPrintStyle style;
    private IMaze3DMapper mapper;
    private Model3d m;
    private HashMap<Point2D, Set<WallEnd>> wallsForPillars;
    private ArrayList<MWall> mWalls = new ArrayList<>();
    private ArrayList<MPillar> pillars = new ArrayList<>();
    private TreeMap<Integer, RoomFace> rooms = new TreeMap<>();
}
