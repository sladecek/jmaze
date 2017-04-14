package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.model3d.MEdge;
import com.github.sladecek.maze.jmaze.model3d.MPoint;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.shapes.WallType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.Optional;


/**
 * Creates pillars from walls.
 */
public class PillarMaker {
    public PillarMaker(Point2D center, Set<WallEnd> wallShapes, double wallWidthInMm, IMaze3DMapper mapper) {
        this.center = center;
        this.wallEnds = wallShapes;
        this.wallWidthInMm = wallWidthInMm;
        this.mapper = mapper;
    }

    public void makePillar() {
        createBasePillar();
        sortWalls();
        computeIntersections();
        base.setIntersections(intersections);
    }

    private void createBasePillar() {
        base = new MPillar();
        int altitude = FloorFace.CEILING_ALTITUDE;
        if (wallEnds.stream().allMatch((we) -> we.getWallShape().getWallType() == WallType.noWall)) {
            altitude = FloorFace.FLOOR_ALTITUDE;
        }
        base.setAltitude(FloorFace.CEILING_ALTITUDE);
    }

    private void sortWalls() {
        walls = new ArrayList<>();
        if (!unsortedWalls.isEmpty()) {

            WallEnd first = unsortedWalls.pop();
            WallEnd current = first;
            RoomCorner corner = addWallAndCreateCorner(current);
            while (!unsortedWalls.isEmpty()) {
                Optional<WallEnd> r = findFollower(current);
                if (r.isPresent()) {
                    current = r.get();
                    corner.setWall2(current);
                } else {
                    throw new IllegalArgumentException("Walls for pillar " + center + " are not consistent");
                }
                unsortedWalls.remove(current);
                corner = addWallAndCreateCorner(current);
            }
            corner.setWall2(first);
        }
    }

    private void computeIntersections() {

        final int size = walls.size();
        for (int i = 0; i < size; i++) {
            int j = (i + 1) % size;
            Point3D in = computeIntersection(walls.get(i).getCenterPoint(), walls.get(j).getCenterPoint());
            MPoint mpin = new FloorPoint(in.getX(), in.getY());
            intersections.add(mpin);

            if (i > 0) {
                addEdge(walls.get(i), intersections.get(i - 1), mpin);
            }
        }

        // close loop
        addEdge(walls.get(0), intersections.get(size - 1), intersections.get(0));
    }

    private void addEdge(WallEnd wallEnd, MPoint p1, MPoint p2) {
        MEdge edge = new MEdge(p1, p2);
        edge.setLeftFace(base);
        edge.setRightFace(wallEnd.getWall());
        base.addEdge(edge);
        wallEnd.addEdge(edge);
    }

    Point3D computeIntersection(Point2D p1, Point2D p2) {
        double l1 = p1.getCartesianLength();
        double l2 = p2.getCartesianLength();
        double phi1 = p1.getCartesianAngle();
        double phi2 = p2.getCartesianAngle();
        double c1 = Math.cos(phi1);
        double s1 = Math.sin(phi1);
        double c2 = Math.cos(phi2);
        double s2 = Math.sin(phi2);
        double dx1 = -wallWidthInMm / 2 * s1;
        double dy1 = wallWidthInMm / 2 * c1;
        double dx2 = -wallWidthInMm / 2 * s2;
        double dy2 = wallWidthInMm / 2 * c2;
        double dx = -dx1 - dx2;
        double dy = -dy1 - dy2;
        double det = -c1 * s2 + c2 * s1;
        double k1 = (-dx * s2 + c2 * dy) / det;
        double k2 = (c1 * dy - dx * s1) / det;
        double px = k1 * c1 + dx1;
        double py = k1 * s1 + dy1;
        double px2 = k2 * c2 + dx2;
        double py2 = k2 * s2 + dy2;
        assert Math.abs(px - px2) < 0.000001 : "PillarMaker intersection solutions not equal x";
        assert Math.abs(py - py2) < 0.000001 : "PillarMaker intersection solutions not equal y";
        return new Point3D(px, py, 0);
    }

    public MPillar getBase() {
        return base;
    }


    private RoomCorner addWallAndCreateCorner(WallEnd current) {
        walls.add(current);
        RoomCorner rc = new RoomCorner(current.getLeftFaceId());
        corners.add(rc);
        rc.setWall1(current);
        return rc;
    }

    public ArrayList<RoomCorner> getCorners() {
        return corners;
    }

    private Optional<WallEnd> findFollower(WallEnd current) {
        for (WallEnd b : unsortedWalls) {
            if (current.getLeftFaceId() == b.getRightFaceId()) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    private final IMaze3DMapper mapper;
    private LinkedList<WallEnd> unsortedWalls;
    private Point2D center;
    private Set<WallEnd> wallEnds;
    private double wallWidthInMm;
    private MPillar base;
    private ArrayList<WallEnd> walls;
    private ArrayList<RoomCorner> corners;
    private ArrayList<MPoint> intersections = new ArrayList<>();
}
