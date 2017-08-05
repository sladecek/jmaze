package com.github.sladecek.maze.jmaze.generator;

import java.util.BitSet;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.maze.IMazeStructure;

/*
 * Generates random maze by opening walls random walk in a depth first order.
 */
public final class DepthFirstMazeGenerator implements IMazeGenerator {

    public DepthFirstMazeGenerator(Random randomGenerator) {
        super();
        this.randomGenerator = randomGenerator;
    }

    @Override
    public MazeRealization generateMaze(final IMazeStructure graph) {
        MazeRealization result = new MazeRealization(graph.getWallCount(), graph.getStartRoom(), graph.getTargetRoom());
        Vector<Integer> solution = null;
        int allRoomsCnt = graph.getRoomCount();
        visitedRooms = new BitSet(allRoomsCnt);

        stack = new Stack<>();
        visitRoom(graph.getStartRoom());
        while (!stack.isEmpty()) {

            int room = stack.peek();
            LOGGER.log(Level.INFO, " observing room: " + room);
            if (room == graph.getTargetRoom()) {
                // save solution
                assert solution == null : "Irrengarten cannot have two solutions";
                solution = new Vector<>();
                for (Integer i : stack) {
                    solution.add(i);
                }
                result.setSolution(solution);

                // do not continue from the target room, instead backtrace to
                // fill gaps
                stack.pop();
                continue;
            }
            Vector<Integer> candidates = findAllPossibleNextRooms(graph, result,
                    room);
            if (candidates.isEmpty()) {
                // backtrace - no way to go
                LOGGER.log(Level.INFO, "backtrace ");
                stack.pop();
                continue;
            }

            LOGGER.log(Level.INFO, " candidates: " + candidates.toString());

            // select next room
            int choice = 0;
            if (candidates.size() > 1) {
                choice = randomGenerator.nextInt(candidates.size());
            }
            int wall = candidates.elementAt(choice);
            LOGGER.log(Level.INFO, " opening wall " + wall);
            result.setWallClosed(wall, false);
            int otherRoom = graph.getRoomBehindWall(room, wall);
            visitRoom(otherRoom);

        }
        return result;
    }

    private Vector<Integer> findAllPossibleNextRooms(final IMazeStructure maze,
            final MazeRealization real, final int room) {
        Vector<Integer> candidates = new Vector<>();
        for (int wall : maze.getWalls(room)) {
            if (real.isWallClosed(wall)) {
                int otherRoom = maze.getRoomBehindWall(room, wall);
                if (!visitedRooms.get(otherRoom)) {
                    candidates.add(wall);
                }
            }
        }
        if (candidates.size() <= 1) {
            return candidates;
        }
        Vector<Integer> weightedCandidates = new Vector<>();
        for (int wall : candidates) {
            int weight = maze.getWallProbabilityWeight(wall);
            for (int i = 0; i < weight; i++) {
                weightedCandidates.add(wall);
            }
        }
        return weightedCandidates;
    }

    private void visitRoom(final int room) {
        assert !visitedRooms.get(room) : "Cannot visit the same room twice";
        LOGGER.log(Level.INFO, "visiting room " + room);
        visitedRooms.set(room);
        stack.push(room);
    }

    private static final Logger LOGGER = Logger.getLogger("maze");
    private final Random randomGenerator;
    private BitSet visitedRooms;
    private Stack<Integer> stack;
}
