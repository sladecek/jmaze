package com.github.sladecek.maze.jmaze.spheric;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.geometry.SouthNorth;

public class EggMazeTest {

	@Test
	public void testComputeRoomCountsNorth()	
	{
		EggGeometry egg = new EggGeometry(4, 3, 0.5);
		final int equatorCellCnt = 16;
		EggMaze maze = new EggMaze(egg, equatorCellCnt);
		final double baseRoomSize_mm = maze.getBaseRoomSize_mm();
		Vector<Double> layerXPosition = egg.divideMeridian(baseRoomSize_mm, SouthNorth.north);
		Vector<Integer> layerRoomCnt = maze.computeRoomCounts(layerXPosition, equatorCellCnt, baseRoomSize_mm);
		assertEquals(layerXPosition.size(), layerRoomCnt.size());
		assertEquals(5, layerRoomCnt.size());
		assertEquals(16, (int)layerRoomCnt.get(0));
		assertEquals(16, (int)layerRoomCnt.get(1));
		assertEquals(8, (int)layerRoomCnt.get(2));
		assertEquals(8, (int)layerRoomCnt.get(3));
		assertEquals(1, (int)layerRoomCnt.get(4));
	}
	
	@Test
	public void testComputeRoomCountsSouth()	
	{
		EggGeometry egg = new EggGeometry(4, 3, 0.5);
		final int equatorCellCnt = 16;
		EggMaze maze = new EggMaze(egg, equatorCellCnt);
		final double baseRoomSize_mm = maze.getBaseRoomSize_mm();
		Vector<Double> layerXPosition = egg.divideMeridian(baseRoomSize_mm, SouthNorth.south);
		Vector<Integer> layerRoomCnt = maze.computeRoomCounts(layerXPosition, equatorCellCnt, baseRoomSize_mm);
		assertEquals(layerXPosition.size(), layerRoomCnt.size());
		assertEquals(5, layerRoomCnt.size());
		assertEquals(16, (int)layerRoomCnt.get(0));
		assertEquals(16, (int)layerRoomCnt.get(1));
		assertEquals(16, (int)layerRoomCnt.get(2));
		assertEquals(16, (int)layerRoomCnt.get(3));
		assertEquals(1, (int)layerRoomCnt.get(4));
	}

}
