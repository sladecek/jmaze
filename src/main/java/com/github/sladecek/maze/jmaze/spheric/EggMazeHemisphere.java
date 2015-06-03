package com.github.sladecek.maze.jmaze.spheric;

import java.util.Vector;

/*
 * 
 * One half of egg maze. An egg consist of two halfeggs - north and south.
 *
 */
class EggMazeHemisphere {

	/***
	 * Number of cells in egg layer. Zero index contains number of cells on the equator.
	 */
	public Vector<Integer> layerCellCnt = new Vector<Integer>();
	
	/***
	 * Id of the first room (Greenwich room) in the layer.
	 */
	public Vector<Integer> greenwichWall = new Vector<Integer>();

	
	/*
	 * Axial (x) coordinate of egg layer. Zero index contains zero value - the equator.
	 */
	public Vector<Double> layerXPosition = new Vector<Double>();
	
	
	


}
