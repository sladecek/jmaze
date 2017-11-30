package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;


import com.github.sladecek.maze.jmaze.geometry.LeftRight;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Room floor in 3D model.
 */
public class MRoom extends FloorFace {

    public MRoom(int floorId) {
        this.floorId = floorId;
    }

    public void addCorner(RoomCorner c) {
        corners.add(c);
    }

    /**
     * Creates new face from existing edges taken from walls. Walls are visited in circular order,
     * Assigns face id to the edges.
     */
    public void finishEdges() {
        LOG.info("finishingEdges of " + this);
        if (corners.isEmpty()) {
            return;
        }
        Set<RoomCorner> unfinished = new HashSet<>(corners);
        for (RoomCorner rc : unfinished) {
            LOG.info("  " + rc);
        }

        RoomCorner current = corners.get(0);
        for (; ; ) {
            unfinished.remove(current);
            final WallEnd wallEnd2 = current.getWallEnd2();
            final LeftRight centralSide = wallEnd2.isP1Pillar() ? LeftRight.left : LeftRight.right;
            final MWall w2 = wallEnd2.getMWall();
            MEdge e2 = w2.getSideEdge(centralSide);
            e2.setLeftFace(this);
            addEdge(e2);

            if (unfinished.isEmpty()) {
                break;
            }

            Optional<RoomCorner> next =
                    unfinished.stream().filter((co) -> co.getWallEnd1().getMWall() == w2).findFirst();

            if (!next.isPresent()) {
                throw new InternalError("Room edges are not cyclic " + w2);
            }
            current = next.get();
        }
    }

    public int getFloorId() {
        return floorId;
    }

    public ArrayList<RoomCorner> getCorners() {
        return corners;
    }

    @Override
    public String toString() {
        return "MRoom{" +
                "floorId=" + floorId +
                " edges=" + getEdges() +
                '}';
    }

    private static final Logger LOG = Logger.getLogger("maze");
    private final ArrayList<RoomCorner> corners = new ArrayList<>();
    private final int floorId;
}
