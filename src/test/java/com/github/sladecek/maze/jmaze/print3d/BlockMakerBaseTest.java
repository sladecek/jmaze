package com.github.sladecek.maze.jmaze.print3d;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.TestUtilities;
import com.github.sladecek.maze.jmaze.geometry.EastWest;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;
import com.github.sladecek.maze.jmaze.geometry.UpDown;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

public class BlockMakerBaseTest {

	class Maze3DMapperStub implements IMaze3DMapper {

		@Override
		public Point3D mapPoint(int cellY, int cellX, double offsetY, double offsetX, double offsetZ) {
			return new Point3D(cellX + offsetX, cellY + offsetY, offsetZ);
		}

		@Override
		public Point3D mapCorner(int cellX, EastWest ew, UpDown ud, SouthNorth snWall, SouthNorth snEdge) {
			throw new InternalError("Not impleemented.");
		}

		@Override
		public int getStepY(int y, int x) {
			return 1;
		}

	};

	@Test
	public void testPrintPolyhedron() {

		List<Block> list = testOneOperation(bmb -> {
			bmb.printPolyhedron(TestUtilities.buildPolyhedronForTest(1, 2, 3, 1), "comment", bmb.style.getBaseColor());
		});
		assertEquals(1, list.size());
	}

	private List<Block> testOneOperation(Consumer<BlockMakerBase> operation) {
		BlockMakerBase bmb = constructBlockMaker();
		operation.accept(bmb);
		List<Block> list = new ArrayList<Block>();
		bmb.getBlocks().iterator().forEachRemaining(list::add);
		return list;
	}

	private BlockMakerBase constructBlockMaker() {
		ShapeContainer shapes = null;
		Maze3DSizes sizes = new Maze3DSizes();
		IPrintStyle colors = new DefaultPrintStyle();
		double approxRoomSizeInmm = 10;
		BlockMakerBase bmb = new BlockMakerBase(shapes, sizes, colors, approxRoomSizeInmm) {
		};
		bmb.setMaze3dMapper(new Maze3DMapperStub());
		return bmb;
	}

	/*
	 * @Test public void testPrintWallElements() { fail("Not yet implemented");
	 * }
	 */
	@Test
	public void testPrintInnerWallElement() {
		List<Block> list = testOneOperation(bmb -> {
			int y1 = 1;
			int y2 = 1;
			int x1 = 1;
			int x2 = 1;
			double wx = 1;
			double wy = 1;
			bmb.printInnerWallElement(y1, y2, x1, x2, wy, wx, "comment", bmb.style.getBaseColor());
		});
		assertEquals(1, list.size());
		Block b = list.get(0);
		assertEquals(false, b.isMark());
		assertEquals("Point [x=0.0, y=0.0, z=0.0]", b.getPolyhedron().get(0).toString());
		assertEquals("Point [x=0.0, y=0.0, z=5.0]", b.getPolyhedron().get(1).toString());
		assertEquals("Point [x=0.0, y=2.0, z=0.0]", b.getPolyhedron().get(2).toString());
		assertEquals("Point [x=0.0, y=2.0, z=5.0]", b.getPolyhedron().get(3).toString());
		assertEquals("Point [x=2.0, y=0.0, z=0.0]", b.getPolyhedron().get(4).toString());
		assertEquals("Point [x=2.0, y=0.0, z=5.0]", b.getPolyhedron().get(5).toString());
		assertEquals("Point [x=2.0, y=2.0, z=0.0]", b.getPolyhedron().get(6).toString());
		assertEquals("Point [x=2.0, y=2.0, z=5.0]", b.getPolyhedron().get(7).toString());
	}

	@Test
	public void testPrintFloorWithHoleOneRoom() {
		List<Block> list = testOneOperation(bmb -> {
			int y1 = 1;
			int x1 = 1;
			bmb.printFloorWithHoleOneRoom(y1, x1);
		});
		assertEquals(4, list.size());
	}

	@Test
	public void testMakeFloorSegmentWest() {
		BlockMakerBase bmb = constructBlockMaker();
		int y1 = 10;
		int x1 = 20;
		ArrayList<Point3D> p = bmb.makeFloorSegmentWest(y1, x1);
		assertEquals("Point [x=20.0, y=10.0, z=0.0]", p.get(0).toString());
		assertEquals("Point [x=20.0, y=10.0, z=1.0]", p.get(1).toString());
		assertEquals("Point [x=20.0, y=11.0, z=0.0]", p.get(2).toString());
		assertEquals("Point [x=20.0, y=11.0, z=1.0]", p.get(3).toString());
		assertEquals("Point [x=21.0, y=10.0, z=0.0]", p.get(4).toString());
		assertEquals("Point [x=21.0, y=10.0, z=1.0]", p.get(5).toString());
		assertEquals("Point [x=21.0, y=11.0, z=0.0]", p.get(6).toString());
		assertEquals("Point [x=21.0, y=11.0, z=1.0]", p.get(7).toString());
	}

