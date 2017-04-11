package com.github.sladecek.maze.jmaze.maze3d;


import com.github.sladecek.maze.jmaze.model3d.MEdge;

import java.util.ArrayList;
import java.util.Set;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Room floor in 3D model.
 */
public class MRoom extends FloorFace {

    public void addCorner(RoomCorner c) {
        corners.add(c);
    }

    public void finishEdges() {
        if (corners.isEmpty()) {
            return;
        }
        Set<RoomCorner> unfinished = corners.stream().collect(Collectors.toSet());
        RoomCorner first = corners.get(0);
        RoomCorner current = first;
        for (; ; ) {
            unfinished.remove(current);
            MEdge e1 = current.getWall1().getWall().getSideEdge(false);
            e1.setRightFace(this);
            addEdge(e1);
            MEdge e2 = current.getWall2().getWall().getSideEdge(true);
            addEdge(e2);
            e2.setRightFace(this);

            if (unfinished.isEmpty()) {
                break;
            }

            final WallEnd w2 = current.getWall2();
            Optional<RoomCorner> next =
                    unfinished.stream().filter((co) -> co.getWall1() == w2).findFirst();

            if (next.isPresent()) {
                throw new InternalError("Room face edges are not cyclic");
            }
            current = next.get();
        }
    }

    private ArrayList<RoomCorner> corners = new ArrayList<>();
}
