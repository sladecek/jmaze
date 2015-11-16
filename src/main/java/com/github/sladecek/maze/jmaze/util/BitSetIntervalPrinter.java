package com.github.sladecek.maze.jmaze.util;

import java.util.BitSet;

/**
 * Utility class that prints bitset as set of intervals.
 *
 */
public final class BitSetIntervalPrinter {

	public BitSetIntervalPrinter(BitSet b) {
		this.b = b;
	}
	
	public String printAsIntervals() {
		String result = new String();
		int start = -1;
		for (int i = 0; i < b.size(); i++) {
			if (b.get(i)) {
				if (start < 0) {
					start = i;
				} else {
					if (start >= 0) {
						result += Integer.toString(start);
						if (start < i - 1) {
							result += "-" + Integer.toString(i - 1);
						}
						result += " ";
					}					
					start = -1;
				}				
			}
		}
		if (start >= 0) {
			result += Integer.toString(start);
			if (start < b.size() - 1) {
				result += "-" + Integer.toString(b.size() - 1);
			}
		}					
		return result;
	}

	private BitSet b;
	
}
