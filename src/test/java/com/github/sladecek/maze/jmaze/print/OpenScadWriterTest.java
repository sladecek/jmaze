package com.github.sladecek.maze.jmaze.print;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OpenScadWriterTest {

	@Test
	public void testColorIsWrittenInProperFormat() {
		Color c = new Color(0, 255, 128, 0);
		assertEquals("[0.00, 1.00, 0.50]", OpenScadComposer.formatColor(c));
	}

}
