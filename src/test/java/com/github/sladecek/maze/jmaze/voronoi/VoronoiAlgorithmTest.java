package com.github.sladecek.maze.jmaze.voronoi;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import be.humphreys.simplevoronoi.GraphEdge;

public class VoronoiAlgorithmTest {

	@Test
	public void testComputeEdges() {
		final int width = 10;
		final int height = 10;
		final int roomCount =2;
		PointsInRectangle pts = new PointsInRectangle(width, height, roomCount);
		pts.setRoomCenterX(0, 4);
		pts.setRoomCenterX(1, 6);
		pts.setRoomCenterY(0, 5);
		pts.setRoomCenterY(1, 5);
		
		VoronoiAlgorithm va = new VoronoiAlgorithm();
		List<GraphEdge> e = va.computeEdges(pts);
		assertEquals(7, e.size());
		long insideEdgeCount = e.stream().filter(edge -> edge.site1 != -1 && edge.site2 != -1).count();
		assertEquals(1, insideEdgeCount);
	}

}
