package com.github.sladecek.maze.jmaze.voronoi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.Voronoi;

/***
 * 
 * Given a list of points (sites) in plane, partition plane into polygons so
 * that each point in the plane belongs to the nearest site.
 *
 * Implemented using simplevoronoi library.
 */
public class VoronoiAlgorithm {

	/*
	 * 
	 */
	public class Edge {

		public Edge(int site1, int site2, double x1, double x2, double y1, double y2) {
			super();
			this.site1 = site1;
			this.site2 = site2;
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
			this.ix1 = (int)Math.floor(x1);
			this.ix2 = (int)Math.floor(x2);
			this.iy1 = (int)Math.floor(y1);
			this.iy2 = (int)Math.floor(y2);
		}

		public int getSite1() {
			return site1;
		}

		public int getSite2() {
			return site2;
		}

		public double getX1() {
			return x1;
		}

		public double getX2() {
			return x2;
		}

		public double getY1() {
			return y1;
		}

		public double getY2() {
			return y2;
		}

		public int getIx1() {
			return ix1;
		}

		public int getIx2() {
			return ix2;
		}

		public int getIy1() {
			return iy1;
		}

		public int getIy2() {
			return iy2;
		}

		private int site1;
		private int site2;
		private double x1;
		private double x2;
		private double y1;
		private double y2;
		private int ix1;
		private int ix2;
		private int iy1;
		private int iy2;
	}

	public List<Edge> computeEdges(PointsInRectangle p1) {
		// TODO smazat
		HashSet<Long> set = new HashSet();
		Voronoi v = new Voronoi(0.00001f);
		List<GraphEdge> allEdges = v.generateVoronoi(p1.getRoomCenterX(), p1.getRoomCenterY(), 0, p1.getWidth() - 1, 
				0, p1.getHeight() - 1);

		ArrayList<Edge> result = new ArrayList<Edge>();
		
		for (GraphEdge ge : allEdges) {
			/*
			 * TODO smazat - older simplevoronoi
			 */
			Long k = ge.site1 * 1000000l + ge.site2;
			if (ge.site1 < ge.site2) {
				k = ge.site2 * 1000000l + ge.site1;
			}
			if (set.contains(k)) {
				continue;
			}

			set.add(k);

			// Voronoi algorithm generates nonsensical edges with very small
			// length that would
			// otherwise be outside the rectangle. There are also estheticall
			// reasons to filter
			// short edges out.
			double dx = (ge.x1 - ge.x2);
			double dy = (ge.y1 - ge.y2);
			double lsq = dx * dx + dy * dy;
			final double minimalLength = 0.01;
			boolean isShort = lsq < minimalLength * minimalLength;
			LOGGER.info("voronoi edge room1=" + ge.site1 + " room2=" + ge.site2 + " x1=" + ge.x1 + " y1=" + ge.y1
					+ " x2=" + ge.x2 + " y2=" + ge.y2 + " isShort=" + isShort);

			if (isShort) {
				continue;
			}
			
			Edge e = new Edge(ge.site1, ge.site2, ge.x1, ge.x2, ge.y1, ge.y2);
			result.add(e);
		}
		return result;

	}

	private static final Logger LOGGER = Logger.getLogger("maze.jmaze");

}
