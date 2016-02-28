package com.github.sladecek.maze.jmaze.spheric;


import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

public class EggMazeHemisphereTest {

	@Test
	public void testGetCircleCnt() {
		Vector<Integer> layerRoomCnt = new Vector<Integer>();
		layerRoomCnt.addElement(4);
		layerRoomCnt.addElement(8);

		EggMazeHemisphere ehm = new EggMazeHemisphere(27);
		ehm.setLayerRoomCnt(layerRoomCnt);
		assertEquals(2, ehm.getCircleCnt());
	}

	@Test
	public void testGetRoomCntAfterCircle() {
		Vector<Integer> layerRoomCnt = new Vector<Integer>();
		layerRoomCnt.addElement(4);
		layerRoomCnt.addElement(8);

		EggMazeHemisphere ehm = new EggMazeHemisphere(27);
		ehm.setLayerRoomCnt(layerRoomCnt);
		assertEquals(8, ehm.getRoomCntAfterCircle(0));
		assertEquals(1, ehm.getRoomCntAfterCircle(1));
	}

	@Test
	public void testGetRoomCntBeforeCircle() {
		Vector<Integer> layerRoomCnt = new Vector<Integer>();
		layerRoomCnt.addElement(4);
		layerRoomCnt.addElement(8);

		EggMazeHemisphere ehm = new EggMazeHemisphere(27);
		ehm.setLayerRoomCnt(layerRoomCnt);
		assertEquals(4, ehm.getRoomCntBeforeCircle(0));
		assertEquals(8, ehm.getRoomCntBeforeCircle(1));
		assertEquals(1, ehm.getRoomCntBeforeCircle(2));

	}

	@Test
	public void testIsPolarLayer() {
		Vector<Integer> layerRoomCnt = new Vector<Integer>();
		layerRoomCnt.addElement(4);
		layerRoomCnt.addElement(8);

		EggMazeHemisphere ehm = new EggMazeHemisphere(27);
		ehm.setLayerRoomCnt(layerRoomCnt);
		assertEquals(false, ehm.isPolarLayer(0));
		assertEquals(true, ehm.isPolarLayer(1));
	}

	@Test
	public void testGetGreenwichRoom() {
		Vector<Integer> layerRoomCnt = new Vector<Integer>();
		layerRoomCnt.addElement(4);
		layerRoomCnt.addElement(8);
			
		EggMazeHemisphere ehm = new EggMazeHemisphere(27);
		ehm.setLayerRoomCnt(layerRoomCnt);
		ehm.addGreenwichRoom(55);
		ehm.addGreenwichRoom(66);
		assertEquals(55, ehm.getGreenwichRoom(0));
		assertEquals(66, ehm.getGreenwichRoom(1));
	}

	@Test
	public void testGetLayerXPosition() {
		Vector<Integer> layerRoomCnt = new Vector<Integer>();
		layerRoomCnt.addElement(4);
		layerRoomCnt.addElement(8);

		Vector<Double> layerX = new Vector<Double>();
		layerX.addElement(1.1);
		layerX.addElement(2.2);


		EggMazeHemisphere ehm = new EggMazeHemisphere(27);
		ehm.setLayerRoomCnt(layerRoomCnt);
		ehm.setLayerXPosition(layerX);
		assertEquals(1.1, ehm.getLayerXPosition(0), 0.0001);
		assertEquals(2.2, ehm.getLayerXPosition(1), 0.0001);

		assertEquals(2.2, ehm.getLastLayerXPosition(), 0.0001);

	}


	@Test
	public void testGetWallCntOnCircle() {
		Vector<Integer> layerRoomCnt = new Vector<Integer>();
		layerRoomCnt.addElement(4);
		layerRoomCnt.addElement(8);

		EggMazeHemisphere ehm = new EggMazeHemisphere(27);
		ehm.setLayerRoomCnt(layerRoomCnt);
		assertEquals(4, ehm.getWallCntOnCircle(0));
		assertEquals(8, ehm.getWallCntOnCircle(1));
		assertEquals(1, ehm.getWallCntOnCircle(2));
	}

	@Test
	public void testGetPoleXPosition() {
		EggMazeHemisphere ehm = new EggMazeHemisphere(27);
		assertEquals(27, ehm.getPoleXPosition(),0.001);
	}

}
