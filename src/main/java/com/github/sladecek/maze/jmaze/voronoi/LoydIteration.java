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
			
			
	    	PolygonProperties pp = new PolygonProperties(e, input.getRoomCount());
	    	pp.compute();
	    	

/* TODO
				double x1 = Math.max(0, Math.min(ge.x1, width-1));
				double x2 = Math.max(0, Math.min(ge.x2, width-1));
				double y1 = Math.max(0, Math.min(ge.y1, height-1));
				double y2 = Math.max(0, Math.min(ge.y2, height-1));
				double da = e.getX1() * e.getY2() - e.getX2() * e.getY1();
				double dcx = (e.getX1()+e.getX2())*da;
				double dcy = (e.getY1()+e.getY2())*da;
										
				area[e.getSite1()] += da/2;
				area[e.getSite2()] += da/2;

				cx[e.getSite1()] += dcx;
				cx[e.getSite2()] += dcx;
				cy[e.getSite1()] += dcy;
				cy[e.getSite2()] += dcy;
			}
*/			
			
			for (int i= 0; i < output.getRoomCount(); i++) {
				
				output.setRoomCenterX(i,  pp.getCenterOfGravityX().get(i));
				output.setRoomCenterY(i,  pp.getCenterOfGravityY().get(i));

			}
		}

	}

	private PointsInRectangle input;
	private PointsInRectangle output;
	private int numberOfIterations;

}