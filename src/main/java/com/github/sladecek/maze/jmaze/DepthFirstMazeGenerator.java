package com.github.sladecek.maze.jmaze;

import java.util.BitSet;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

public class DepthFirstMazeGenerator implements IMazeGenerator {

	Random randomGenerator = new Random();
	
	@Override
	public void generateMaze(IMazeable maze) {
		randomGenerator.setSeed(27);
		visitedRooms = new BitSet(maze.getRoomCount());
		stack = new Stack<Integer>();
		visitRoom(maze.getStartRoom());
		while (!stack.isEmpty())
		{
			int room = stack.pop();
			if (room == maze.getTargetRoom() )
			{
				// do not continue from the target room, instead backtrace to fill gaps
				continue;
			}
			Vector<Integer> candidates = findAllPossibleNextRooms(maze, room);
			if (candidates.isEmpty())
			{
				// backtrace - no way to go
				continue;
			}
			
			// select next room
			int choice = 0;
			if (candidates.size() > 1)
			{
				choice = randomGenerator.nextInt(candidates.size());
				// keep this room in the stack there are still unvisited walls left
				stack.push(room);
			}
			int wall = candidates.elementAt(choice);
			maze.setWallClosed(wall, true);
			int otherRoom = maze.getOtherRoom(room, wall);
			visitRoom(otherRoom);
			
		}
		
	}
	private Vector<Integer> findAllPossibleNextRooms(IMazeable maze, int room) {
		Vector<Integer> candidates = new Vector<Integer>();
		for(int wall: maze.getWalls(room))
		{
			if (maze.isWallClosed(wall))
			{
				int otherRoom = maze.getOtherRoom(room, wall);
				if (!visitedRooms.get(otherRoom)) {
					candidates.add(otherRoom);
				}						
			}
		}
		return candidates;
	}
	private void visitRoom(int room) {
		assert !visitedRooms.get(room) : "Cannot visit the same room twice";
		visitedRooms.set(room);
		stack.push(room);		
	}
	
	private BitSet visitedRooms;
	private Stack<Integer> stack;

}
