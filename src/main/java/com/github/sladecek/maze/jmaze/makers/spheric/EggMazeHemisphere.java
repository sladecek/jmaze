package com.github.sladecek.maze.jmaze.makers.spheric;

import java.util.ArrayList;

/* 
 * One half of an egg maze. An egg consist of two half-eggs - north and south.
 */
class EggMazeHemisphere {

	public EggMazeHemisphere(double poleXPosition) {
		super();
		this.poleXPosition = poleXPosition;
	}

	/**
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

	public boolean isPolarLayer(int layer) {
		return layer == getCircleCnt() - 1;
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

	public void setLayerXPosition(ArrayList<Double> layerXPosition) {
		this.layerXPosition = layerXPosition;
	}

	public void setLayerRoomCnt(ArrayList<Integer> layerRoomCnt) {
		this.layerRoomCnt = layerRoomCnt;
	}

	public double getLastLayerXPosition() {
		return getLayerXPosition(getCircleCnt() - 1);

	}

	/**
	 * 
	 * @param circle required circle.
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

	/***
	 * Number of rooms in egg layer. Zero index contains number of rooms on the
	 * equator.
	 */
	private ArrayList<Integer> layerRoomCnt = new ArrayList<>();

	/***
	 * Id of the first room (Greenwich room) in the layer.
	 */
	private final ArrayList<Integer> greenwichRoom = new ArrayList<>();

	/*
	 * Axial (x) coordinate of egg layer. Zero index contains zero value - the
	 * equator.
	 */
	private ArrayList<Double> layerXPosition = new ArrayList<>();
	private final double poleXPosition;

}
