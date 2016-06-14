package com.github.sladecek.maze.jmaze.voronoi;

import java.util.List;

import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.PolygonProperties;

/**
 * Loyd iterative algorithm. Works on a set of points in a rectangle. Makes the
 * distribution o points smoother by replacing every point with a centroid of
 * its Voronoi rectangle.
 */
public class LoydIteration {

	public LoydIteration(PointsInRectangle input, int numberOfIterations) {
		super();
		this.input = input;
		this.numberOfIterations = numberOfIterations;
		computeOutput();
	}

	public PointsInRectangle getInput() {
		return input;
	}

	public PointsInRectangle getOutput() {
		return output;
	}

	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	private void computeOutput() {
		output = PointsInRectangle.newCopyOf(input);
		for (int l = 0; l < numberOfIterations; l++) {

			List<GraphEdge> e= new VoronoiAlgorithm().computeEdges(output);
			for (GraphEdge ee: e) System.out.println(ee);
			
			
	    	PolygonProperties pp = new PolygonProperties(e, input.getRoomCount());
	    	pp.compute();
	    	


			
			for (int i= 0; i < output.getRoomCount(); i++) {
				double cx = pp.getCenterOfGravityX().get(i);
				double cy = pp.getCenterOfGravityY().get(i);
				output.setRoomCenterX(i,  cx);
				output.setRoomCenterY(i,  cy);
				System.out.println("it="+l+" i="+i+" cx="+cx+" cy="+cy);

			}
		}

	}

	private PointsInRectangle input;
	private PointsInRectangle output;
	private int numberOfIterations;

}
