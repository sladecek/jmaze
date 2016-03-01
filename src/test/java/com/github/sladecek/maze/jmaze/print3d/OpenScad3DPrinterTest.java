package com.github.sladecek.maze.jmaze.print3d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.junit.Test;

import com.github.sladecek.maze.jmaze.TestUtilities;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.printstyle.Color;

public class OpenScad3DPrinterTest {

	@Test
	public void testPrintBlocks() {
		String comment = "";

		Color color = new Color("010203");
		String expected = "union() { /* */ color([0.00, 0.01, 0.01]) "
				+ "polyhedron (points =[ [ 0.0,1.0,2.0] , [ 2.0,1.0,2.0] , [ 4.0,1.0,2.0] ,"
				+ " [ 6.0,1.0,2.0] , [ 8.0,1.0,2.0] , [ 10.0,1.0,2.0] , [ 12.0,1.0,2.0] , [ 14.0,1.0,2.0] ],"
				+ " faces = [ [0,2,3,1], [0,1,5,4], [5,7,6,4], [6,7,3,2], [1,3,7,5], [6,2,0,4]] );"
				+ " /* */ color([0.00, 0.01, 0.01]) "
				+ "polyhedron (points =[ [ 3.0,4.0,5.0] , [ 5.0,4.0,5.0] , [ 7.0,4.0,5.0] ,"
				+ " [ 9.0,4.0,5.0] , [ 11.0,4.0,5.0] , [ 13.0,4.0,5.0] , [ 15.0,4.0,5.0] ," + " [ 17.0,4.0,5.0] ],"
				+ " faces = [ [0,2,3,1], [0,1,5,4], [5,7,6,4], [6,7,3,2], [1,3,7,5], [6,2,0,4]] ); } ";

		ArrayList<Block> inputBlocks = new ArrayList<Block>();

		ArrayList<Point3D> p1 = TestUtilities.buildPolyhedronForTest(0, 1, 2, 2);
		inputBlocks.add(Block.newPolyhedron(p1, comment, color));
		ArrayList<Point3D> p2 = TestUtilities.buildPolyhedronForTest(3, 4, 5, 2);
		inputBlocks.add(Block.newPolyhedron(p2, comment, color));

		Point3D center = new Point3D(6, 7, 8);
		double radius = 3.14;
		inputBlocks.add(Block.newMark(center, radius, comment, color));

		IBlockMaker bm = mock(IBlockMaker.class);

		when(bm.getBlocks()).thenReturn(inputBlocks);
		OpenScad3DPrinter p = new OpenScad3DPrinter();
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			p.printBlocks(bm, baos);
			verify(bm).makeBlocks();
			verify(bm, atLeast(1)).getBlocks();
			String result = baos.toString().replaceAll("\\s+", " ");
			assertEquals(expected, result);
		} catch (Exception ioe) {
			fail(ioe.getMessage());
		}

	}

}
