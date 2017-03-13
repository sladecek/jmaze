package com.github.sladecek.maze.jmaze.print3dn;


import com.github.sladecek.maze.jmaze.geometry.Axis;
import com.github.sladecek.maze.jmaze.geometry.Direction;

/**
 * Rotator rotates RoomFace coordinates by the z axis so that we can generate all faces of a room
 * by generation one corner and rotating it four time.
 */
public class Rotator {

    /**
     * Creates new rotator that rotates everything  {@code quadrants} times by 90 degrees around z axis clockwise..
     *
     * @param quadrants
     */
    public Rotator(int quadrants) {
        this.quadrants = (quadrantCnt + quadrants) % quadrantCnt;
    }

    Direction rot(Direction in) {
        if (in == Direction.FLOOR) {
            return in;
        }
        int o = (in.ordinal() + quadrants) % quadrantCnt;
        return Direction.values()[o];
    }

    Axis rot(Axis in) {
        if (quadrants % 2 == 0) {
            return in;
        } else {
            switch (in) {
                case X:
                    return Axis.Y;
                case Y:
                    return Axis.X;
                default:
                    return in;
            }
        }
    }

    RoomPosition rot(RoomPosition in) {
        if (quadrants >= 2) {
            switch (in) {
                case outSmall:
                    return RoomPosition.outBig;
                case outBig:
                    return RoomPosition.outSmall;
                case inSmall:
                    return RoomPosition.inBig;
                case inBig:
                    return RoomPosition.inSmall;
            }
        }
        return in;
    }

    boolean rot(boolean in) {
        if (quadrants >= 2) {
            return in;
        } else {
            return !in;
        }
    }

    private final int quadrantCnt = 4;
    private int quadrants;

}
