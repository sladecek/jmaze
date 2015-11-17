package com.github.sladecek.maze.jmaze.spheric;

import static org.junit.Assert.assertEquals;

import java.nio.MappedByteBuffer;
import java.util.Vector;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.WallShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;

public class EggMazeAllRoomsTest {

	/* TODO
	@Test
	public void testMazeConstruction()
	{
		EggGeometry egg = new EggGeometry(4, 2, 0);
		EggMaze maze = new EggMaze(egg, 8);
		
		int roomCount = 0;
		int meridianWallCount = 0;
		int parallelWallCount = 0;
		
		for (IMazeShape s: maze.getShapes()) {
			System.out.println(s);
			if (s.getShapeType() == ShapeType.nonHole) {
				roomCount++;
			} else if (s.getShapeType() == ShapeType.innerWall) {
				WallShape ws = (WallShape)s;
				if (ws.getX1() == ws.getX2()) {
					parallelWallCount++;
				} else {
					meridianWallCount++;
				}
				
		
			}			
		}
		
	}
	*/

	
}
