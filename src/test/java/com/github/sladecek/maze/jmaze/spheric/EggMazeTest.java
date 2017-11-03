package com.github.sladecek.maze.jmaze.spheric;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.WallShape;
import org.junit.Test;

import com.github.sladecek.maze.jmaze.geometry.SouthNorth;

public class EggMazeTest {



	@Test
	public void testComputeRoomCountsNorth()	
	{
		SouthNorth hemisphere = SouthNorth.north;
		ArrayList<Integer> layerRoomCnt = generateTestHemisphere(hemisphere, 16);
		assertEquals(3, layerRoomCnt.size());
		assertEquals(16, (int)layerRoomCnt.get(0));
		assertEquals(16, (int)layerRoomCnt.get(1));
		assertEquals(16, (int)layerRoomCnt.get(2));
	}

	@Test
	public void testComputeRoomCountsSouth()	
	{
		SouthNorth hemisphere = SouthNorth.south;
		ArrayList<Integer> layerRoomCnt = generateTestHemisphere(hemisphere, 16);
		assertEquals(3, layerRoomCnt.size());
		assertEquals(16, (int)layerRoomCnt.get(0));
		assertEquals(16, (int)layerRoomCnt.get(1));
		assertEquals(16, (int)layerRoomCnt.get(2));

	}
	
	@Test
	public void testComputeRoomCountsNorthSmall()	
	{
		SouthNorth hemisphere = SouthNorth.north;
		ArrayList<Integer> layerRoomCnt = generateTestHemisphere(hemisphere, 8);
		assertEquals(2, layerRoomCnt.size());
		assertEquals(8, (int)layerRoomCnt.get(0));
		assertEquals(8, (int)layerRoomCnt.get(1));
	}

	@Test
	public void testComputeRoomCountsSouthSmall()	
	{
		SouthNorth hemisphere = SouthNorth.south;
		ArrayList<Integer> layerRoomCnt = generateTestHemisphere(hemisphere, 8);
		assertEquals(2, layerRoomCnt.size());
		assertEquals(8, (int)layerRoomCnt.get(0));
		assertEquals(8, (int)layerRoomCnt.get(1));

	}

	private ArrayList<Integer> generateTestHemisphere(SouthNorth hemisphere, int equatorCellCnt ) {

		EggMaze maze = createEggMaze(equatorCellCnt);

		maze.buildMazeGraphAndShapes();
		final double baseRoomSizeInmm = maze.getBaseRoomSizeInmm();		
		ArrayList<Double> layerXPosition = maze.getGeometry().divideMeridian(baseRoomSizeInmm, hemisphere);
        return maze.computeRoomCounts(layerXPosition, equatorCellCnt, baseRoomSizeInmm);
	}

	private EggMaze createEggMaze(int equatorCellCnt) {
		EggMaze maze = new EggMaze();
		MazeProperties p = new EggMazeDescription().getDefaultProperties();
		p.put("ellipseMajor", 4.0);
		p.put("ellipseMinor", 3.0);
		p.put("eggness", 0.5);
		p.put("equatorCells", equatorCellCnt);
		maze.setProperties(p);
		return maze;
	}

	@Test
	public void testMazeConstructionSmall()
	{


		EggMaze maze = createEggMaze(8);
		maze.buildMazeGraphAndShapes();

		int roomCount = 0;
		int meridianWallCount = 0;
		int parallelWallCount = 0;

		for (IMazeShape s: maze.getAllShapes().getShapes())  {

			if (s instanceof FloorShape) {
				roomCount++;
			} else if (s instanceof WallShape) {
				WallShape ws = (WallShape)s;
				if (ws.getX1() == ws.getX2()) {
					parallelWallCount++;
				} else {
					meridianWallCount++;
				}
				
				assertTrue(ws.getX1() >= -10);
				assertTrue(ws.getX1() <= 20);
				assertTrue(ws.getX2() >= -10);
				assertTrue(ws.getX2() <= 20);
				
				assertTrue(ws.getY1() >= 0);
				assertTrue(ws.getY1() < 80);
				assertTrue(ws.getY2() >= 0);
				assertTrue(ws.getY2() < 80);
			}			
		}
			
		assertEquals(4+8+8+8+4, roomCount);
		assertEquals(16, meridianWallCount);
		assertEquals(24, parallelWallCount);

	}

}
