package com.github.sladecek.maze.jmaze.shapes;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;

/**
 * Created by sla on 3/7/17.
 */
public enum MarkType {
    none, solution, startRoom, targetRoom;


    public static MarkType markRoomFromRealization(int roomId, MazeRealization mr) {
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
