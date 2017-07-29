package com.github.sladecek.maze.jmaze.maze;

import static org.junit.Assert.assertEquals;

import com.github.sladecek.maze.jmaze.shapes.*;
import org.junit.Before;
import org.junit.Test;


public class MazeTest {

	Maze maze;

	@Before
	public void setUp() throws Exception {
		maze = new Maze();
	}

	/* TODO opravit starrt stop
	@Test
	public void applyRealizationShouldReturnClosedWallsOnly() {		
		MazeRealization realization = new MazeRealization(10);
		
		WallShape s1 = new WallShape(ShapeType.innerWall, 1, 2, 3, 4);
		maze.addShape(s1);
		maze.linkShapeToId(s1, 1);
		realization.setWallClosed(1, true);
		
		WallShape s2 = new WallShape(ShapeType.innerWall, 2, 2, 3, 4);
		maze.addShape(s2);
		maze.linkShapeToId(s2, 2);
		realization.setWallClosed(2, false);

		WallShape s3 = new WallShape(ShapeType.innerWall, 3, 2, 3, 4);
		maze.addShape(s3);
		maze.linkShapeToId(s3, 3);
		realization.setWallClosed(3, true);
		
		ShapeContainer c = maze.applyRealization(realization);
		Vector<IMazeShape2D> s = c.getShapes();
		assertEquals(2, s.size());
		
		assertEquals(ShapeType.innerWall, s.get(0).getShapeType());
		assertEquals(1, ((WallShape)s.get(0)).getY1());

		assertEquals(ShapeType.innerWall, s.get(1).getShapeType());
		assertEquals(3, ((WallShape)s.get(1)).getY1());		
	}

	@Test
	public void applyRealizationShouldReturnStartRoom() {		
		FloorShape fs = new FloorShape(new Point2DInt(0, 0), false, 0,0);
		maze.linkRoomToFloor(2, fs);
		maze.setStartRoom(2);
		
		MazeRealization realization = new MazeRealization(10);
		ShapeContainer c = maze.applyRealization(realization);
		Vector<IMazeShape2D> s = c.getShapes();
		assertEquals(1, s.size());
		assertEquals(ShapeType.startRoom, s.get(0).getShapeType());
		
	}

	@Test
	public void applyRealizationShouldReturnTargetRoom() {		
		FloorShape fs = new FloorShape(new Point2DInt(0, 0), false,0,0);
		maze.linkRoomToFloor(2, fs);
		maze.setTargetRoom(2);
		
		MazeRealization realization = new MazeRealization(10);
		ShapeContainer c = maze.applyRealization(realization);
		Vector<IMazeShape2D> s = c.getShapes();
		assertEquals(1, s.size());
		assertEquals(ShapeType.targetRoom, s.get(0).getShapeType());
		
	}

	@Test
	public void applyRealizationShouldReturnSolutionWithoutStartAndTargetRooms() {		
		FloorShape fsS = new FloorShape(new Point2DInt(0, 0), false,0,0);
		maze.linkRoomToFloor(0, fsS);
		maze.setStartRoom(0);

		FloorShape fs = new FloorShape(new Point2DInt(0, 0), false,0,0);
		maze.linkRoomToFloor(1, fs);

		FloorShape fsT = new FloorShape(new Point2DInt(0, 0), false,0,0);
		maze.linkRoomToFloor(7, fsT);
		maze.setTargetRoom(7);

		MazeRealization realization = new MazeRealization(10);
		Vector<Integer> sol = new Vector<Integer>();
		sol.addElement(0);
		sol.addElement(1);
		sol.addElement(7);
		realization.setSolution(sol);
		ShapeContainer c = maze.applyRealization(realization);
		Vector<IMazeShape2D> s = c.getShapes();
		assertEquals(3, s.size());
		assertEquals(ShapeType.startRoom, s.get(0).getShapeType());
		assertEquals(ShapeType.targetRoom, s.get(1).getShapeType());
		assertEquals(ShapeType.solution, s.get(2).getShapeType());
		
	}

	
	@Test
	public void testGetShapes() {
		maze.addShape(new FloorShape(new Point2DInt(1, 1), false,0,0));
		Vector<IMazeShape2D> s = maze.getShapes().getShapes();
		assertEquals(1, s.size());
	}

*/
	@Test
	public void testSetContext() {
		ShapeContext c = new ShapeContext(true, 1, 2);
		maze.setContext(c);
		assertEquals(c,  maze.getContext());
	}

}
