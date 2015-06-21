package com.github.sladecek.maze.jmaze.spheric;

import java.util.Vector;

/*
 * 
 * One half of egg maze. An egg consist of two halfeggs - north and south.
 *
 */
class EggMazeHemisphere {
	
	public int getLayerCnt() {
		return  layerRoomCnt.size();
	}

	public int getGeometricalRoomCntInLayer(int layer) {
		return layerRoomCnt.get(layer);
	}
	
	
	public int getLogicalRoomCntInLayer(int layer) {
		// polar room has only one room
		if (isPolarLayer(layer)) {
			return 1;
		} else {
			return getGeometricalRoomCntInLayer(layer);
		}
	}
	
	public int getGeometricalRoomCntInNextLayer(int layer) {
		if (isPolarLayer(layer)) {
			return getGeometricalRoomCntInLayer(layer);
		} else {
			return getGeometricalRoomCntInLayer(layer+1);
		}
	}
	

	
	public boolean isPolarLayer(int layer) {
		return layer == getLayerCnt()-1;
	}

	/***
	 * Number of rooms in egg layer. Zero index contains number of rooms on the equator.
	 */
	private Vector<Integer> layerRoomCnt = new Vector<Integer>();
	
	/***
	 * Id of the first room (Greenwich room) in the layer.
	 */
	private Vector<Integer> greenwichRoom = new Vector<Integer>();

	
	/*
	 * Axial (x) coordinate of egg layer. Zero index contains zero value - the equator.
	 */
	private Vector<Double> layerXPosition = new Vector<Double>();


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
		return getLayerXPosition(getLayerCnt()-1);
		
	}
	
	
	


}
