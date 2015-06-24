package com.github.sladecek.maze.jmaze.generator;

import java.util.BitSet;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepthFirstMazeGenerator implements IMazeGenerator {
	
	private final static Logger log = Logger.getLogger("LOG"); 
	
	private Random randomGenerator = new Random();
	
	@Override
	public MazeRealization generateMaze(IMazeSpace maze) {
		MazeRealization result = new MazeRealization();
		result.allocateWalls(maze.getWallCnt());
		Vector<Integer> solution = null;
		randomGenerator.setSeed(27);
		visitedRooms = new BitSet(maze.getRoomCount());
		stack = new Stack<Integer>();
		visitRoom(maze.getStartRoom());
		while (!stack.isEmpty())
		{
			int room = stack.peek();
			log.log(Level.INFO, " observing room: "+ room);
			if (room == maze.getTargetRoom() )
			{
				// save solution
				assert solution == null : "Maze cannot have two solutions";
				solution = new Vector<Integer>();
				for(Integer i: stack) {
					solution.add(i);
				}
				result.setSolution(solution);
				
				// do not continue from the target room, instead backtrace to fill gaps
				stack.pop();
				continue;
			}
			Vector<Integer> candidates = findAllPossibleNextRooms(maze, result, room);
			if (candidates.isEmpty())
			{
				// backtrace - no way to go
				log.log(Level.INFO, "backtrace ");
				stack.pop();
				continue;
			}
			
			log.log(Level.INFO, " candidates: "+ candidates.toString());
			
			// select next room
			int choice = 0;
			if (candidates.size() > 1)
			{
				choice = randomGenerator.nextInt(candidates.size());
			}
			int wall = candidates.elementAt(choice);
			log.log(Level.INFO, " opening wall "+wall);
			result.setWallClosed(wall, false);
			int otherRoom = maze.getOtherRoom(room, wall);
			visitRoom(otherRoom);			
		}
		return result;
		
	}
	private Vector<Integer> findAllPossibleNextRooms(IMazeSpace maze, MazeRealization real, int room) {
		Vector<Integer> candidates = new Vector<Integer>();
		for(int wall: maze.getWalls(room))
		{
			if (real.isWallClosed(wall))
			{
				int otherRoom = maze.getOtherRoom(room, wall);
				if (!visitedRooms.get(otherRoom)) {
					candidates.add(wall);
				}						
			}
		}
		if (candidates.size() <= 1) {
			return candidates;
		}
		Vector<Integer> weightedCandidates = new Vector<Integer>();
		for(int wall: candidates) {
			int weight = maze.getWallProbabilityWeight(wall);
			for(int i = 0; i < weight; i++) {
				weightedCandidates.add(wall);
			}
		}
		return weightedCandidates;
		
	}
	private void visitRoom(int room) {
		assert !visitedRooms.get(room) : "Cannot visit the same room twice";
		log.log(Level.INFO, "visiting room " + room);
		visitedRooms.set(room);
		stack.push(room);		
	}
	
	private BitSet visitedRooms;
	private Stack<Integer> stack;

}
