package com.github.sladecek.maze.jmaze.maze3d;

import com.github.sladecek.maze.jmaze.geometry.Point2D;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

import java.util.*;

/**
 * Creates pillars from walls.
 */
public class PillarMaker {
    public PillarMaker(Point2D center, Set<WallShape> wallShapes, double wallWidthInMm) {
        this.center = center;
        this.wallShapes = wallShapes;
        this.wallWidthInMm = wallWidthInMm;
    }

    public void makePillars() {
        createWallBeams();
        sortBeams();
    }

    private void sortBeams() {
        beams = new ArrayList<>();
        if (!unsortedBeams.isEmpty()) {
            WallBeam current = unsortedBeams.pop();
            addBeam(current);
            while (!unsortedBeams.isEmpty()) {
                Optional<WallBeam> r = findFollower(current);
                if (r.isPresent()) {
                    current = r.get();
                } else {
                    throw new IllegalArgumentException("Beams for pillar " + center + " are not consistent");
                }
                unsortedBeams.remove(current);
                addBeam(current);
            }
        }
    }

    private void addBeam(WallBeam current) {
        beams.add(current);
        beams.add(new RoomBeam(current.getLeftFaceId()));
    }

    private Optional<WallBeam> findFollower(WallBeam current) {
        for (WallBeam b : unsortedBeams) {
            if (current.getLeftFaceId() == b.getRightFaceId()) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    private void createWallBeams() {
        unsortedBeams = new LinkedList<>();
        for (WallShape ws : wallShapes) {
            WallBeam b;
            if (ws.getP1().equals(center)) {
                b = new WallBeam(ws.getP2(), ws,  ws.getLeftFaceId(), ws.getRightFaceId());
            } else {
                b = new WallBeam(ws.getP1(), ws, ws.getRightFaceId(), ws.getLeftFaceId());
            }
            unsortedBeams.add(b);
        }
    }

    private LinkedList<WallBeam> unsortedBeams;
    private Point2D center;
    private Set<WallShape> wallShapes;
    private double wallWidthInMm;
    private FloorFace base;
    private ArrayList<Beam> beams;
}
