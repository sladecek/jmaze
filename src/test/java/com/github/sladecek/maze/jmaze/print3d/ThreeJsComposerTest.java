package com.github.sladecek.maze.jmaze.print3d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.TestUtilities;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.printstyle.Color;

public class ThreeJsComposerTest {
	@FunctionalInterface
	public interface TestedOperation {
		void test(ThreeJsComposer t) throws IOException;
	}

	@Test
	public void testHeader() {
		String expected = "{ } ";
		TestedOperation testedOperation = jsc -> {
		};
		testOneOperation(expected, testedOperation);
	}

	@Test
	public void testBeginList() throws IOException {
		String expected = "{ \"listName\":[ } ";
		TestedOperation testedOperation = jsc -> jsc.beginList("listName");
		testOneOperation(expected, testedOperation);
	}

	@Test
	public void testCloseList() {
		String expected = "{ ] , } ";
		TestedOperation testedOperation = jsc -> jsc.closeList(true);
		testOneOperation(expected, testedOperation);
	}

	@Test
	public void testPrintPolyhedron() {
		String expected = "{ [ [ 1.0,2.0,3.0] , [ 2.0,2.0,3.0] , [ 3.0,2.0,3.0] , [ 4.0,2.0,3.0] , [ 5.0,2.0,3.0] , [ 6.0,2.0,3.0] , [ 7.0,2.0,3.0] , [ 8.0,2.0,3.0] ] } ";
		ArrayList<Point3D> polyhedron = TestUtilities.buildPolyhedronForTest(1, 2, 3, 1);

		String comment = "myComment";
		Color color = new Color("010203");
		TestedOperation testedOperation = jsc -> jsc.printPolyhedron(polyhedron, comment, color);
		testOneOperation(expected, testedOperation);
	}

	@Test
	public void testPrintMark() {
		String expected = "{ [ 4.0,5.0,6.0] } ";
		Point3D point = new Point3D(4, 5, 6);
		String comment = "myComment";
		Color color = new Color("010203");
		TestedOperation testedOperation = jsc -> jsc.printMark(point, comment, color);
		testOneOperation(expected, testedOperation);
	}

	private void testOneOperation(String expected, TestedOperation testedOperation) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (ThreeJsComposer jsc = new ThreeJsComposer(baos)) {
			testedOperation.test(jsc);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		String content = baos.toString().replaceAll("\\s+", " ");
		assertEquals(expected, content);
	}

}
