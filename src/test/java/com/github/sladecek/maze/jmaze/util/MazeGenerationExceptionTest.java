package com.github.sladecek.maze.jmaze.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class MazeGenerationExceptionTest {

	@Test
	public void testMazeGenerationExceptionString() {
		Exception mge = new MazeGenerationException("gugu");
		assertEquals("gugu", mge.getMessage());
	}

	@Test
	public void testMazeGenerationExceptionStringThrowable() {
		Exception thr = new NullPointerException();
		Exception mge = new MazeGenerationException("gugu", thr);
		assertEquals("gugu", mge.getMessage());
		assertEquals(thr, mge.getCause());
	}

}
