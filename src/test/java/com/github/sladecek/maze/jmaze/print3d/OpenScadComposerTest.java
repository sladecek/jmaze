package com.github.sladecek.maze.jmaze.print3d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.printstyle.Color;

public class OpenScadComposerTest {

	@FunctionalInterface
	public interface TestedOperation  {
	  void test(OpenScadComposer t) throws IOException;
	}

	@Test
	public void testHeader()  {
		String expected = "";
		TestedOperation testedOperation = osc -> {};
		testOneOperation(expected, testedOperation);
	}
	
	@Test
	public void testBeginUnion() {
		String expected = "union() { ";
		TestedOperation testedOperation = osc -> osc.beginUnion(); 
		testOneOperation(expected, testedOperation);
	}

	@Test
	public void testCloseUnion() {
		String expected = "} ";
		TestedOperation testedOperation = osc -> osc.closeUnion(); 
		testOneOperation(expected, testedOperation);
	}

	@Test
	public void testBeginDifference() {
		String expected = "difference() { ";
		TestedOperation testedOperation = osc -> osc.beginDifference(); 
		testOneOperation(expected, testedOperation);
	}

	@Test
	public void testCloseDifference() {
		String expected = "} ";
		TestedOperation testedOperation = osc -> osc.closeUnion(); 
		testOneOperation(expected, testedOperation);
	}

	@Test
	public void testPrintTranslate() {
		String expected = "translate( [ 4.0,5.0,6.0] )";

		Point3D point = new Point3D(4, 5, 6);
		TestedOperation testedOperation = osc -> osc.printTranslate(point);
		testOneOperation(expected, testedOperation);
	}

	@Test
	public void testPrintText() {
		String expected = "color([0.00, 0.01, 0.01]) { text(\"myText\", size=2.71); } ";		
		String text = "myText";
		Color color = new Color("010203");
		double size = 2.71;
		TestedOperation testedOperation = osc -> osc.printText(text, color, size);
		testOneOperation(expected, testedOperation);
	}

	@Test
	public void testPrintPolyhedron() {
		String expected = "/* myComment*/ color([0.00, 0.01, 0.01]) polyhedron (points =[ [ 1.0,2.0,3.0] , [ 2.0,2.0,3.0] , [ 3.0,2.0,3.0] , [ 4.0,2.0,3.0] , [ 5.0,2.0,3.0] , [ 6.0,2.0,3.0] , [ 7.0,2.0,3.0] , [ 8.0,2.0,3.0] ], faces = [ [0,2,3,1], [0,1,5,4], [5,7,6,4], [6,7,3,2], [1,3,7,5], [6,2,0,4]] ); ";		
		
		ArrayList<Point3D> polyhedron = new ArrayList<Point3D>();
		polyhedron.add(new Point3D(1, 2, 3));
		polyhedron.add(new Point3D(2, 2, 3));
		polyhedron.add(new Point3D(3, 2, 3));
		polyhedron.add(new Point3D(4, 2, 3));
		polyhedron.add(new Point3D(5, 2, 3));
		polyhedron.add(new Point3D(6, 2, 3));
		polyhedron.add(new Point3D(7, 2, 3));
		polyhedron.add(new Point3D(8, 2, 3));
		
		String comment = "myComment";
		Color color = new Color("010203");
		
		TestedOperation testedOperation = osc -> osc.printPolyhedron(polyhedron, comment, color);
		testOneOperation(expected, testedOperation);
	}

	@Test
	public void testFormatColor() {
		Color color = new Color("102030"); 
		assertEquals("[0.06, 0.13, 0.19]", OpenScadComposer.formatColor(color));
	}

	private void testOneOperation(String expected, TestedOperation testedOperation) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (OpenScadComposer osc = new OpenScadComposer(baos)) {
			testedOperation.test(osc);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		String content = baos.toString().replaceAll("\\s+", " ");
		assertEquals(expected, content);
	}

	
}
