package com.github.sladecek.maze.jmaze.print3d;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.TestUtilities;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

public class BlockMakerBaseTest {

	@Test
	public void testPrintPolyhedron() {

		ShapeContainer shapes = null;
		Maze3DSizes sizes = new Maze3DSizes();
		IPrintStyle colors = new DefaultPrintStyle();
		double approxRoomSizeInmm = 10;

		BlockMakerBase bmb = new BlockMakerBase(shapes, sizes, colors, approxRoomSizeInmm) {
		};
		bmb.printPolyhedron(TestUtilities.buildPolyhedronForTest(1, 2, 3, 1), "comment", colors.getBaseColor());

		List<Block> list = new ArrayList<Block>();
		bmb.getBlocks().iterator().forEachRemaining(list::add);
		assertEquals(1, list.size());
	}
/*
	@Test
	public void testPrintWallElements() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintInnerWallElement() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintFloorWithHoleOneRoom() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeFloorSegmentWest() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeFloorSegmentEast() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeFloorSegmentSouth() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeFloorSegmentNorth() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillHoleInTheFloorOneRoom() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHolePoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testMapPointWithZ() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFloorPoint() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintMark() {
		fail("Not yet implemented");
	}
*/
}
