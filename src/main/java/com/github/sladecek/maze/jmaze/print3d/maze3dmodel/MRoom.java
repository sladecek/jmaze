package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;


import com.github.sladecek.maze.jmaze.geometry.LeftRight;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.MEdge;

import java.util.ArrayList;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /* Creates new face from existing edges taken from MWals. MWals are visited in circular order,
     Assigns face id to the edges.
      */
    public void finishEdges() {
        if (corners.isEmpty()) {
            return;
        }
        Set<RoomCorner> unfinished = corners.stream().collect(Collectors.toSet());
        RoomCorner first = corners.get(0);
        RoomCorner current = first;
        for (; ; ) {
            unfinished.remove(current);
            final MWall w2 = current.getWallEnd2().getMWall();
            MEdge e2 = w2.getSideEdge(LeftRight.right);
            e2.setRightFace(this);
            addEdge(e2);

            if (unfinished.isEmpty()) {
                break;
            }

            Optional<RoomCorner> next =
                    unfinished.stream().filter((co) -> co.getWallEnd1().getMWall() == w2).findFirst();

            if (!next.isPresent()) {
                throw new InternalError("Room face edges are not cyclic");
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
                '}';
    }

    private ArrayList<RoomCorner> corners = new ArrayList<>();
    private int floorId;
}
