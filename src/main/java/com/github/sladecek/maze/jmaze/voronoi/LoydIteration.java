package com.github.sladecek.maze.jmaze.voronoi;

import java.util.ArrayList;
import java.util.List;

import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.Polygon;
import be.humphreys.simplevoronoi.PolygonSeparator;

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
/*
			System.out.println("fig, ax = plt.subplots()");

			System.out.println("plt.ylim([-100,600])");
			System.out.println("plt.xlim([-100,600])");

			System.out.println("plt.plot([0,0], [0,499], color=\"gray\")");
			System.out.println("plt.plot([0,499], [499,499], color=\"gray\")");
			System.out.println("plt.plot([499,499], [499,0], color=\"gray\")");
			System.out.println("plt.plot([499,0], [0,0], color=\"gray\")");

			printOutput();
			
			System.out.println("plt.plot(x, y, 'o')");
			System.out.println("plt.title(\"Loyd "+l+"\")");
*/
			List<GraphEdge> e = new VoronoiAlgorithm().computeEdges(output);

			
		//	for (GraphEdge ee : e)
		//		System.out.println("plt.plot(["+ee.x1+","+ee.y1+"], ["+ee.x2+","+ee.y2+"], color=\"green\")");
			ArrayList<Polygon> polygons = PolygonSeparator.makePolygons(e);
			for (Polygon p : polygons) {
				Polygon.CenterOfGravity cog = p.computeAreaAndCog();
				int i = p.getCenterSite();
				if (i >= 0) {
					output.setRoomCenterX(i, cog.getX());
					output.setRoomCenterY(i, cog.getY());
					//System.out.println("it=" + l + " i=" + i + " cog=" + cog);
				}

			}
	/*		printOutput();
			
			System.out.println("plt.plot(x, y, 'x')");
			
			
			System.out.println("plt.show()");
		*/	
		}

	}
/*
	private void printOutput() {
		System.out.print("x=[");
		for (int i = 0; i < output.getRoomCount(); i++) {
			System.out.print(output.getRoomCenterX()[i] + ", ");
		}
		System.out.println("];");
		System.out.print("y=[");
		for (int i = 0; i < output.getRoomCount(); i++) {
			System.out.print(output.getRoomCenterY()[i] + ", ");
		}
		System.out.println("];");
	}
*/
	private PointsInRectangle input;
	private PointsInRectangle output;
	private int numberOfIterations;

}
