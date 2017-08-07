package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.generator.MazePick;

/**
 * Created by sla on 3/7/17.
 */
public enum MarkType {
    none, solution, startRoom, targetRoom;


    public static MarkType markRoomFromRealization(int roomId, MazePick mr) {
        if (mr.getStartRoom() == roomId) {
            return startRoom;
        } else if (mr.getTargetRoom() == roomId) {
            return targetRoom;
        } else if (mr.getSolution().contains(roomId)) {
            return solution;
        } else {
            return none;
        }
    }
}
