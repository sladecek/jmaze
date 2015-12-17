package com.github.sladecek.maze.jmaze.spheric;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.geometry.SouthNorth;

public class EggMazeTest {

	@Test
	public void testComputeRoomCountsNorth()	
	{
		SouthNorth hemisphere = SouthNorth.north;
		Vector<Integer> layerRoomCnt = generateTestHemisphere(hemisphere, 16);
		assertEquals(4, layerRoomCnt.size());
		assertEquals(16, (int)layerRoomCnt.get(0));
		assertEquals(16, (int)layerRoomCnt.get(1));
		assertEquals(8, (int)layerRoomCnt.get(2));
		assertEquals(8, (int)layerRoomCnt.get(3));

	}

	@Test
	public void testComputeRoomCountsSouth()	
	{
		SouthNorth hemisphere = SouthNorth.south;
		Vector<Integer> layerRoomCnt = generateTestHemisphere(hemisphere, 16);
		assertEquals(4, layerRoomCnt.size());
		assertEquals(16, (int)layerRoomCnt.get(0));
		assertEquals(16, (int)layerRoomCnt.get(1));
		assertEquals(16, (int)layerRoomCnt.get(2));
		assertEquals(16, (int)layerRoomCnt.get(3));

	}
	
	@Test
	public void testComputeRoomCountsNorthSmall()	
	{
		SouthNorth hemisphere = SouthNorth.north;
		Vector<Integer> layerRoomCnt = generateTestHemisphere(hemisphere, 8);
		assertEquals(2, layerRoomCnt.size());
		assertEquals(8, (int)layerRoomCnt.get(0));
		assertEquals(8, (int)layerRoomCnt.get(1));
	}

	@Test
	public void testComputeRoomCountsSouthSmall()	
	{
		SouthNorth hemisphere = SouthNorth.south;
		Vector<Integer> layerRoomCnt = generateTestHemisphere(hemisphere, 8);
		assertEquals(2, layerRoomCnt.size());
		assertEquals(8, (int)layerRoomCnt.get(0));
		assertEquals(8, (int)layerRoomCnt.get(1));

	}
	
	private Vector<Integer> generateTestHemisphere(SouthNorth hemisphere, int equatorCellCnt ) {
		EggGeometry egg = new EggGeometry(4, 3, 0.5);

		EggMaze maze = new EggMaze(egg, equatorCellCnt);
		final double baseRoomSizeInmm = maze.getBaseRoomSizeInmm();		
		Vector<Double> layerXPosition = egg.divideMeridian(baseRoomSizeInmm, hemisphere);
		Vector<Integer> layerRoomCnt = maze.computeRoomCounts(layerXPosition, equatorCellCnt, baseRoomSizeInmm);
		return layerRoomCnt;
	}
	
	@Test
	public void testMazeConstructionSmall()
	{
		/*
		EggGeometry egg = new EggGeometry(4, 3, 0.5);
		EggMaze maze = new EggMaze(egg, 8);
		
		int roomCount = 0;
		int meridianWallCount = 0;
		int parallelWallCount = 0;
		/TODO
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
				
				assert(ws.getX1() >= -1);
				assert(ws.getX1() <= 2);
				assert(ws.getX2() >= -1);
				assert(ws.getX2() <= 2);
				
				assert(ws.getY1() >= 0);
				assert(ws.getY1() < 8);
				assert(ws.getY2() >= 0);
				assert(ws.getY2() < 8);
			}			
		}
			
		assertEquals(4+8+8+8+4, roomCount);
		assertEquals(16, meridianWallCount);
		assertEquals(24, parallelWallCount);
		*/
	}
	/* TODO

	@Test
	public void testMazeConstruction()
	{
		final int equator_cells = 32; // must be power of 2
		final double ellipseMajorInmm = 30;
		final double ellipseMinorInmm = 20;
		final double eggCoef = 0.2;
		
		EggGeometry egg = new EggGeometry(ellipseMajorInmm, ellipseMinorInmm, eggCoef);
		EggMaze maze = new EggMaze(egg, equator_cells);
		

		for (IMazeShape s: maze.getShapes()) {
			System.out.println(s);
			 if (s.getShapeType() == ShapeType.innerWall) {
				WallShape ws = (WallShape)s;
				if (ws.getX1() != ws.getX2()) {
				
				
				assert(ws.getX1() >= -1);
				assert(ws.getX1() <= 2);
				assert(ws.getX2() >= -1);
				assert(ws.getX2() <= 2);
				
				assert(ws.getY1() >= 0);
				assert(ws.getY1() < 8);
				assert(ws.getY2() >= 0);
				assert(ws.getY2() < 8);
			}			
		}
	
	}
	}
	*/
}
