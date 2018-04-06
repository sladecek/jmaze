package com.github.sladecek.maze.jmaze.generator;
//REV1
import com.github.sladecek.maze.jmaze.util.BitSetIntervalPrinter;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * Defines concrete maze by picking opened walls among all possible walls in the maze. This also contains solution.
 */
public final class MazePath {


    public MazePath(int wallCount, int startRoom, int targetRoom) {
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

    public ArrayList<Integer> getSolution() {
        return solution;
    }

    public void setSolution(ArrayList<Integer> solution) {
        this.solution = solution;
    }

    public String printClosedWalls() {
        return new BitSetIntervalPrinter(isWallClosed, wallCount).printAsIntervals();
    }

    private void allocateWalls(int wallCount) {
        isWallClosed = new BitSet(wallCount);
        isWallClosed.set(0, isWallClosed.size(), true);
    }

    public int getStartRoom() {
        return startRoom;
    }

    public int getTargetRoom() {
        return targetRoom;
    }
    private final int wallCount;
    private final int startRoom;
    private final int targetRoom;
    private BitSet isWallClosed;
    private ArrayList<Integer> solution = new ArrayList<>();

}
