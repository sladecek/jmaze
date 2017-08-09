package com.github.sladecek.maze.jmaze.print3d.output;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.print3d.output.OpenScadComposer;
import com.github.sladecek.maze.jmaze.printstyle.Color;

public class OpenScadWriterTest {

	@Test
	public void testColorIsWrittenInProperFormat() {
		Color c = new Color(0, 255, 128, 0);
		assertEquals("[0.00, 1.00, 0.50]", OpenScadComposer.formatColor(c));
	}

}
