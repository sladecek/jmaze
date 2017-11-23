package com.github.sladecek.maze.jmaze.util;

import static org.junit.Assert.*;

import com.github.sladecek.maze.jmaze.maze.MazeGenerationException;
import org.junit.Test;

public class MazeGenerationExceptionTest {

	@Test
	public void testMazeGenerationExceptionString() {
		Exception mge = new MazeGenerationException("hello");
		assertEquals("hello", mge.getMessage());
	}

	@Test
	public void testMazeGenerationExceptionStringThrowable() {
		Exception thr = new NullPointerException();
		Exception mge = new MazeGenerationException("hello", thr);
		assertEquals("hello", mge.getMessage());
		assertEquals(thr, mge.getCause());
	}

}
