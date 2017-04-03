package com.github.sladecek.maze.jmaze.maze3d;


import java.util.ArrayList;
import java.util.Set;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Room floor in 3D model.
 */
public class RoomFace extends FloorFace
{


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
        for(;;) {
            unfinished.remove(current);
            addEdge(current.getWall1().getWall().getSideEdge(false));
            addEdge(current.getWall2().getWall().getSideEdge(true));

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
