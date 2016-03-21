package com.github.sladecek.maze.jmaze.printstyle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.printstyle.Color;

public class ColorTest {

	@Test
	public void testConstructorCorrectlyParsesString() {
		Color c = new Color("0181ff");
		assertEquals(0x01, c.getR());
		assertEquals(0x81, c.getG());
		assertEquals(0xff, c.getB());
		assertEquals(0, c.getA());
	}

	@Test
	public void testToString() {
		Color c = new Color("0181ff");
		assertEquals("Color [r=1, g=129, b=255, a=0]", c.toString());
	}

	@Test
	public void testToSvg() {
		Color c = new Color("0181ff");
		assertEquals("rgb(1,129,255)", c.toSvg());
	}

	@Test
	public void testToThreeJs() {
		Color c = new Color("0181ff");
		assertEquals("#0181ff", c.toThreeJs());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testColor_invalid() {
		new Color("01234");
	}

}
