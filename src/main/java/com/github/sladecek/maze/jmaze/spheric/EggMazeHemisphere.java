package com.github.sladecek.maze.jmaze.spheric;

import java.util.Vector;

/*
 * 
 * One half of egg maze. An egg consist of two halfeggs - north and south.
 *
 */
class EggMazeHemisphere {

	/***
	 * Number of rooms in egg layer. Zero index contains number of rooms on the equator.
	 */
	public Vector<Integer> layerRoomCnt = new Vector<Integer>();
	
	/***
	 * Id of the first room (Greenwich room) in the layer.
	 */
	public Vector<Integer> greenwichRoom = new Vector<Integer>();

	
	/*
	 * Axial (x) coordinate of egg layer. Zero index contains zero value - the equator.
	 */
	public Vector<Double> layerXPosition = new Vector<Double>();
	
	
	


}
