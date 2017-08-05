package com.github.sladecek.maze.jmaze.maze;

import static org.junit.Assert.assertEquals;

import com.github.sladecek.maze.jmaze.shapes.*;
import org.junit.Before;
import org.junit.Test;


public class MazeTest {

	Irrengarten maze;

	@Before
	public void setUp() throws Exception {
		maze = new Irrengarten();
	}

	@Test
	public void testSetContext() {
		ShapeContext c = new ShapeContext(true, 1, 2);
		maze.setContext(c);
		assertEquals(c,  maze.getContext());
	}

}
