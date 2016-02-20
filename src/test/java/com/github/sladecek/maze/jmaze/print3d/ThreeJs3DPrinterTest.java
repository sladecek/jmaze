package com.github.sladecek.maze.jmaze.print3d;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeast;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.TestUtilities;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.printstyle.Color;

public class ThreeJs3DPrinterTest {

	@Test
	public void testPrintBlocks() {
		String expected = "{ \"blocks\":[ " 
				+ "["
				+ " [ 0.0,1.0,2.0] , [ 2.0,1.0,2.0] , [ 4.0,1.0,2.0] ,"
				+ " [ 6.0,1.0,2.0] , [ 8.0,1.0,2.0] , [ 10.0,1.0,2.0] ,"
				+ " [ 12.0,1.0,2.0] , [ 14.0,1.0,2.0] ] ,"
				+ " [ [ 3.0,4.0,5.0] , [ 5.0,4.0,5.0] , [ 7.0,4.0,5.0] ,"
				+ " [ 9.0,4.0,5.0] , [ 11.0,4.0,5.0] , [ 13.0,4.0,5.0] ,"
				+ " [ 15.0,4.0,5.0] , [ 17.0,4.0,5.0] ] ] ,"
				+ " \"marks\":[ [ 6.0,7.0,8.0] ] } ";

		Color color = new Color("010203");
		String comment = "";

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
		ThreeJs3DPrinter p = new ThreeJs3DPrinter();
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			p.printBlocks(bm, baos);
			verify(bm).makeBlocks();
			verify(bm, atLeast(1)).getBlocks();
			String result = baos.toString().replaceAll("\\s+", " ");
			assertEquals(expected, result);
		} catch (IOException ioe) {
			fail(ioe.getMessage());
		}

	}

}
