package com.github.sladecek.maze.jmaze.generator;

import java.util.BitSet;
import java.util.Vector;

import com.github.sladecek.maze.jmaze.util.BitSetIntervalPrinter;

/**
 * Data structure which defines one particular maze. Defines walls opened
 * on the maze. Also contains solution.
 */
public final class MazeRealization {


    public MazeRealization(int wallCount, int startRoom, int targetRoom) {
        allocateWalls(wallCount);
        this.wallCount = wallCount;
        this.startRoom = startRoom;
        this.targetRoom = targetRoom;
    }

    public boolean isWallClosed(int wall) {
        return isWallClosed.get(wall);
    }

    public void setWallClosed(int wall, boolean value) {
        isWallClosed.set(wall, value);
    }

    public Vector<Integer> getSolution() {
        return solution;
    }

    public void setSolution(Vector<Integer> solution) {
        this.solution = solution;
    }

    public String printClosedWalls() {
        return new BitSetIntervalPrinter(isWallClosed, wallCount).printAsIntervals();
    }

    private void allocateWalls(int wallCount) {
        isWallClosed = new BitSet(wallCount);
        isWallClosed.set(0, isWallClosed.size(), true);
    }

    private BitSet isWallClosed;
    private int wallCount;

    private Vector<Integer> solution = new Vector<Integer>();

    public int getStartRoom() {
        return startRoom;
    }

    public int getTargetRoom() {
        return targetRoom;
    }

    private int startRoom;
    private int targetRoom;


}
