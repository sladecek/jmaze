package com.github.sladecek.maze.jmaze.generator;

import java.util.BitSet;
import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.maze.IMazeGraph;

/*
 * Generates random maze opening walls by random walk in a depth first order.
 */
public final class DepthFirstMazeGenerator implements IMazeGenerator {

    public DepthFirstMazeGenerator(Random randomGenerator) {
        super();
        this.randomGenerator = randomGenerator;
    }

    @Override
    public MazePath generatePick(final IMazeGraph graph) {
        MazePath result = new MazePath(graph.getWallCount(), graph.getStartRoom(), graph.getTargetRoom());
        ArrayList<Integer> solution = null;
        int allRoomsCnt = graph.getRoomCount();
        visitedRooms = new BitSet(allRoomsCnt);

        stack = new Stack<>();
        visitRoom(graph.getStartRoom());
        while (!stack.isEmpty()) {

            int room = stack.peek();
            LOGGER.log(Level.FINE, " observing room: " + room);
            if (room == graph.getTargetRoom()) {
                // save solution
                assert solution == null : "Maze cannot have two solutions";
                solution = new ArrayList<>();
                for (Integer i : stack) {
                    solution.add(i);
                }
                result.setSolution(solution);

                // do not continue from the target room, instead backtrace to
                // fill gaps
                stack.pop();
                continue;
            }
            ArrayList<Integer> candidates = findAllPossibleNextRooms(graph, result,
                    room);
            if (candidates.isEmpty()) {
                // backtrace - no way to go
                LOGGER.log(Level.FINE, "backtrace ");
                stack.pop();
                continue;
            }

            LOGGER.log(Level.FINE, " candidates: " + candidates.toString());

            // select next room
            int choice = 0;
            if (candidates.size() > 1) {
                choice = randomGenerator.nextInt(candidates.size());
            }
            int wall = candidates.get(choice);
            LOGGER.log(Level.FINE, " opening wall " + wall);
            result.setWallClosed(wall, false);
            int otherRoom = graph.getRoomBehindWall(room, wall);
            visitRoom(otherRoom);

        }
        return result;
    }

    private ArrayList<Integer> findAllPossibleNextRooms(final IMazeGraph mazeGraph,
                                                     final MazePath real, final int room) {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int wall : mazeGraph.getWalls(room)) {
            if (real.isWallClosed(wall)) {
                int otherRoom = mazeGraph.getRoomBehindWall(room, wall);
                if (!visitedRooms.get(otherRoom)) {
                    candidates.add(wall);
                }
            }
        }
        if (candidates.size() <= 1) {
            return candidates;
        }
        ArrayList<Integer> weightedCandidates = new ArrayList<>();
        for (int wall : candidates) {
            int weight = mazeGraph.getWallProbabilityWeight(wall);
            for (int i = 0; i < weight; i++) {
                weightedCandidates.add(wall);
            }
        }
        return weightedCandidates;
    }

    private void visitRoom(final int room) {
        assert !visitedRooms.get(room) : "Cannot visit the same room twice";
        LOGGER.log(Level.FINE, "visiting room " + room);
        visitedRooms.set(room);
        stack.push(room);
    }

    private static final Logger LOGGER = Logger.getLogger("maze");
    private final Random randomGenerator;
    private BitSet visitedRooms;
    private Stack<Integer> stack;
}
