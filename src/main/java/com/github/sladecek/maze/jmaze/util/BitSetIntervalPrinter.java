package com.github.sladecek.maze.jmaze.util;

import java.util.BitSet;

/**
 * Utility class that prints bitset as set of intervals.
 *
 */
public final class BitSetIntervalPrinter {

    public BitSetIntervalPrinter(BitSet b, int bitCount) {
		this.bitSet = b;
		this.bitCount = bitCount;
	}
	
	public String printAsIntervals() {
		String result = new String();
		int start = 0;

		boolean spc = false;
		while (start < bitCount) {
		    int i1 = bitSet.nextSetBit(start);
		    if (i1 < 0) {
		        break;
		    }
		    int i2 = bitSet.nextClearBit(i1);
		    if (i2 < 0 || i2 >= bitCount)  {
		            i2 =  bitCount;
		    }
		    if (spc) {
		        result += " ";
		    }
            result += Integer.toString(i1);
            if (i1 != i2-1) {
                result += "-" + Integer.toString(i2-1);
            }
            spc = true;
		    start = i2;
		}
		return result;
	}

	private BitSet bitSet;
    private int bitCount;


}
