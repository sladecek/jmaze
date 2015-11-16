package com.github.sladecek.maze.jmaze.spheric;

import java.util.Vector;

/* 
 * One half of egg maze. An egg consist of two halfeggs - north and south.
 */
class EggMazeHemisphere {

	/**
	 * 
	 * @return Number of circles parallel to the equator.
	 */
	public int getCircleCnt() {
		return layerRoomCnt.size();
	}

	/**
	 * 
	 * @param circle
	 *            Ordinal number of the circle starting from equator.
	 * @return Number of rooms in the layer that follows i-th circle. The last
	 *         circle (polar) circle may contain several walls but is followed
	 *         by only one polar room.
	 */
	public int getRoomCntAfterCircle(int circle) {
		// polar room has only one room
		if (circle >= getCircleCnt() - 1) {
			return 1;
		} else {
			return layerRoomCnt.get(circle + 1);
		}
	}

	/**
	 * 
	 * @param circle
	 *            Ordinal number of the circle starting from equator.
	 * @return Number of rooms in the layer that precedes i-th circle.
	 */
	public int getRoomCntBeforeCircle(int circle) {
		return getRoomCntAfterCircle(circle - 1);
	}

	/*
	 * 
	 * public int getLogicalRoomCntInLayer(int layer) { // polar room has only
	 * one room if (isPolarLayer(layer)) { return 1; } else { return
	 * getGeometricalRoomCntInLayer(layer); } }
	 * 
	 * public int getGeometricalRoomCntInNextLayer(int layer) { if
	 * (isPolarLayer(layer)) { return getGeometricalRoomCntInLayer(layer); }
	 * else { return getGeometricalRoomCntInLayer(layer+1); } }
	 */

	public boolean isPolarLayer(int layer) {
		return layer == getCircleCnt() - 1;
	}

	/***
	 * Number of rooms in egg layer. Zero index contains number of rooms on the
	 * equator.
	 */
	private Vector<Integer> layerRoomCnt = new Vector<Integer>();

	/***
	 * Id of the first room (Greenwich room) in the layer.
	 */
	private Vector<Integer> greenwichRoom = new Vector<Integer>();

	/*
	 * Axial (x) coordinate of egg layer. Zero index contains zero value - the
	 * equator.
	 */
	private Vector<Double> layerXPosition = new Vector<Double>();

	public EggMazeHemisphere(double poleXPosition) {
		super();
		this.poleXPosition = poleXPosition;
	}

	public int getGreenwichRoom(int layer) {
		return greenwichRoom.get(layer);
	}

	public void addGreenwichRoom(int greenwichRoom) {
		this.greenwichRoom.add(greenwichRoom);
	}

	public double getLayerXPosition(int layer) {
		return layerXPosition.get(layer);
	}

	public void setLayerXPosition(Vector<Double> layerXPosition) {
		this.layerXPosition = layerXPosition;
	}

	public void setLayerRoomCnt(Vector<Integer> layerRoomCnt) {
		this.layerRoomCnt = layerRoomCnt;
	}

	public double getLastLayerXPosition() {
		return getLayerXPosition(getCircleCnt() - 1);

	}

	/**
	 * 
	 * @param circle
	 * @return Number of walls on a circle (geometrical).
	 */
	public int getWallCntOnCircle(int circle) {
		if (circle >= layerRoomCnt.size()) {
			return 1;
		}
		return layerRoomCnt.get(circle);
	}

	public double getPoleXPosition() {
		return poleXPosition;
	}

	private double poleXPosition;

}
