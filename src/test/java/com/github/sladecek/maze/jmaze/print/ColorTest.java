package com.github.sladecek.maze.jmaze.print;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.colors.Color;

public class ColorTest {

	@Test
	public void testConstructorCorrectlyParsesString() {
		Color c = new Color("0181ff");
		assertEquals(0x01, c.getR());
		assertEquals(0x81, c.getG());
		assertEquals(0xff, c.getB());
		assertEquals(0, c.getA());
	}

}
