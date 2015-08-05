package com.github.sladecek.maze.jmaze.util;

import java.util.BitSet;

public class BitSetWithPrint extends BitSet {

	public BitSetWithPrint(int size) {
		super(size);
	}
	
	public String printAsIntervals()
	{
		String result = new String();
		int start = -1;
		for (int i = 0; i < size(); i++) {
			if (get(i)) {
				if (start < 0) {
					start = i;
				} else {
					if (start >= 0) {
						result += Integer.toString(start);
						if (start < i-1) {
							result += "-" + Integer.toString(i-1);
						}
						result += " ";
					}					
					start = -1;
				}				
			}
		}
		if (start >= 0) {
			result += Integer.toString(start);
			if (start < size()-1) {
				result += "-" + Integer.toString(size()-1);
			}
		}					
		return result;
	}

}