	// TODO proc je east a west stejne
	@Test
	public void testMakeFloorSegmentEast() {
		BlockMakerBase bmb = constructBlockMaker();
		int y1 = 10;
		int x1 = 20;
		ArrayList<Point3D> p = bmb.makeFloorSegmentEast(y1, x1);
		assertEquals("Point [x=20.0, y=10.0, z=0.0]", p.get(0).toString());
		assertEquals("Point [x=20.0, y=10.0, z=1.0]", p.get(1).toString());
		assertEquals("Point [x=20.0, y=11.0, z=0.0]", p.get(2).toString());
		assertEquals("Point [x=20.0, y=11.0, z=1.0]", p.get(3).toString());
		assertEquals("Point [x=21.0, y=10.0, z=0.0]", p.get(4).toString());
		assertEquals("Point [x=21.0, y=10.0, z=1.0]", p.get(5).toString());
		assertEquals("Point [x=21.0, y=11.0, z=0.0]", p.get(6).toString());
		assertEquals("Point [x=21.0, y=11.0, z=1.0]", p.get(7).toString());
	}

	@Test
	public void testMakeFloorSegmentSouth() {
		BlockMakerBase bmb = constructBlockMaker();
		int y1 = 10;
		int x1 = 20;
		ArrayList<Point3D> pe = bmb.makeFloorSegmentEast(y1, x1);
		ArrayList<Point3D> pw = bmb.makeFloorSegmentEast(y1, x1);
		ArrayList<Point3D> p = bmb.makeFloorSegmentSouth(pe, pw);
		/* TODO
		assertEquals("Point [x=20.0, y=10.0, z=0.0]", p.get(0).toString());
		assertEquals("Point [x=20.0, y=10.0, z=1.0]", p.get(1).toString());
		assertEquals("Point [x=20.0, y=11.0, z=0.0]", p.get(2).toString());
		assertEquals("Point [x=20.0, y=11.0, z=1.0]", p.get(3).toString());
		assertEquals("Point [x=21.0, y=10.0, z=0.0]", p.get(4).toString());
		assertEquals("Point [x=21.0, y=10.0, z=1.0]", p.get(5).toString());
		assertEquals("Point [x=21.0, y=11.0, z=0.0]", p.get(6).toString());
		assertEquals("Point [x=21.0, y=11.0, z=1.0]", p.get(7).toString());
		*/
	}

	@Test
	public void testMakeFloorSegmentNorth() {
		BlockMakerBase bmb = constructBlockMaker();
		int y1 = 10;
		int x1 = 20;
		ArrayList<Point3D> pe = bmb.makeFloorSegmentEast(y1, x1);
		ArrayList<Point3D> pw = bmb.makeFloorSegmentEast(y1, x1);
		ArrayList<Point3D> p = bmb.makeFloorSegmentNorth(pe, pw);
		/*
		assertEquals("Point [x=20.0, y=10.0, z=0.0]", p.get(0).toString());
		assertEquals("Point [x=20.0, y=10.0, z=1.0]", p.get(1).toString());
		assertEquals("Point [x=20.0, y=11.0, z=0.0]", p.get(2).toString());
		assertEquals("Point [x=20.0, y=11.0, z=1.0]", p.get(3).toString());
		assertEquals("Point [x=21.0, y=10.0, z=0.0]", p.get(4).toString());
		assertEquals("Point [x=21.0, y=10.0, z=1.0]", p.get(5).toString());
		assertEquals("Point [x=21.0, y=11.0, z=0.0]", p.get(6).toString());
		assertEquals("Point [x=21.0, y=11.0, z=1.0]", p.get(7).toString());
		*/
	}
	
	/*
	 * 
	 * @Test public void testFillHoleInTheFloorOneRoom() { fail(
	 * "Not yet implemented"); }
	 * 
	 * @Test public void testGetHolePoint() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testMapPointWithZ() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testGetFloorPoint() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testPrintMark() { fail("Not yet implemented"); }
	 */
}
