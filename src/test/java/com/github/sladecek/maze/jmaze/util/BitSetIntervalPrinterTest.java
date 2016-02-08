package com.github.sladecek.maze.jmaze.util;

import static org.junit.Assert.assertEquals;

import java.util.BitSet;

import org.junit.Test;

public class BitSetIntervalPrinterTest {

    private static final int bitCount = 5;

    @Test
    public void testEmpty() {
        BitSet bs = new BitSet(bitCount);
        assertEquals("", new BitSetIntervalPrinter(bs, bitCount).printAsIntervals());
    }

    @Test
    public void testFull() {
        BitSet bs = new BitSet(bitCount);
        bs.set(0, 5);
        assertEquals("0-4", new BitSetIntervalPrinter(bs, bitCount).printAsIntervals());
    }

    @Test
    public void testFirst() {
        BitSet bs = new BitSet(bitCount);
        bs.set(0);
        assertEquals("0", new BitSetIntervalPrinter(bs, bitCount).printAsIntervals());
    }

    @Test
    public void testLast() {
        BitSet bs = new BitSet(bitCount);
        bs.set(4);
        assertEquals("4", new BitSetIntervalPrinter(bs, bitCount).printAsIntervals());
    }

    @Test
    public void testMiddle() {
        BitSet bs = new BitSet(bitCount);
        bs.set(2);
        assertEquals("2", new BitSetIntervalPrinter(bs, bitCount).printAsIntervals());
    }

    @Test
    public void testInterval() {
        BitSet bs = new BitSet(bitCount);
        bs.set(1);
        bs.set(2);
        assertEquals("1-2", new BitSetIntervalPrinter(bs, bitCount).printAsIntervals());
    }

    @Test
    public void testTwo() {
        BitSet bs = new BitSet(bitCount);
        bs.set(1);
        bs.set(2);
        bs.set(4);
        assertEquals("1-2 4", new BitSetIntervalPrinter(bs, bitCount).printAsIntervals());
    }
}
